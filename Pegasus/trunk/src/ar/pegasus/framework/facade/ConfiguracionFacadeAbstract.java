package ar.pegasus.framework.facade;

import ar.pegasus.framework.componentes.PException;

public interface ConfiguracionFacadeAbstract<T extends Enum<?>> {

	public String get(T enumClavesConfiguracion);

	public boolean getBoolean(T enumClavesConfiguracion);
	
	public Integer getInteger(T enumClavesConfiguracion) throws PException ;

}
