package ar.pegasus.framework.componentes.error.notificaciones;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.error.INotificadorErrores;

public class NotificadorErroresConsola implements INotificadorErrores {

	private static NotificadorErroresConsola instance;

	private NotificadorErroresConsola() {
	}

	public static NotificadorErroresConsola getInstance() {
		if(instance == null) {
			instance = new NotificadorErroresConsola();
		}
		return instance;
	}

	public void notificarError(PException e) {
		e.printStackTrace();
	}

}