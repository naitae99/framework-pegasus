package ar.pegasus.framework.dao.api.local;

import javax.ejb.Local;

import ar.pegasus.framework.entidades.core.Usuario;

@Local
public interface UsuarioDAOLocal extends DAOLocal<Usuario, Integer>{
	public Usuario getByName(String nombreUsuario);

	public int verificarUsuario(String usuario, String password);
}
