package ar.pegasus.framework.dao.api.local;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;

@Local
public interface ConfiguracionDAOLocal<T extends Enum<?>> {

	public String get(T enumClavesConfiguracion) ;
	
	public boolean getBoolean(T enumClavesConfiguracion);

	public Integer getInteger(T enumClavesConfiguracion) throws PException ;

}
