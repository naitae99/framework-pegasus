package ar.pegasus.framework.templates.login;

import ar.pegasus.framework.componentes.PException;

public interface ILoginManager {

	public boolean login(String usuario, String password) throws PException;

}