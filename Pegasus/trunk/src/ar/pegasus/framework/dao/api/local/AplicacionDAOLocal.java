package ar.pegasus.framework.dao.api.local;

import java.util.List;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Aplicacion;
import ar.pegasus.framework.entidades.core.Usuario;

@Local
public interface AplicacionDAOLocal extends DAOLocal<Aplicacion,Integer>{
	
	public Aplicacion getDatosAplicacion(int idAplicacion) throws PException ;

	public List<Usuario> getUsuariosPorAplicacion(int idAplicacion) throws PException; 
}