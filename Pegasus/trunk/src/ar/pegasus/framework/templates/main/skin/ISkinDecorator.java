package ar.pegasus.framework.templates.main.skin;

import java.awt.Color;
import java.awt.Font;

/**
 * Interface para crear un decorador para un skin.
 * Util para definir propiedades gr�ficas extra, por ejemplo a trav�s
 * de la clase javax.swing.UIManager.
 * @author AGEA S.A.
 */
public interface ISkinDecorator {

	/**
	 * Inicializa el decorador del skin.
	 * En �ste m�todo pueden definirse propiedades gr�ficas extra a trav�s de la
	 * clase UIManager.
	 * @see javax.swing.UIManager
	 */
	public abstract void init();

	/**
	 * @return La tipograf�a por defecto.
	 * @see java.awt.Font
	 */
	public abstract Font getDefaultFont();

	/**
	 * @return La tipograf�a alternativa.
	 * @see java.awt.Font
	 */
	public abstract Font getSecondaryFont();

	/**
	 * @return El �cono de la ventana.
	 */
	public abstract String getIconoVentana();

	/**
	 * @return La im�gen que se muestra en el cuadro de login.
	 */
	public abstract String getIconoLogin();

	/**
	 * @return El color de la barra de t�tulo de las ventanas.
	 * @see java.awt.Color
	 */
	public abstract Color getColorBarraTituloVentana();

	/**
	 * @return El color de fondo de las ventanas.
	 * @see java.awt.Color
	 */
	public abstract Color getColorFondoVentana();

	/**
	 * @return El color de fondo de los paneles.
	 * @see java.awt.Color
	 */
	public abstract Color getColorFondoPanel();

	/**
	 * @return El color por defecto de los componentes en su estado normal.
	 * @see java.awt.Color
	 */
	public abstract Color getColorComponenteNormal();

	/**
	 * @return El color por defecto de los componentes en estado rollover.
	 * @see java.awt.Color
	 */
	public abstract Color getColorComponenteRollover();

	/**
	 * @return El color por defecto de los componentes en estado deshabilitado.
	 * @see java.awt.Color
	 */
	public abstract Color getColorComponenteDeshabilitado();

	/**
	 * @return El color por defecto de los componentes en estado seleccionado.
	 * @see java.awt.Color
	 */
	public abstract Color getColorComponenteSeleccionado();

	/**
	 * @return El color de los encabezados de las tablas.
	 * @see java.awt.Color
	 */
	public abstract Color getColorHeaderTabla();

	/**
	 * @return El color de los �tems (de una lista o �rbol por ejemplo) en su estado normal.
	 * @see java.awt.Color
	 */
	public abstract Color getColorItemNormal();

	/**
	 * @return El color de los �tems (de una lista o �rbol por ejemplo) en su estado resaltado.
	 * @see java.awt.Color
	 */
	public abstract Color getColorItemResaltado();

	/**
	 * @return El color interno de las cajas de texto (textfield, textarea, etc.).
	 * @see java.awt.Color
	 */
	public abstract Color getColorCajaTexto();

}