package ar.pegasus.framework.templates.config;

import ar.pegasus.framework.componentes.PException;

public interface IConfigClienteManager {

	public abstract void cargarConfiguracionCliente() throws PException;

	public abstract void guardarConfiguracionCliente() throws PException;

}