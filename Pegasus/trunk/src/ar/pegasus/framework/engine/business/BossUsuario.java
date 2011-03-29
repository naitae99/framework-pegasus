package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.UsuarioDAOLocal;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.entidades.core.Usuario;

@Stateless
public class BossUsuario implements BossUsuarioLocal, BossUsuarioRemote {
	public static final int USUARIO_INVALIDO = -1;
	public static final int PASSWORD_INVALIDA = 0;
	public static final int ERR_CONEXION = -2;
	
	@EJB
	private UsuarioDAOLocal usuarioDAO;

	@Override
	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario,
			int idAplicacion) throws PException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPasswordPorUsuario(int idUsuario) throws PException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rol> getRolesPorAplicacion(int idAplicacion) throws PException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rol> getRolesPorUsuarioAplicacion(int idUsuario,
			int idAplicacion) throws PException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getUsuariosPorIds(Integer[] ids, int idAplicacion)
			throws PException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Chequea que el usuario sea valido.
	 * Devuelve el <b>id</b> del usuario en caso de que sea valido.
	 * En caso contrario devuelve </b>USUARIO_INVALIDO</b> si el usuario es invalido
	 * o <b>PASSWORD_INVALIDA</b> si la password es inv�lida.
	 * @param usuario El nombre de usuario.
	 * @param password La contrase�a del usuario.
	 * @return idUsuario El id del usuario logueado o alguno de los c�digos de error.
	 * @throws CLException
	 */
	@Override
	public int verificarUsuario(String usuario, String password) throws PException {
		return usuarioDAO.verificarUsuario(usuario,password);
	}

	@Override
	public int verificarUsuario(String username) throws PException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Modulo> getModulosPorRol(int idRol) throws PException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
