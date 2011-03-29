package ar.pegasus.framework.facade.api.remote;

import javax.ejb.Remote;

import ar.pegasus.framework.facade.ConfiguracionFacadeAbstract;

@Remote
public interface ConfiguracionFacadeRemote<T extends Enum<?>> extends ConfiguracionFacadeAbstract<T> {

}
