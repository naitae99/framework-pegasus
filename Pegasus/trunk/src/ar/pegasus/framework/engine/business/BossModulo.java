package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.ModuloDAOLocal;
import ar.pegasus.framework.entidades.core.Modulo;

@Stateless
@Name("modulo")
public class BossModulo implements BossModuloRemote {

	@EJB
	private ModuloDAOLocal moduloDAO;

	public Modulo getModulo(int idAplicacion, int idModulo) throws PException {
		return moduloDAO.getModulo(idAplicacion, idModulo);
	}
	
	public List<Modulo> getModulosPorAplicacion(int idAplicacion) throws PException {
		return moduloDAO.getModulosPorAplicacion(idAplicacion) ;
	}

	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException {
		return moduloDAO.getModulosPorUsuarioAplicacion(idUsuario, idAplicacion) ;
	}

}