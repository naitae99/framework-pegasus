package ar.pegasus.framework.util;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.engine.business.BossModuloRemote;
import ar.pegasus.framework.engine.business.BossRolRemote;
import ar.pegasus.framework.engine.business.BossUsuarioRemote;
import ar.pegasus.framework.facade.api.remote.ConfiguracionFacadeRemote;

public class BeanFactoryGeneralRemote extends BeanFactoryRemoteAbstract {


	private static BeanFactoryGeneralRemote instance;

	public static BeanFactoryGeneralRemote getInstance() throws PException {
		if(instance == null)
			instance = new BeanFactoryGeneralRemote();
		return instance;
	}
	
	protected BeanFactoryGeneralRemote() throws PException {
		addJndiName(BossUsuarioRemote.class);
		addJndiName(BossRolRemote.class);
		addJndiName(ConfiguracionFacadeRemote.class);
		addJndiName(BossModuloRemote.class);


	}
	
}
