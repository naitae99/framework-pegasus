package ar.pegasus.framework.facade.api.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.ConfiguracionDAOLocal;
import ar.pegasus.framework.facade.api.local.ConfiguracionFacadeLocal;
import ar.pegasus.framework.facade.api.remote.ConfiguracionFacadeRemote;

@Stateless
public class ConfiguracionFacade<T extends Enum<?>> implements ConfiguracionFacadeLocal<T>, ConfiguracionFacadeRemote<T> {

	@EJB
	private ConfiguracionDAOLocal<T> configuracionDAO ;

	public String get(T enumClavesConfiguracion) {
		return configuracionDAO.get(enumClavesConfiguracion) ;
	}

	public boolean getBoolean(T enumClavesConfiguracion) {
		return configuracionDAO.getBoolean(enumClavesConfiguracion);
	}
	
	public Integer getInteger(T enumClavesConfiguracion) throws PException {
		return configuracionDAO.getInteger(enumClavesConfiguracion);
	}

}
