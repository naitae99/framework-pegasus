package ar.pegasus.framework.util.jdbc;

import java.util.Properties;

public class JdbcQueryConfiguration extends Properties {
	private static final long serialVersionUID = 1455323186596035247L;

	private final String name;

	public JdbcQueryConfiguration (String name, String propertiesFilePath) {
		this.name = name ;
		load(propertiesFilePath) ;
		validateProperties();
	}

	public JdbcQueryConfiguration (String name, String user, String password, String url, String driver) {
		this.name = name ;
		setProperty(PropertyNames.USER.name, user);
		setProperty(PropertyNames.PASSWORD.name, password);
		setProperty(PropertyNames.URL.name, url);
		setProperty(PropertyNames.DRIVER.name, driver);
		validateProperties();
	}
	
	private void validateProperties() {
		for(PropertyNames propertyName : PropertyNames.values()) {
			if(!containsKey(propertyName.name)) {
				JdbcQuery.logger.error("[" + name + "] Se necesita especificar: " + propertyName.name);
				throw new IllegalStateException("Se necesita especificar: " + propertyName.name);
			}
		}
	}

	/**
	 * Levanta los parámetros de un archivo de configuración:
	 * ws/db/JdbcSqlFacade.properties
	 * @return los parámetros.
	 * @throws Exception Si no se pudo cargar la configuración.
	 */
	private void load(String propertiesFilePath) {
		try {
			load(JdbcQuery.class.getClassLoader().getResource(propertiesFilePath).openStream());
			//Sólo para ocultar el password
            String realPassword = getProperty(PropertyNames.PASSWORD.name);
            setProperty(PropertyNames.PASSWORD.name, "****");
            JdbcQuery.logger.info("[" + name + "] Configuración cargada desde archivo: " + this);
            setProperty(PropertyNames.PASSWORD.name, realPassword);
		} catch(Exception e) {
			JdbcQuery.logger.error("[" + name + "] No se pudo cargar el archivo de configuración.");
			throw new IllegalStateException("No se pudo cargar el archivo de configuración: " + e);
		}
	}

	protected enum PropertyNames {
		USER("user"), PASSWORD("password"), URL("url"), DRIVER("driver");

		public final String name;

		private PropertyNames(String name) {
			this.name = name;
		}
	}

	public String getUser() {
		return getProperty(PropertyNames.USER.name);
	}

	public String getPassword() {
		return getProperty(PropertyNames.PASSWORD.name);
	}

	public String getUrl() {
		return getProperty(PropertyNames.URL.name);
	}

	public String getDriver() {
		return getProperty(PropertyNames.DRIVER.name);
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

}
