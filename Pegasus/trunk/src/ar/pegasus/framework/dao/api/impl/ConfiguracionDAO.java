package ar.pegasus.framework.dao.api.impl;

import javax.ejb.Stateless;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.ConfiguracionDAOLocal;

@Stateless
public class ConfiguracionDAO<T extends Enum<?>> implements ConfiguracionDAOLocal<T> {

	public String get(T enumClavesConfiguracion) {
		return System.getProperty(enumClavesConfiguracion.name()) ;
	}

	public boolean getBoolean(T enumClavesConfiguracion) {
		return Boolean.parseBoolean(get(enumClavesConfiguracion));
	}

	public Integer getInteger(T enumClavesConfiguracion) throws PException {
		try {
		return Integer.parseInt(get(enumClavesConfiguracion));
		} catch (NumberFormatException e) {
			throw new PException("No se pudo obtener el valor asociado a la clave " + enumClavesConfiguracion + " (no se pudo convertir a entero el valor " + get(enumClavesConfiguracion) + ")") ;
		}
	}

}
