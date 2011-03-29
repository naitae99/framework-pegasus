package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.entidades.core.Usuario;

@Local 
public interface BossUsuarioLocal {

	public abstract int verificarUsuario(String usuario, String password) throws PException;

	/**
	 * 
	 * Ver {@link UsuarioAccesoDAOLocal#getUsuarioAccesoList(String, Integer...)}
	 */
//	public List<UsuarioAcceso> getUsuarioAccesoList(String filtroAgencia, String filtroNroSapAgencia, int idAplicacion, List<UsuarioAcceso> usuarioAccesoList, String filtroNombre, Integer... estadoUsuario) throws PException;
//	
//	public List<UsuarioAcceso> getUsuarioAccesoList(String filtroAgencia, String filtroNroSapAgencia, int idAplicacion, List<UsuarioAcceso> usuarioAccesoList, Integer... estadoUsuario) throws PException;
//	
//	public List<UsuarioAcceso> getUsuarioAccesoListEagerByDesktop(Agencia agencia, int idAplicacion, Integer ... estadoUsuario) throws PException;
//
//	
//	public abstract List<UsuarioAcceso> getUsuarioAccesoListEager(Agencia agencia, int idAplicacion, Integer... estadoUsuario) throws PException;

	public abstract List<Rol> getRolesPorAplicacion(int idAplicacion) throws PException;

	public abstract List<Rol> getRolesPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException;

//	public abstract void save(UsuarioAcceso usuarioAcceso, int idAplicacion) throws PException;
//	
//	public abstract void delete(UsuarioAcceso usuarioAcceso) throws PException;

//	public abstract UsuarioAcceso getUsuarioAcceso(int idUsuarioAcceso) throws PException;
	
//	public abstract UsuarioAcceso getUsuarioAccesoEager(int idUsuarioAcceso, int idAplicacion) throws PException;
//
//	public abstract UsuarioAcceso getUsuarioAccesoPorLogin(String login) throws PException;

	public abstract int verificarUsuario(String username) throws PException;

	public String getPasswordPorUsuario(int idUsuario) throws PException;

//	public abstract void save(UsuarioAcceso usuario);
	
	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException;
	
	public List<Usuario> getUsuariosPorIds(Integer[] ids, int idAplicacion) throws PException;
	
//	public List<ProductoConsulta> getProductosConfiguracionAcceso(ConfiguracionAcceso configuracionAcceso) throws PException;
//	
//	public UsuarioAcceso getUsuarioAccesoSession(Context sessionContext) throws PException ;
//	
//	public void getUsuarioAccesoListEagerHelper(List<UsuarioAcceso> usuarioList);
	
}
