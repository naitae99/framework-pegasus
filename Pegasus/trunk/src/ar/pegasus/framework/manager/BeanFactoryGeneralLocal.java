package ar.pegasus.framework.manager;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.UsuarioDAOLocal;
import ar.pegasus.framework.engine.business.BossUsuarioLocal;
import ar.pegasus.framework.util.BeanFactoryLocalAbstract;


public class BeanFactoryGeneralLocal extends BeanFactoryLocalAbstract {

	private static BeanFactoryGeneralLocal instance;
	
	public static BeanFactoryGeneralLocal getInstance() throws PException {
		if(instance == null)
			instance = new BeanFactoryGeneralLocal();
		return instance;
	}

	protected BeanFactoryGeneralLocal() throws PException {
		addJndiName(UsuarioDAOLocal.class);
		addJndiName(BossUsuarioLocal.class);
	}
}
