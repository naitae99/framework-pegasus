package ar.pegasus.framework.boss;

import java.net.URL;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;

/**
 * Enumerador de aplicaciones.
 * @author oarias
 */
public enum EnumAplicacionGeneral {

    VETERINARIA     ( 1,   "Veterinaria.jar",     "Veterinaria"),
    CLASIFICADOR    ( 2,   "Clasificador.jar",    "Clasificador"),
    VETERINARIA_WEB ( 3,   "VeterinariaWeb.jar",  "VeterinariaWeb"),
    CLASIFICADOR_WEB( 4,   "ClasificadorWeb.jar", "ClasificadorWeb"),
	;

	private Integer idAplicacion;
	private String serverJarName;
	private String earName;

	/**
	 * 
	 * @param idAplicacion id de la aplicación en el Portal
	 * @param serverJarName nombre del jar utilizado en el deploy
	 * @param earName nombre del .ear (nombre a utilizar como prefijo en los lookups)
	 */
	private EnumAplicacionGeneral(Integer idAplicacion, String serverJarName, String earName) {
		this.idAplicacion = idAplicacion;
		this.serverJarName = serverJarName;
		this.earName = earName;
	}

	public static String getBaseJndiName() throws PException {
		EnumAplicacionGeneral enumAplicacionJEE  = getEnumAplicacionJEE() ;
		if (enumAplicacionJEE.getEarName() == null) {
			throw new PException (BossError.ERR_APLICACION, "No se pudo determinar el nombre del ear de la aplicacion que se está ejecutando", new String[] {"Indicar el nombre del .ear de la aplicación en " + getCanonicalName() + "."}) ;
		}
		return enumAplicacionJEE.getEarName() + "/";
	}

	public static Integer getIdAplicacionPortal() throws PException {
		EnumAplicacionGeneral enumAplicacionJEE  = getEnumAplicacionJEE() ;
		if (enumAplicacionJEE.getIdAplicacion() == null) {
			throw new PException (BossError.ERR_APLICACION, "No se pudo determinar el nombre del ear de la aplicacion que se está ejecutando", new String[] {"Indicar el id de la aplicación en " + getCanonicalName() + "."}) ;
		}
		return enumAplicacionJEE.getIdAplicacion() ;
	}

	private static EnumAplicacionGeneral getEnumAplicacionJEE() throws PException {
		URL url = EnumAplicacionGeneral.class.getClassLoader().getResource(getResourceLocation());
		String serverJar = url.toString() ;
		for (EnumAplicacionGeneral enumAplicacionJEE : values()) {
			if(serverJar.indexOf(enumAplicacionJEE.getServerJarName() + "!") != -1) {
				return enumAplicacionJEE ;
			}
		}
		throw new PException (BossError.ERR_APLICACION, "No se pudo determinar la aplicacion que se está ejecutando (" + serverJar + ")", new String[] {"Verificar la información de la aplicación en " + getCanonicalName() + "."}) ;		
	}

	public Integer getIdAplicacion() {
		return idAplicacion;
	}

	private String getServerJarName() {
		return serverJarName;
	}

	public String getEarName() {
		return earName;
	}

	private static String getResourceLocation() {
		return getCanonicalName().replace('.', '/') + ".class";
	}
	private static String getCanonicalName() {
		return EnumAplicacionGeneral.class.getCanonicalName() ;
	}
	
	public static EnumAplicacionGeneral getValue(int id) {
		if (id == VETERINARIA.getIdAplicacion()) {
			return VETERINARIA;
		} 
		if (id == CLASIFICADOR.getIdAplicacion()) {
			return CLASIFICADOR;
		} 
		if (id == VETERINARIA_WEB.getIdAplicacion()) {
			return VETERINARIA_WEB;
		} 
		if (id == CLASIFICADOR_WEB.getIdAplicacion()) {
			return CLASIFICADOR_WEB;
		} 
		
		throw new IllegalArgumentException("No existe un tipo de medio con el id:" + id);
	}


}