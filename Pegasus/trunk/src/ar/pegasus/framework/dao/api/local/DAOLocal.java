package ar.pegasus.framework.dao.api.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;
 
import javax.ejb.Local;
import javax.persistence.Query;

import ar.pegasus.framework.componentes.PException;

@Local
public interface DAOLocal<E, PK> {

	public void remove(E object) throws PException;

	public E save(E object);

	public E getById(PK id);

	public List<E> getAll();

	public List<E> getAllOrderBy(String nameAttribute);

	public void persist(E object);

	public void save(Collection<E> objectList);

	public void removeById(PK id);

	public List<E> getBy(Map<String, Object> parametros);

	public List<E> executeListResultQuery(Query query) throws PException;

	public E executeSingleResultQuery(Query query) throws PException;
	
	public E getReferenceById(PK id);

	/**
	 * hace un flush y limpia la session de hibernate. 
	 * (es util en procesos batch porque la session junta demasiados objetos y eso hace lenta la ejecucion) 
	 */
	public void clearSession();
}

