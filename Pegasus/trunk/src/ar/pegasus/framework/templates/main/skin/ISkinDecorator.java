package ar.pegasus.framework.templates.main.skin;

import java.awt.Color;
import java.awt.Font;

/**
 * Interface para crear un decorador para un skin.
 * Util para definir propiedades gráficas extra, por ejemplo a través
 * de la clase javax.swing.UIManager.
 * @author AGEA S.A.
 */
public interface ISkinDecorator {

	/**
	 * Inicializa el decorador del skin.
	 * En éste método pueden definirse propiedades gráficas extra a través de la
	 * clase UIManager.
	 * @see javax.swing.UIManager
	 */
	public abstract void init();

	/**
	 * @return La tipografía por defecto.
	 * @see java.awt.Font
	 */
	public abstract Font getDefaultFont();

	/**
	 * @return La tipografía alternativa.
	 * @see java.awt.Font
	 */
	public abstract Font getSecondaryFont();

	/**
	 * @return El ícono de la ventana.
	 */
	public abstract String getIconoVentana();

	/**
	 * @return La imágen que se muestra en el cuadro de login.
	 */
	public abstract String getIconoLogin();

	/**
	 * @return El color de la barra de título de las ventanas.
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
	 * @return El color de los ítems (de una lista o árbol por ejemplo) en su estado normal.
	 * @see java.awt.Color
	 */
	public abstract Color getColorItemNormal();

	/**
	 * @return El color de los ítems (de una lista o árbol por ejemplo) en su estado resaltado.
	 * @see java.awt.Color
	 */
	public abstract Color getColorItemResaltado();

	/**
	 * @return El color interno de las cajas de texto (textfield, textarea, etc.).
	 * @see java.awt.Color
	 */
	public abstract Color getColorCajaTexto();

}