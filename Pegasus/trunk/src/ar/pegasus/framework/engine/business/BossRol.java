package ar.pegasus.framework.engine.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Rol;

/**
 * Clase encargada de operar con objetos <b>Rol</b> 
 * @author oarias
 */
@Stateless
//@Name("rol")
public class BossRol implements BossRolLocal, BossRolRemote {

//	@EJB
//	private RolDAOLocal rolDAO;
//	
	@EJB
	private RolUsuarioDAOLocal rolUsuarioDAO;

	/**
	 * @return Retorna el rol asociado al usuario idUsuario en la aplicación idAplicacion.
	 * @throws PException 
	 */
	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		return rolUsuarioDAO.getRolPorUsuarioAplicacion(idUsuario, idAplicacion);
	}

}