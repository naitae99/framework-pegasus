package ar.pegasus.framework.util.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.util.log.PLogger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcQuery {

	private static int instanceCounter = 0;
	public static final PLogger logger = new PLogger(JdbcQuery.class);
	private final JdbcQueryConfiguration configuration;

	private ComboPooledDataSource comboPooledDataSource = null ; 
	
	/**
	 * @param protertiesFilePath El archivo de configuración.
	 * @see JdbcQueryConfiguration
	 */
	public JdbcQuery(String protertiesFilePath) {
		configuration = new JdbcQueryConfiguration("JdbcSqlFacade " + (++instanceCounter), protertiesFilePath);
	}

	/**
	 * @param properties Las propiedades de configuración.
	 * @see JdbcQueryConfiguration
	 */
	public JdbcQuery(JdbcQueryConfiguration jdbcQueryConfiguration) {
		configuration = jdbcQueryConfiguration;
	}

	/** 
	 * Abre una conexión JDBC y la devuelve.
	 * @return La conexión JDBC creada. 
	 * @throws SQLException Si no se pudo abrir la conexión.
	 */
	protected Connection getConnection() throws SQLException {
		if (comboPooledDataSource == null) {
			try {
				comboPooledDataSource = new ComboPooledDataSource(); 
				comboPooledDataSource.setDriverClass( configuration.getDriver() ); //loads the jdbc driver 
				comboPooledDataSource.setJdbcUrl( configuration.getUrl() ); 
				comboPooledDataSource.setUser( configuration.getUser() ); 
				comboPooledDataSource.setPassword( configuration.getPassword() );
			} catch (PropertyVetoException e) {
				logger.error("[" + configuration.getName() + "] No se pudo abrir la conexión con la Base de Datos de Publicidad", e);
				throw new SQLException(e.getMessage());
			}
		}
		return comboPooledDataSource.getConnection() ;
	}

	/**
	 * 
	 * @param <T>
	 * @param query
	 * @param parametersNames
	 * @param parametersValues
	 * @param clazz
	 * @return
	 * @throws PException
	 */
	public <T extends InstantiateFromResultSet> List<T> executeQuery(String query, String[] parametersNames, Object[] parametersValues, Class<T> clazz) throws PException {
		Connection connection = null ;
		Statement statement = null ;			
		try {
			long initTime = System.currentTimeMillis();
			connection = getConnection();
			NamedParameterStatement namedParameterStatement = new NamedParameterStatement(connection, query);
			for (int i = 0; i < parametersValues.length; i++) {
				if (parametersValues[i] == null) {
					namedParameterStatement.setObject(parametersNames[i], parametersValues[i]) ;
					continue ;
				}
				if (Date.class.isAssignableFrom(parametersValues[i].getClass())) {
					Timestamp timestamp = new Timestamp (((Date)parametersValues[i]).getTime()) ;
					namedParameterStatement.setTimestamp(parametersNames[i], timestamp) ;
					continue ;
				}
				if (String.class.isAssignableFrom(parametersValues[i].getClass())) {
					namedParameterStatement.setString(parametersNames[i], (String)parametersValues[i]) ;
					continue ;
				}
				if (Integer.class.isAssignableFrom(parametersValues[i].getClass())) {
					namedParameterStatement.setInt(parametersNames[i], (Integer)parametersValues[i]) ;
					continue ;
				}
				namedParameterStatement.setObject(parametersNames[i], parametersValues[i]) ;
			}
			ResultSet rs = namedParameterStatement.executeQuery();
			List<T> lista = new ArrayList<T>() ;
			while(rs.next()) {
				T instancia = clazz.newInstance() ;
				instancia.setData(rs) ;
				lista.add(instancia);
			}
			logger.debug("[" + configuration.getName() + "] Retorno total en " + (System.currentTimeMillis() - initTime) + " milisegundos de " + query);
			return lista;
		} catch(Exception e) {
			logger.debug("[" + configuration.getName() + "] No se pudo ejecutar la consulta: " + query, e);
			throw Exceptions.getPException(e);
		} finally {
			try {
				release(connection, statement);
			} catch(SQLException e) {
				logger.error("[" + configuration.getName() + "] Error liberando la conexión de: " + query, e);
				throw Exceptions.getPException(e);
			}
		}
	}

	/**
	 * Libera la conexión y el statement cuando corresponda (si no son null). 
	 * @param connection La conexión.
	 * @param statement El statement.
	 * @throws SQLException Si se produjo algún error.
	 */
	private void release(Connection connection, Statement statement) throws SQLException {
		if(connection != null) {
			if(statement != null) {
				statement.close();
			}
			connection.close();
		}
	}

	/**
	 * Creación de excepciones.
	 */
	private static class Exceptions {

		/**
		 * @param e La excepción original.
		 * @return Una PException de causa desconocida.
		 */
		private static PException createGeneralPException(Exception e) {
			int tipoError = BossError.ERR_INDETERMINADO ;
			String[] tips = new String[] { "Reintente luego" } ;
			if (e instanceof SQLException && e.getMessage() != null) { 
				if ( 
					e.getMessage().indexOf("String or binary data would be truncated.") != -1 || 
					e.getMessage().indexOf("Incorrect syntax near") != -1 ||
					e.getMessage().indexOf("Could not find stored procedure") != -1 ||
					e.getMessage().indexOf("Violation of PRIMARY KEY constraint") != -1
				) {
					tipoError = BossError.ERR_APLICACION ;
					tips = new String[] { "Notifique a Sistemas" } ;
				} else if (
					e.getMessage().indexOf("An attempt by a client to checkout a Connection has timed out") != -1 ||
					e.getMessage().indexOf("Connections could not be acquired from the underlying database") != -1 ||
					e.getMessage().indexOf("Software caused connection abort") != -1
				) {
					tipoError = BossError.ERR_CONEXION ;					
				}
			}
			return new PException(tipoError, null, "Error al ejecutar llamada Sql", e, tips);
		}

		/**
		 * @param e La excepción original.
		 * @return La misma excepción si es del tipo PException o una excepción PException general del facade. 
		 */
		private static PException getPException(Exception e) {
			PException cle;
			if(e instanceof PException) {
				cle = (PException)e;
			} else {
				cle = createGeneralPException(e);
			}
			return cle;
		}
	}

}