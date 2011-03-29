package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.Stateless;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.impl.GenericDAO;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.entidades.core.RolUsuario;

@Stateless
public class RolUsuarioDAO extends GenericDAO<RolUsuario, Integer> implements RolUsuarioDAOLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getRolesPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {

		List<Rol> roles = getEntityManager().createNativeQuery(  " select rol.idRol,rol.nombre,rol.descripcion " +
															"   from ModuloAplicacion ma " +
															    "   inner join RolModulo rm on ma.F_IdModulo = rm.F_IdModulo " +
															    "   inner join RolUsuario ru on ru.F_IdRol = rm.F_IdRol " +
															    "   inner join Usuario usuario ON ru.F_IdUsuario = usuario.idUsuario " +
															    "   inner join Rol rol ON rm.F_IdRol = rol.idRol " +
														    " where  ma.F_IdAplicacion = :idAplicacion " +
														    "  and usuario.idUsuario = :idUsuario ",ar.pegasus.framework.entidades.core.Rol.class)
											.setParameter("idUsuario", idUsuario)
											.setParameter("idAplicacion", idAplicacion)
											.getResultList();															    
		return roles;
	}

	@Override
	public Rol getRolPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		List<Rol> roles = getRolesPorUsuarioAplicacion(idUsuario, idAplicacion);
        return ((roles == null) || (roles.isEmpty())) ? null : roles.get(0);
	}

}
