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
	 * Devuelve la aplicación.
	 * @return aplicacion La entidad <b>Aplicación</b>.
	 */
	public static Aplicacion getAplicacion() {
		return aplicacion;
	}

	/**
	 * Setea la aplicación.
	 * @param aplicacion La entidad <b>Aplicación</b>.
	 */
	public static void setAplicacion(Aplicacion aplicacion) {
		BossAplicacion.aplicacion = aplicacion;
	}

	/**
	 * Setea la aplicación.
	 * @param idAplicacion El nro. de id de la aplicación.
	 */
	public static void setAplicacion(int idAplicacion) {
		//Tomar los datos de la aplicacion del Portal
		BossAplicacion.idAplicacion = idAplicacion;
	}

	/**
	 * Poblamos la aplicación.
	 * @throws PException
	 */
	public static void poblarAplicacion() throws PException {
		Aplicacion aplicacion = BossLoginPortal.getInstance().getDatosAplicacion(idAplicacion);
		setAplicacion(aplicacion);
	}

	/**
	 * Devuelve el id de la aplicación.
	 * @return idAplicacion El nro. de id de la aplicación.
	 */
	public static Integer getIdAplicacion() {
		return idAplicacion;
	}

}