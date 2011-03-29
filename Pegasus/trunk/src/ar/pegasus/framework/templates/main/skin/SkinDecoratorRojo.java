package ar.pegasus.framework.templates.main.skin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import ar.pegasus.framework.componentes.PCalendar;

public class SkinDecoratorRojo implements ISkinDecorator {

	//Imágenes
	private static final String ICONO_VENTANA = "ar/pegasus/framework/imagenes/ico_logo_trans.png";
	private static final String ICONO_LOGIN = "ar/pegasus/framework/imagenes/logo_login_trans.png";
	//Colores
	private static final Color COLOR_ROJO = new Color(154, 0, 0);
	private static final Color COLOR_NARANJA = new Color(231, 99, 16);
	private static final Color COLOR_MARRON_FONDO_VENTANA = new Color(239, 238, 228);
	private static final Color COLOR_GRIS_BARRA_TITULO = new Color(119, 126, 138);
	private static final Color COLOR_MARRON_HEADER_TABLA = new Color(194, 193, 169);

	public void init() {
		//Button
		UIManager.put("Button.foreground", Color.WHITE);
		//TitledBorder
		UIManager.put("TitledBorder.titleColor", COLOR_ROJO);
		//TabbedPane
		UIManager.put("TabbedPane.foreground", Color.WHITE);
		//TaskPane
		UIManager.put("TaskPane.useGradient", Boolean.TRUE);
		UIManager.put("TaskPane.backgroundGradientStart", COLOR_ROJO);
		UIManager.put("TaskPane.backgroundGradientEnd", COLOR_ROJO);
		//Calendario
        PCalendar.weekDaysForeground = COLOR_ROJO;
	}

	public Font getDefaultFont() {
		return new Font("Tahoma", Font.PLAIN, 11);
	}

	public Font getSecondaryFont() {
		return new Font("Tahoma", Font.PLAIN, 11);
	}

	public String getIconoVentana() {
		return ICONO_VENTANA;
	}

	public String getIconoLogin() {
		return ICONO_LOGIN;
	}

	public Color getColorBarraTituloVentana() {
		return COLOR_ROJO;
	}

	public Color getColorFondoVentana() {
		return COLOR_MARRON_FONDO_VENTANA;
	}

	public Color getColorFondoPanel() {
		return COLOR_MARRON_FONDO_VENTANA;
	}

	public Color getColorComponenteNormal() {
		return COLOR_ROJO;
	}

	public Color getColorComponenteRollover() {
		return COLOR_NARANJA;
	}

	public Color getColorComponenteDeshabilitado() {
		return COLOR_GRIS_BARRA_TITULO;
	}

	public Color getColorComponenteSeleccionado() {
		return COLOR_NARANJA;
	}

	public Color getColorHeaderTabla() {
		return COLOR_MARRON_HEADER_TABLA;
	}

	public Color getColorItemNormal() {
		return Color.BLACK;
	}

	public Color getColorItemResaltado() {
		return COLOR_ROJO;
	}

	public Color getColorCajaTexto() {
		return Color.WHITE;
	}

}