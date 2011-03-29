package ar.pegasus.framework.auditoria;

public class AuditoriaException extends Exception {
	private static final long serialVersionUID = -8912121744229365554L;

	String mensajeSistema;

	public AuditoriaException(String mensajeUsuario, String mensajeSistema, Throwable cause) {
		super(mensajeUsuario, cause);
		this.mensajeSistema = mensajeSistema;
	}

	public AuditoriaException(String mensajeUsuario, String mensajeSistema) {
		super(mensajeUsuario);
		this.mensajeSistema = mensajeSistema;
	}

	public String getMensajeSistema() {
		return mensajeSistema;
	}
}
