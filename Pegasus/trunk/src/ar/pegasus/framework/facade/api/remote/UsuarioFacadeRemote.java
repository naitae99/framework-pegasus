package ar.pegasus.framework.facade.api.remote;

import javax.ejb.Remote;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Usuario;

@Remote
public interface UsuarioFacadeRemote {
	public Usuario doLogin(String nombreUsuario, String clave) throws PException;
}
