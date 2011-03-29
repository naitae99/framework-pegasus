package ar.pegasus.framework.componentes.error;

import ar.pegasus.framework.componentes.PException;

public interface INotificadorErrores {

	public abstract void notificarError(PException e);
}
