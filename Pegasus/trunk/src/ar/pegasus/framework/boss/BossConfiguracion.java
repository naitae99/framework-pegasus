package ar.pegasus.framework.boss;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.configuracion.EnumParametros;
import ar.pegasus.framework.facade.api.remote.ConfiguracionFacadeRemote;
import ar.pegasus.framework.util.BeanFactoryGeneralRemote;
import ar.pegasus.framework.util.Configuracion;

/**
 * Clase encargada de configurar a los otros Boss, por ejemplo con par�metros necesarios
 * para acceder a Web Services o con propiedades personalizadas para cada usuario. 
 * @author AGEA S.A.
 */
/**
 * Versi�n preliminar para que levante el desktop, cuando tengamos la versi�n definitiva del manejo de la
 * configuraci�n esta clase creo que no va a ser necesaria.
 */
public class BossConfiguracion {

	private static BossConfiguracion instance ;

	public static BossConfiguracion getInstance() throws PException {
		if (instance == null) {
			instance = new BossConfiguracion();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	private ConfiguracionFacadeRemote<EnumParametros> getConfiguracionFacade() throws PException {
		return BeanFactoryGeneralRemote.getInstance().getBean(ConfiguracionFacadeRemote.class);
	}

	private BossConfiguracion() throws PException {

		// Poblar el objecto Configuracion
		// Login
		Configuracion.setLoginEndpoint(getConfiguracionFacade().get(EnumParametros.LOGIN_ENDPOINT));
		Configuracion.setLoginNombre(getConfiguracionFacade().get(EnumParametros.NOMBRE_SERVICIO_LOGIN));
		// Mail
		Configuracion.setMailEndpoint(getConfiguracionFacade().get(EnumParametros.MAIL_ENDPOINT));
		Configuracion.setMailNombre(getConfiguracionFacade().get(EnumParametros.NOMBRE_SERVICIO_MAIL));
		// Publicidad
		// Transferencia
//		Configuracion.setTransferenciaEndpoint(getConfiguracionFacade().get(EnumParametros.TRANSFERENCIA_ARCHIVOS_ENDPOINT));
//		Configuracion.setTransferenciaNombre(getConfiguracionFacade().get(EnumParametros.NOMBRE_SERVICIO_TRANSFERENCIA_ARCHIVOS));
		// FTP
//		Configuracion.setFtpServer(getConfiguracionFacade().get(EnumParametros.TRANSFERENCIA_ARCHIVOS_FTP_SERVER));
//		Configuracion.setFtpUsuario(getConfiguracionFacade().get(EnumParametros.TRANSFERENCIA_ARCHIVOS_FTP_USER));
//		Configuracion.setFtpPassword(getConfiguracionFacade().get(EnumParametros.TRANSFERENCIA_ARCHIVOS_FTP_PASSWORD));
		// Clasificados
		// Red1
		// Direccion de notificacion de errores
		Configuracion.setMailNotiError(getConfiguracionFacade().get(EnumParametros.MAIL_NOTI_ERROR));
	}

}