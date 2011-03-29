package ar.pegasus.framework.engine.business;

import javax.ejb.Remote;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Rol;

@Remote
public interface RolDAORemote {

	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException ;

}
