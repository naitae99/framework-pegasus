package ar.pegasus.framework.engine.business;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Rol;

@Local
public interface RolDAOLocal {

	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException ;

}
