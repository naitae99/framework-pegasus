package ar.pegasus.framework.componentes;

import java.io.Serializable;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * Clase utilizada para mostrar una progress bar y mostrar el progreso de un proceso.
 * Se utiliza pasando una instancia como par�metro al m�todo que se desea monitorear
 * su progreso. El m�todo que recibe como par�metro una instancia de esa clase debe
 * implementarse a trav�s de SwingWorker.
 * @see ar.pegasus.framework.MonitorIndicadorProgreso
 * @see ar.pegasus.framework.util.SwingWorker
 * @author oarias
 */
public class IndicadorProgreso implements Serializable{
	private static final long serialVersionUID = 3820607553782331121L;

	private long valorActual;
	private String comentario;
	private boolean ready;
	private boolean finished;
	private JProgressBar progressBar;
	//esta variable indica un modo de trabajo. Si el (min,max) con el que se inicializo el objeto son enteros, 
	//le pasa esos enteros a la progress bar.
	//En cambio, si le llega (min, long>Integer.MaxValue) hace una reduccion para evitar overflows aritmeticos. 
	//Tipico cuando se trabaja con archivos grandes.
	private boolean castDirectoInt = true;

	public IndicadorProgreso(JProgressBar progressBar) {
		this(null, progressBar);
	}

	public IndicadorProgreso(String comentario, JProgressBar progressBar) {
		this.comentario = comentario;
		this.progressBar = progressBar;
		this.ready = false;
		this.finished = false;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public long getValorActual() {
		return valorActual;
	}

	public void setValorActual(long valor) {
		this.valorActual = valor;
	}

	public boolean isReady() {
		return ready;
	}

	/**
	 * Con este m�todo se indica al monitor que el indicador est� listo para que se incremente
	 * la progress bar.
	 * @param minimo Valor m�nimo para la progress bar
	 * @param maximo Valor m�ximo para la progress bar
	 */
	public void setReady(long minimo, long maximo) {
		if(maximo >= Integer.MAX_VALUE) {
			castDirectoInt = false;
		}
		setValorActual(minimo);
		progressBar.setValue(getCastedInt(minimo));
		progressBar.setMinimum(getCastedInt(minimo));
		progressBar.setMaximum(getCastedInt(maximo));
		this.ready = true;
	}

	private int getCastedInt(long valor) {
		if(castDirectoInt)
			return (int)valor;
		else
			return (int)(valor / 1024); // para evitar overflows
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	/** Con este m�todo el monitor incrementa la progress bar */
	void incrementarValor() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(getCastedInt(getValorActual()));
			}
		});
	}

	/** Con este m�todo el monitor incrementa la progress bar */
	void incrementarValor(long cantidad) {
		this.valorActual += cantidad;
	}

	/** Con este m�todo el monitor incrementa la progress bar */
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
		if(finished) {
			ready = false;
		}
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

}