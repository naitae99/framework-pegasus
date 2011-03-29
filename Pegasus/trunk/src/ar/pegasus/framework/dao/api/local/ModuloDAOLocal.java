package ar.pegasus.framework.dao.api.local;

import java.util.List;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;

@Local
public interface ModuloDAOLocal extends DAOLocal<Modulo, Integer>{

	public Modulo getModulo(int idAplicacion, int idModulo) throws PException;
	
	public List<Modulo> getModulosPorAplicacion(int idAplicacion) throws PException ;

	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException ;

}