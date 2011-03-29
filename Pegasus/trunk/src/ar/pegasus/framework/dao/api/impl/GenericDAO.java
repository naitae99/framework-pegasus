package ar.pegasus.framework.dao.api.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.error.PRuntimeException;
import ar.pegasus.framework.dao.api.local.DAOLocal;


/**
 * Dao Generico para la persistencia de las entidades del negocio.
 * 
 * @author lcremonte
 * 
 * @param <E>
 *            Entidad de negocio
 * @param <PK>
 *            Tipo de Primary key de la Entidad de negocio. Actualmente
 *            trabajamos con Integer y String
 */
public abstract class GenericDAO<E, PK> implements DAOLocal<E, PK> {

	private static Logger log = Logger.getLogger(GenericDAO.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	protected Class<E> getType() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Type type = actualTypeArguments[0];
		return (Class<E>) type;
	}

	@SuppressWarnings("unchecked")
	public void mergeCollection(Collection entidades) {
		for (Object entidad : entidades) {
			getEntityManager().merge(entidad);
		}
	}

	public E getById(PK id) {
		
		return getEntityManager().find(this.getType(), id);
		/*
		// FIXME: return getEntityManager().find(type, id);//no funciona. debe
		// ser un bug de hibernate en esta version (jboss 4.0.5GA).
		Class<E> type = this.getType();
		List list = getEntityManager().createQuery(
				"from " + type.getName() + " where id = " + id).getResultList();
		if (list.size() == 0)
			return null;
		return (E) list.get(0);
		*/
	}

	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		Class<E> type = this.getType();
		return getEntityManager()
				.createQuery("from " + type.getCanonicalName()).getResultList();
	}
	
	public Query getQueryAllRO() {
		Class<E> type = this.getType();
		Query query = getEntityManager()
				.createQuery("from " + type.getCanonicalName());
		query.setHint("org.hibernate.cacheable", Boolean.TRUE);
		query.setHint("org.hibernate.readOnly", Boolean.TRUE);
		return query;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<E> getAllOrderBy(String nameAttribute) {
		Class<E> type = this.getType();
		return getEntityManager().createQuery(
				"from " + type.getCanonicalName() + " ORDER BY "
						+ nameAttribute).getResultList();
	}

	/**
	 * Ejecuta el save y/o update
	 * 
	 * @param object
	 * @return
	 */
	public E save(E object) {
		E newEntity = getEntityManager().merge(object);
		//getEntityManager().flush();
		return newEntity;
	}

	/**
	 * Ejecuta el save y/o update para la colecci�n de objetos,
	 * 
	 * @param objectList
	 */
	public void save(Collection<E> objectList) {
		// TODO: Implementar
	}

	public void persist(E object) {
		this.getEntityManager().persist(object);
	}

	/**
	 * Elimina fisicamente a la entidad.
	 * 
	 * @param object
	 * @throws PException
	 */
	public void remove(E object) throws PException {
		getEntityManager().remove(object);
	}

	/**
	 * Elimina fisicamente a la entidad por medio se su id.
	 * 
	 * @param id
	 */
	public void removeById(PK id) {
		//E object = getEntityManager().find(this.getType(), id);
		E object = getEntityManager().getReference(this.getType(), id);
		getEntityManager().remove(object);
		getEntityManager().flush();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	public List<E> getBy(Map<String, Object> parametros) {

		Class<E> type = this.getType();
		StringBuffer hql = new StringBuffer();
		hql.append("from  " + type.getCanonicalName());

		Set<String> keys = parametros.keySet();
		boolean esPrimero = true;
		for (String key : keys) {
			if (esPrimero) {
				hql.append("    where " + key + " = :" + key);
				esPrimero = false;
			} else
				hql.append("   and  " + key + " = :" + key);
		}

		log.debug(hql);

		Query query = entityManager.createQuery(hql.toString());
		for (String key : keys) {
			query.setParameter(key, parametros.get(key));
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List createQuery(String consulta, String[] parameterNames,
			Object[] parameterValues) {
		Query query = getEntityManager().createQuery(consulta);
		for (int i = 0; i < parameterNames.length; i++) {
			query.setParameter(parameterNames[i], parameterValues[i]);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List createQuery(String consulta, String[] parameterNames,
			Object[] parameterValues, int firstResult, int maxResults) {
		Query query = getEntityManager().createQuery(consulta);
		for (int i = 0; i < parameterNames.length; i++) {
			query.setParameter(parameterNames[i], parameterValues[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<E> executeListResultQuery(Query query) throws PException {
		List<E> lista = null;
		try {
			lista = query.getResultList();
		} catch (PersistenceException ex) {
			this.convertirPersistenceException(ex);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public E executeSingleResultQuery(Query query) throws PException {
		E e = null;
		try {
			e = (E) query.getSingleResult();
		} catch (PersistenceException ex) {
			this.convertirPersistenceException(ex);
		}
		return e;
	}

	protected void convertirPersistenceException(PersistenceException ex)
			throws PException {
		// TODO: Convertir
		throw new PException("Se ha producido un error de infraestructura", ex);
	}
	
	/**
	 * Obtiene la session de hibernate.
	 * @return
	 */
	protected Session getHibernateSession() {
		Session session=null;
		try{
			session = (Session)getEntityManager().getDelegate();//jboss 4.2
		}catch (ClassCastException cast){
			session = ((HibernateEntityManager)getEntityManager().getDelegate()).getSession();//jboss 4.0.5
		}
		return session;
	}

	public E getReferenceById(PK id){
		return getEntityManager().getReference(this.getType(), id);
	}
	
	public void clearSession() {
		getHibernateSession().flush();
		getHibernateSession().clear();
	}
	
	private static Map<String,DataSource> dsMap = new HashMap<String, DataSource>();
	
	public static Connection getConnection(String jndiDataSource) {
		Connection cnn = null;
		try {
			
			DataSource ds = dsMap.get(jndiDataSource);
			if (ds == null){
				InitialContext initialContext = new InitialContext();
				ds = (DataSource)initialContext.lookup(jndiDataSource);
				dsMap.put(jndiDataSource, ds);
			}

			try {
				cnn = ds.getConnection();
				//cnn.setAutoCommit(false);
			} catch(SQLException sqle) {
				log.error("Error obteniendo la conexi�n en GenericDAO: " + sqle.getMessage(),sqle);
				throw new PRuntimeException("Error obteniendo la conexi�n en GenericDAO: " + sqle.getMessage(),sqle);
			}
		} catch(NamingException ne) {
			log.error("Error en el m�todo getConnection() de la clase GenericDAO: " + ne.getMessage());
			throw new PRuntimeException("Error en el m�todo getConnection() de la clase GenericDAO: " + ne.getMessage(),ne);
		}
		return cnn;
	}
	
	
	
}
