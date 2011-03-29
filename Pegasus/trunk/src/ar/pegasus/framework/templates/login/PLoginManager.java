package ar.pegasus.framework.templates.login;


import java.util.List;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;

public abstract class PLoginManager implements ILoginManager {

	protected int idAplicacion;
	protected List<Modulo> modulosUsuario;
	protected List<Modulo> modulosAplicacion;

	protected PLoginManager(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public abstract List<Modulo> getModulosUsuario() throws PException;

	public abstract List<Modulo> getModulosAplicacion() throws PException;

}