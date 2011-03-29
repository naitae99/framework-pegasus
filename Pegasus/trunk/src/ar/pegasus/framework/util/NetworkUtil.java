package ar.pegasus.framework.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;

public class NetworkUtil {

	private static InetAddress getLocalHost() throws PException {
		try {
			return InetAddress.getLocalHost();
		} catch(UnknownHostException e) {
			throw new PException(BossError.ERR_CONEXION, "No se pudo obtener la direcci�n del equipo.", "No se pudo obtener la direcci�n del equipo.", e,
					new String[] { "Verifique la configuraci�n de red del equipo." });
		}
	}

	/**
	 * 
	 * @return el nombre del equipo en el cual se est� ejecutando la aplicaci�n.
	 * @throws PException
	 */
	public static String getNombreLocalHost() throws PException {
		return getLocalHost().getHostName();
	}

	/**
	 * 
	 * @return el ip del equipo en el cual se est� ejecutando la aplicaci�n.
	 * @throws PException
	 */
	public static String getIpLocalHost() throws PException {
		return getLocalHost().getHostAddress();
	}

	/**
	 * @param host el nombre del host.
	 * @return el ip de host.
	 * @throws PException
	 */
	public static InetAddress getIpEquipo(String host) throws PException {
		try {
			return InetAddress.getByName(host);
		} catch(UnknownHostException e) {
			throw new PException(BossError.ERR_CONEXION, "No se pudo obtener la dirección del equipo (" + host + ").", "No se pudo obtener la dirección del equipo (" + host + ").",e,
					new String[] { "Verifique el nombre del equipo.", "Verifique el DNS." });
		}
	}

}