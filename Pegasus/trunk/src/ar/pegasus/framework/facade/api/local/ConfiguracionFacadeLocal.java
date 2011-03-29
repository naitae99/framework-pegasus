package ar.pegasus.framework.facade.api.local;

import javax.ejb.Local;

import ar.pegasus.framework.facade.ConfiguracionFacadeAbstract;

@Local
public interface ConfiguracionFacadeLocal<T extends Enum<?>> extends ConfiguracionFacadeAbstract<T> {

}
