package ar.pegasus.framework.dao.api.impl;

import java.util.List;

import javax.ejb.Stateless;

import ar.pegasus.framework.dao.api.local.UsuarioDAOLocal;
import ar.pegasus.framework.engine.business.BossUsuario;
import ar.pegasus.framework.entidades.core.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario,Integer> implements UsuarioDAOLocal{

	@SuppressWarnings("unchecked")
	public Usuario getByName(String nombreUsuario) {
		List<Usuario> usuarios = getEntityManager().createQuery("from Usuario p where p.userName=:name")
													.setParameter( "name", nombreUsuario).getResultList();
		return (usuarios.size() > 0?usuarios.get(0):null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int verificarUsuario(String nombreUsuario, String password) {
		List<Usuario> usuarios = getEntityManager().createQuery("from Usuario p where p.userName=:name")
													.setParameter( "name", nombreUsuario).getResultList();
		if(usuarios.isEmpty())
			return BossUsuario.USUARIO_INVALIDO;
		for(Usuario usuario : usuarios){
			if(usuario.getPassword().equals(password))
				return  usuario.getIdUsuario();
		}
		return BossUsuario.PASSWORD_INVALIDA;
	}

}
