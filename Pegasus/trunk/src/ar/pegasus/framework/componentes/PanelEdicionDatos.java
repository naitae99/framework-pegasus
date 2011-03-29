package ar.pegasus.framework.componentes;

import java.util.List;

import javax.swing.JPanel;

public abstract class PanelEdicionDatos<T> extends JPanel {
	private static final long serialVersionUID = -2576763034917427772L;

	protected ValidationManager validationManager;

	private T contenido;

	public PanelEdicionDatos() {
		super(null);
		construct();
		createValidationManager();
	}


	protected abstract void construct();
	
	/**
	 * Setea el contenido actual con que se esta trabajando
	 * 
	 * @param contenido
	 */
	public void setContenido(T contenido) {
		this.contenido = contenido;
	}

	public T getContenido(){
		return contenido;
	}
	
	public void createValidationManager(){
		validationManager = new ValidationManager(this, "Error en el ingreso de datos", PJOptionPane.ERROR_MSG);
	}
	
	public abstract void setModoEdicion(boolean estado);
	
	public abstract void mostrarDatos(T contenido); 
	
	public abstract boolean validarDatos(List<T> items); 
	
	/**
	 * Permite capturar los datos que se estaban editando. 
	 * 
	 * @return El objeto con sus campos editados.
	 */
	public abstract T capturarDatos(T...contenido);

	/**
	 * Limpia los contenidos de los valores del Panel
	 */
	public abstract void clear();
}
