package ar.pegasus.framework.engine.business;

import java.util.List;

import javax.ejb.Remote;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;

@Remote
public interface BossModuloRemote {

	public Modulo getModulo(int idAplicacion, int idModulo) throws PException;
	
	public List<Modulo> getModulosPorAplicacion(int idAplicacion) throws PException ;

	public List<Modulo> getModulosPorUsuarioAplicacion(int idUsuario, int idAplicacion) throws PException ;

}
