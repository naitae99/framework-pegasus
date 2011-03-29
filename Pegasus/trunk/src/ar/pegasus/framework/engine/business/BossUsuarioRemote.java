package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.Remote;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.entidades.core.Rol;

@Remote
public interface BossUsuarioRemote {

		public abstract int verificarUsuario(String usuario, String password) throws PException;

		public abstract List<Rol> getRolesPorAplicacion(int idAplicacion) throws PException;
		
//		public void save(UsuarioAcceso usuarioAcceso, int idAplicacion) throws PException;
//
//		public abstract List<Rol> getRolesPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException;
//
//		public abstract void delete(UsuarioAcceso usuarioAcceso) throws PException;
//
//		public abstract UsuarioAcceso getUsuarioAcceso(int idUsuarioAcceso, int idAplicacion) throws PException;

		public String getPasswordPorUsuario(int idUsuario) throws PException;
		
		public List<Modulo> getModulosPorRol(int idRol) throws PException;
		
	}