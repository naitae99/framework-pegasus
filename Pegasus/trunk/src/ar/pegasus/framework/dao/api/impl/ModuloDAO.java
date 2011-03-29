package ar.pegasus.framework.dao.api.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.configuracion.EnumParametros;
import ar.pegasus.framework.dao.api.local.AplicacionDAOLocal;
import ar.pegasus.framework.dao.api.local.ModuloDAOLocal;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.facade.api.local.ConfiguracionFacadeLocal;

@Stateless
@Name("moduloDAO")
public class ModuloDAO extends GenericDAO<Modulo, Integer> implements ModuloDAOLocal {

	@EJB
	private ConfiguracionFacadeLocal<EnumParametros> configuracionFacade ;

	@EJB
	private AplicacionDAOLocal aplicacionDAO ;

	public Modulo getModulo(int idAplicacion, int idModulo) throws PException {
		List<Modulo> modulos = getModulosPorAplicacion(idAplicacion);
		for(Modulo modulo : modulos) {
			if(modulo.getIdModulo() == idModulo) {
				return modulo;
			}
		}
		return null;
	}

	public List<Modulo> getModulosPorAplicacion(int idAplicacion) throws PException {
		return aplicacionDAO.getDatosAplicacion(idAplicacion).getModulos();
	}

	@SuppressWarnings("unchecked")
	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		List<Modulo> modulos = getEntityManager().createNativeQuery( "select mo.* from RolUsuario ru,"+
														   " RolModulo rm, Modulo mo,ModuloAplicacion ma "+
															" where ru.F_IdUsuario = :idUsuario "+
															" and rm.F_IdRol = ru.F_IdRol "+
															" and rm.F_IdModulo = mo.p_IdModulo "+
															" and ma.F_IdModulo = mo.p_IdModulo  "+
															" and ma.F_IdAplicacion = :idAplicacion "+
															" order by mo.orden, mo.nombre " ,ar.pegasus.framework.entidades.core.Modulo.class)
										.setParameter("idUsuario", idUsuario)
										.setParameter("idAplicacion", idAplicacion)
										.getResultList();															    
		return modulos;
	}

}