package ar.pegasus.framework.componentes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Renderer de una lista. Requiere que los elementos de dicha lista implementen la 
 * interfaz Remarkable.
 * Los elementos notables (isRemarkable() == true) son resaltados.
 * @author oarias
 */
public class PRemarkableListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 569481310541226799L;

	private final Color remarkableColor;
	private final Color normalColor;

	/**
	 * Los elementos notables se mostraran en color rojo.
	 */
	public PRemarkableListRenderer() {
		this(Color.GRAY);
	}

	/**
	 * Crea un renderer.
	 * No se setear� el color de los elementos no notables.
	 * @param remarkableColor el color con el que se mostraran los elementos notables.
	 */
	public PRemarkableListRenderer(Color remarkableColor) {
		this(remarkableColor, null);
	}

	/**
	 * @param remarkableColor el color con el que se mostraran los elementos notables.
	 * @param normalColor el color con el que se mostraran los elementos normales (no notables).
	 */
	public PRemarkableListRenderer(Color remarkableColor, Color normalColor) {
		this.remarkableColor = remarkableColor;
		this.normalColor = normalColor;
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if(value != null && ((Remarkable)value).isRemarkable()) {
			component.setForeground(remarkableColor);
		} else {
			if(normalColor != null) {
				component.setForeground(normalColor);
			}
		}
		return component;
	}

}