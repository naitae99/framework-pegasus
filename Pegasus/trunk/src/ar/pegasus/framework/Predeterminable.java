package ar.pegasus.framework;

public interface Predeterminable {

	/**
	 * M�todo que permite setear el estado de <b>predeterminado</b>.
	 * @param predeterminado
	 */
	void setPredeterminado(boolean predeterminado);

	/**
	 * @return <b>true</b> si el estado es <b>predeterminado</b>.
	 */
	boolean isPredeterminado();

}