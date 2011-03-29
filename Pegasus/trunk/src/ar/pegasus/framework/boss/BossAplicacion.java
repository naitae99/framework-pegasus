package ar.pegasus.framework.boss;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Aplicacion;

/**
 * Clase encargada de operar con objetos <b>Aplicacion</b> ya sea para consultar o grabar en la DB.
 * @author AGEA S.A.
 */
public class BossAplicacion {

	private static Aplicacion aplicacion;
	private static Integer idAplicacion;

	/**
	 * Devuelve la aplicaci�n.
	 * @return aplicacion La entidad <b>Aplicaci�n</b>.
	 */
	public static Aplicacion getAplicacion() {
		return aplicacion;
	}

	/**
	 * Setea la aplicaci�n.
	 * @param aplicacion La entidad <b>Aplicaci�n</b>.
	 */
	public static void setAplicacion(Aplicacion aplicacion) {
		BossAplicacion.aplicacion = aplicacion;
	}

	/**
	 * Setea la aplicaci�n.
	 * @param idAplicacion El nro. de id de la aplicaci�n.
	 */
	public static void setAplicacion(int idAplicacion) {
		//Tomar los datos de la aplicacion del Portal
		BossAplicacion.idAplicacion = idAplicacion;
	}

	/**
	 * Poblamos la aplicaci�n.
	 * @throws PException
	 */
	public static void poblarAplicacion() throws PException {
		Aplicacion aplicacion = BossLoginPortal.getInstance().getDatosAplicacion(idAplicacion);
		setAplicacion(aplicacion);
	}

	/**
	 * Devuelve el id de la aplicaci�n.
	 * @return idAplicacion El nro. de id de la aplicaci�n.
	 */
	public static Integer getIdAplicacion() {
		return idAplicacion;
	}

}