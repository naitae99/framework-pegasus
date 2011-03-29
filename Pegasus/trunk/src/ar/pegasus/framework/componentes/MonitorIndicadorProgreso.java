package ar.pegasus.framework.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

import ar.pegasus.framework.Comentable;

/**
 * Clase utilizada para monitorear un indicador de progreso e incrementar su
 * progress bar cada determinado ciclo de tiempo.
 * @author oarias
 */
public class MonitorIndicadorProgreso implements Serializable{
	private static final long serialVersionUID = -2836180251631516325L;
	
	private IndicadorProgreso indicadorProgreso;
	private Timer timer;
	private Comentable comentable;
	private String viejoComentario;
	private static final int CICLO_POOLING_DEFAULT = 10;

	/**
	 * Método Constructor.
	 * @param indicadorProgreso Indicador a monitorear.
	 */
	public MonitorIndicadorProgreso(IndicadorProgreso indicadorProgreso) {
		this(indicadorProgreso, CICLO_POOLING_DEFAULT);
	}

	/**
	 * Método Constructor.
	 * @param indicadorProgreso Indicador a monitorear.
	 * @param cicloPooling Período de tiempo (MS) en el que se vuelve incrementa la progress bar del indicador.
	 */
	public MonitorIndicadorProgreso(IndicadorProgreso indicadorProgreso, int cicloPooling) {
		this.indicadorProgreso = indicadorProgreso;
		this.timer = new Timer(cicloPooling, new TimerActionListener());
		this.viejoComentario = "";
	}

	/**
	 * Método Constructor.
	 * @param indicadorProgreso Indicador a monitorear.
	 * @param cicloPooling Período de tiempo (MS) en el que se vuelve incrementa la progress bar del indicador.
	 * @param comentable Instancia de una clase cliente donde se desea volcar el contenido del comentario que escribe el indicador de progreso.
	 */
	public MonitorIndicadorProgreso(IndicadorProgreso indicadorProgreso, int cicloPooling, Comentable comentable) {
		this(indicadorProgreso, cicloPooling);
		this.comentable = comentable;
	}

	/** Para empezar a monitorear el progreso */
	public void start() {
		timer.start();
	}

	private void setComentario(String comentario) {
		if(comentable != null) {
			if(viejoComentario != null && comentario != null && viejoComentario.compareTo(comentario) != 0) {
				comentable.setComentario(comentario);
				viejoComentario = comentario;
			}
		}
	}

	private class TimerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!indicadorProgreso.isFinished()) {
				if(indicadorProgreso.isReady()) {
					indicadorProgreso.incrementarValor();
				}
				setComentario(indicadorProgreso.getComentario());
			} else {
				indicadorProgreso.incrementarValor();
				setComentario(indicadorProgreso.getComentario());
				timer.stop();
			}
		}
	}

}