package ar.pegasus.framework.util;

public class Configuracion {

	private static void setProperty(String key, String value) {
		if(value != null) {
			System.setProperty(key, value);
		}
	}

	//Login
	public static String getLoginEndpoint() {
		return System.getProperty("ar.pegasus.login_endpoint");
	}

	public static void setLoginEndpoint(String loginEndpoint) {
		setProperty("ar.pegasus.login_endpoint", loginEndpoint);
	}

	public static String getLoginNombre() {
		return System.getProperty("ar.pegasus.login_nombre");
	}

	public static void setLoginNombre(String loginNombre) {
		setProperty("ar.pegasus.login_nombre", loginNombre);
	}

	//Publicidad
	public static String getPublicidadEndpoint() {
		return System.getProperty("ar.pegasus.publicidad_endpoint");
	}

	public static void setPublicidadEndpoint(String publicidadEndpoint) {
		setProperty("ar.pegasus.publicidad_endpoint", publicidadEndpoint);
	}

	public static String getPublicidadNombre() {
		return System.getProperty("ar.pegasus.publicidad_nombre");
	}

	public static void setPublicidadNombre(String publicidadNombre) {
		setProperty("ar.pegasus.publicidad_nombre", publicidadNombre);
	}

	//Mail
	public static String getMailEndpoint() {
		return System.getProperty("ar.pegasus.mail_endpoint");
	}

	public static void setMailEndpoint(String mailEndpoint) {
		setProperty("ar.pegasus.mail_endpoint", mailEndpoint);
	}

	public static String getMailNombre() {
		return System.getProperty("ar.pegasus.mail_nombre");
	}

	public static void setMailNombre(String mailNombre) {
		setProperty("ar.pegasus.mail_nombre", mailNombre);
	}

	//Transferencia
	public static String getTransferenciaEndpoint() {
		return System.getProperty("ar.pegasus.transferencia_endpoint");
	}

	public static void setTransferenciaEndpoint(String transferenciaEndpoint) {
		setProperty("ar.pegasus.transferencia_endpoint", transferenciaEndpoint);
	}

	public static String getTransferenciaNombre() {
		return System.getProperty("ar.pegasus.transferencia_nombre");
	}

	public static void setTransferenciaNombre(String transferenciaNombre) {
		setProperty("ar.pegasus.transferencia_nombre", transferenciaNombre);
	}

	//FTP
	public static String getFtpServer() {
		return System.getProperty("ar.pegasus.ftp_server");
	}

	public static void setFtpServer(String ftpServer) {
		setProperty("ar.pegasus.ftp_server", ftpServer);
	}

	public static String getFtpUsuario() {
		return System.getProperty("ar.pegasus.ftp_usuario");
	}

	public static void setFtpUsuario(String ftpUsuario) {
		setProperty("ar.pegasus.ftp_usuario", ftpUsuario);
	}

	public static String getFtpPassword() {
		return System.getProperty("ar.pegasus.ftp_password");
	}

	public static void setFtpPassword(String ftpPassword) {
		setProperty("ar.pegasus.ftp_password", ftpPassword);
	}

	//Clasificados
	public static String getClasificadosEndpoint() {
		return System.getProperty("ar.pegasus.clasificados_endpoint");
	}

	public static void setClasificadosEndpoint(String clasificadosEndpoint) {
		setProperty("ar.pegasus.clasificados_endpoint", clasificadosEndpoint);
	}

	public static String getClasificadosNombre() {
		return System.getProperty("ar.pegasus.clasificados_nombre");
	}

	public static void setClasificadosNombre(String clasificadosNombre) {
		setProperty("ar.pegasus.clasificados_nombre", clasificadosNombre);
	}

	//General
	public static String getRaizAlmacenamiento() {
		return System.getProperty("ar.pegasus.raizalmacenamiento");
	}

	public static void setRaizAlmacenamiento(String raizAlmacenamiento) {
		setProperty("ar.pegasus.raizalmacenamiento", raizAlmacenamiento);
	}

	public static String getRutaPdfIn() {
		return System.getProperty("ar.pegasus.rutapdfin");
	}

	public static void setRutaPdfIn(String rutaPdfIn) {
		setProperty("ar.pegasus.rutapdfin", rutaPdfIn);
	}

	public static String getRutaPdfOut() {
		return System.getProperty("ar.pegasus.rutapdfout");
	}

	public static void setRutaPdfOut(String rutaPdfOut) {
		setProperty("ar.pegasus.rutapdfout", rutaPdfOut);
	}

	public static String getRutaTempAutomata() {
		return System.getProperty("ar.pegasus.rutatempautomata");
	}

	public static void setRutaTempAutomata(String rutaTempAutomata) {
		setProperty("ar.pegasus.rutatempautomata", rutaTempAutomata);
	}

	public static String getRutaTempLocal() {
		return System.getProperty("ar.pegasus.rutatemplocal");
	}

	public static void setRutaTempLocal(String rutaTempLocal) {
		setProperty("ar.pegasus.rutatemplocal", rutaTempLocal);
	}

	public static String getDireccionRespuesta() {
		return System.getProperty("ar.pegasus.direccionrespuesta");
	}

	public static void setDireccionRespuesta(String direccionRespuesta) {
		setProperty("ar.pegasus.direccionrespuesta", direccionRespuesta);
	}

	public static boolean isNotificarResponsablesMedio() {
		return Boolean.valueOf(System.getProperty("ar.pegasus.notificarresponsablesmedio"));
	}

	public static void setNotificarResponsablesMedio(boolean notificarResponsablesMedio) {
		setProperty("ar.pegasus.notificarresponsablesmedio", String.valueOf(notificarResponsablesMedio));
	}

	public static boolean isNotificarResponsablesSap() {
		return Boolean.valueOf(System.getProperty("ar.pegasus.notificarresponsablessap"));
	}

	public static void setNotificarResponsablesSap(String notificarResponsablesSap) {
		setProperty("ar.pegasus.notificarresponsablessap", String.valueOf(notificarResponsablesSap));
	}

	//Red1
	public static String getRed1Endpoint() {
		return System.getProperty("ar.pegasus.red1_endpoint");
	}

	public static void setRed1Endpoint(String red1Endpoint) {
		setProperty("ar.pegasus.red1_endpoint", red1Endpoint);
	}

	public static String getRed1Nombre() {
		return System.getProperty("ar.pegasus.red1_nombre");
	}

	public static void setRed1Nombre(String red1Nombre) {
		setProperty("ar.pegasus.red1_nombre", red1Nombre);
	}

	public static String getMailNotiError() {
		return System.getProperty("ar.pegasus.direccionNoficacionErrores");
	}

	public static void setMailNotiError(String mailNotiError) {
		setProperty("ar.pegasus.direccionNoficacionErrores", mailNotiError);
	}

}