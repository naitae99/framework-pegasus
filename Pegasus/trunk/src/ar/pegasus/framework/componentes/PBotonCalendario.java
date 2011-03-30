package ar.pegasus.framework.componentes;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.Icon;
import javax.swing.JButton;

import ar.pegasus.framework.util.DecorateUtil;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaElementos;

/**
 * Componente que muestra un botón que al ser presionado muestra un PPopupCalendar.
 * @author oarias
 * @see PPopupCalendar
 */
public class PBotonCalendario extends JButton {
	private static final long serialVersionUID = 6294681325354031977L;

	public static String iconoCalendario = null;
	public static String iconoCalendarioDeshab = null;
	private PPopupCalendar calendario;
	private static final String DEFAULT_TITLE = "Calendario";
	public final int DEFAULT_BUTTON_WIDTH = 20;
	public final int DEFAULT_BUTTON_HEIGHT = 20;

	/** M�todo constructor */
	public PBotonCalendario() {
		super();
		construct();
		calendario = new PPopupCalendar(null, DEFAULT_TITLE);
		getCalendario().select();
	}

	public PBotonCalendario(Date fechaDefaultSeleccionada) {
		super();
		construct();
		calendario = new PPopupCalendar(null, DEFAULT_TITLE, fechaDefaultSeleccionada);
		//getCalendario().select();Importante: No descomentar. Esto selecciona la fecha de hoy, justo lo que no quiero hacer.
	}

	public PBotonCalendario(Frame owner) {
		super();
		construct();
		calendario = new PPopupCalendar(owner, DEFAULT_TITLE);
		getCalendario().select();
	}

	public PBotonCalendario(Frame owner, Date fechaMinima, Date fechaMaxima) {
		super();
		construct();
		calendario = new PPopupCalendar(owner, DEFAULT_TITLE, fechaMinima, fechaMaxima);
		getCalendario().select();
	}

	public PBotonCalendario(Date fechaMinima, Date fechaMaxima) {
		super();
		construct();
		calendario = new PPopupCalendar(null, DEFAULT_TITLE, fechaMinima, fechaMaxima);
		getCalendario().select();
	}

	/**
	 * M�todo constructor.
	 * @param iconoCalendario El icono del bot�n.
	 * @param iconoCalendarioDeshab El icono del bot�n en estado deshabilitado.
	 */
	public PBotonCalendario(String iconoCalendario, String iconoCalendarioDeshab) {
		this();
		setIconoCalendario(iconoCalendario);
		setIconoCalendarioDeshabilitado(iconoCalendarioDeshab);
	}

	/**
	 * M�todo constructor.
	 * @param calendario El popup calendar.
	 */
	public PBotonCalendario(PPopupCalendar calendario) {
		super();
		construct();
		this.calendario = calendario;
		getCalendario().select();
	}

	//Construye gr�ficamente el componente
	private void construct() {
		String icono = (iconoCalendario == null ? RutaElementos.DEFAULT_ICONO_CALENDARIO : iconoCalendario);
		String iconoDeshab = (iconoCalendarioDeshab == null ? RutaElementos.DEFAULT_ICONO_CALENDARIO_DESHAB : iconoCalendarioDeshab);
		DecorateUtil.decorateButton(this, icono, iconoDeshab);
		setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		setToolTipText("Mostrar calendario");
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				calendario.setVisible(true);
				if(!calendario.isCanceled()) {
					botonCalendarioPresionado();
				}
			}
		});
	}

	/**
	 * Devuelve el calendario asociado al cuadro de di�logo.
	 * @return Una instancia de CLCalendar.
	 * @see ar.PCalendar.fwjava.componentes.CLCalendar
	 */
	public PCalendar getCalendario() {
		return calendario.getCalendar();
	}

	/**
	 * Devuelve el icono del bot�n.
	 * @return iconoCalendario
	 */
	public String getIconoCalendario() {
		return iconoCalendario;
	}

	/**
	 * Setea el icono del bot�n.
	 * @param iconoCalendario
	 */
	public void setIconoCalendario(String iconoCalendario) {
		PBotonCalendario.iconoCalendario = iconoCalendario;
		Icon icon = ImageUtil.loadIcon(iconoCalendario);
		super.setIcon(icon);
		super.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}

	/**
	 * Devuelve el icono del bot�n en estado deshabilitado.
	 * @return iconoCalendarioDeshab
	 */
	public String getIconoCalendarioDeshabilitado() {
		return iconoCalendarioDeshab;
	}

	/**
	 * Setea el icono del bot�n en estado deshabilitado.
	 * @param iconoCalendarioDeshab
	 */
	public void setIconoCalendarioDeshabilitado(String iconoCalendarioDeshab) {
		PBotonCalendario.iconoCalendarioDeshab = iconoCalendarioDeshab;
		super.setDisabledIcon(ImageUtil.loadIcon(iconoCalendarioDeshab));
		super.setIcon(ImageUtil.loadIcon(iconoCalendarioDeshab));
	}

	/**
	 * M�todo invocado cuando se produce el evento de click del bot�n.
	 * M�todo vac�o para ser sobreescrito.
	 */
	public void botonCalendarioPresionado() {
	}

}