package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.DAOLocal;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.entidades.core.RolUsuario;

@Local
public interface RolUsuarioDAOLocal  extends DAOLocal<RolUsuario, Integer>{
	
	public List<Rol> getRolesPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException;

	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException; 

}
