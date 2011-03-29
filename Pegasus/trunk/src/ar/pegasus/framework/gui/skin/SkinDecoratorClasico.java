package ar.pegasus.framework.gui.skin;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.UIManager;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PBotonCalendario;
import ar.pegasus.framework.componentes.PBotonLimpiarFilaTabla;
import ar.pegasus.framework.componentes.PBotonesTabla;
import ar.pegasus.framework.componentes.PBotonesTablaLight;
import ar.pegasus.framework.componentes.PBtnCalendarioBF;
import ar.pegasus.framework.componentes.PCalendar;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.templates.main.skin.ISkinDecorator;
import ar.pegasus.framework.util.MiscUtil;

public class SkinDecoratorClasico implements ISkinDecorator {

	private static Font defaultFont;
	private static final String DEFAULT_FONT_FILE = "ar/pegasus/framework/recursos/mini7___.ttf";
	//Imágenes
	private static final String ICONO_VENTANA = "ar/pegasus/framework/imagenes/ico_logo_trans.png";
	private static final String ICONO_LOGIN = "ar/pegasus/framework/imagenes/logo_login_trans.png";
	private static final String ICONO_CALENDARIO = "ar/pegasus/framework/imagenes/b_calendario.png";
	private static final String ICONO_CALENDARIO_DESHAB = "ar/pegasus/framework/imagenes/b_calendario.png";
    private static final String ICONO_CALENDARIO_MES_ADELANTE = "ar/pegasus/framework/imagenes/b_cal_der.png";
    private static final String ICONO_CALENDARIO_MES_ATRAS = "ar/pegasus/framework/imagenes/b_cal_izq.png";
	private static final String ICONO_CALENDARIO_BF_ADELANTE = "ar/pegasus/framework/imagenes/b_cal_der.png";
	private static final String ICONO_CALENDARIO_BF_ADELANTE_DESHAB = "ar/pegasus/framework/imagenes/b_cal_der.png";
	private static final String ICONO_CALENDARIO_BF_ATRAS = "ar/pegasus/framework/imagenes/b_cal_izq.png";
	private static final String ICONO_CALENDARIO_BF_ATRAS_DESHAB = "ar/pegasus/framework/imagenes/b_cal_izq.png";
	private static final String ICONO_BOTONES_TABLA_AGREGAR = "ar/pegasus/framework/imagenes/b_agregar_fila.png";
	private static final String ICONO_BOTONES_TABLA_AGREGAR_DESHAB = "ar/pegasus/framework/imagenes/b_des_agregar_fila.png";
	private static final String ICONO_BOTONES_TABLA_ELIMINAR = "ar/pegasus/framework/imagenes/b_limpiar_fila.png";
	private static final String ICONO_BOTONES_TABLA_ELIMINAR_DESHAB = "ar/pegasus/framework/imagenes/b_des_limpiar_fila.png";
	private static final String ICONO_BOTONES_TABLA_AGREGAR_TODOS = "ar/pegasus/framework/imagenes/b_autocomp.png";
	private static final String ICONO_BOTONES_TABLA_AGREGAR_TODOS_DESHAB = "ar/pegasus/framework/imagenes/b_des_autocomp.png";
	private static final String ICONO_BOTONES_TABLA_LIGHT_AGREGAR = "ar/pegasus/framework/imagenes/b_agregar_fila.png";
	private static final String ICONO_BOTONES_TABLA_LIGHT_AGREGAR_DESHAB = "ar/pegasus/framework/imagenes/b_des_agregar_fila.png";
	private static final String ICONO_BOTONES_TABLA_LIGHT_ELIMINAR = "ar/pegasus/framework/imagenes/b_limpiar_fila.png";
	private static final String ICONO_BOTONES_TABLA_LIGHT_ELIMINAR_DESHAB = "ar/pegasus/framework/imagenes/b_des_limpiar_fila.png";
	private static final String ICONO_BOTON_LIMPIAR_FILA_TABLA = "ar/pegasus/framework/imagenes/b_limpiar_fila.png";
	private static final String ICONO_BOTON_LIMPIAR_FILA_TABLA_DESHAB = "ar/pegasus/framework/imagenes/b_des_limpiar_fila.png";
	//Colores
	private static final Color COLOR_BLANCO = new Color(248, 246, 245);
	private static final Color COLOR_MARRON_CLARO = new Color(221, 222, 211);
	private static final Color COLOR_MARRON_MEDIO = new Color(191, 192, 169);
	private static final Color COLOR_MARRON_OSCURO = new Color(102, 108, 86);
	private static final Color COLOR_MARRON_BOTONES = new Color(184, 186, 169);
	private static final Color COLOR_GRIS = new Color(163, 163, 163);
	private static final Color COLOR_BORDO = new Color(153, 45, 26);

	public void init() {
		//Button
		UIManager.put("Button.foreground", Color.BLACK);
		//TitledBorder
		UIManager.put("TitledBorder.titleColor", Color.BLACK);
		//TabbedPane
		UIManager.put("TabbedPane.foreground", Color.BLACK);
	    //Fonts
	    UIManager.put("Label.font", getDefaultFont());
	    UIManager.put("Button.font", getDefaultFont());
	    UIManager.put("CheckBox.font", getDefaultFont());
	    UIManager.put("RadioButton.font", getDefaultFont());
	    UIManager.put("Menu.font", getDefaultFont());
	    UIManager.put("MenuItem.font", getDefaultFont());
	    UIManager.put("TitledBorder.font", getDefaultFont());
        //TaskPane
		UIManager.put("TaskPane.useGradient", Boolean.TRUE);
		UIManager.put("TaskPane.backgroundGradientStart", COLOR_MARRON_MEDIO);
		UIManager.put("TaskPane.backgroundGradientEnd", COLOR_MARRON_MEDIO);
	    //Decoración del CLBotonCalendario
	    PBotonCalendario.iconoCalendario = ICONO_CALENDARIO;
	    PBotonCalendario.iconoCalendarioDeshab = ICONO_CALENDARIO_DESHAB;
        //Decoración del calendario
        PCalendar.forwardIcon = ICONO_CALENDARIO_MES_ADELANTE;
        PCalendar.backIcon = ICONO_CALENDARIO_MES_ATRAS;
        PCalendar.daysForeground = Color.BLACK;
        PCalendar.daysGridBackground = COLOR_BLANCO;
        PCalendar.weekDaysForeground = COLOR_BORDO;
        PCalendar.selectedDayForeground = COLOR_BLANCO;
	    //Decoración del CLBotonCalendarioBF
	    PBtnCalendarioBF.iconoBtnAdelante = ICONO_CALENDARIO_BF_ADELANTE;
	    PBtnCalendarioBF.iconoBtnAdelanteDeshab = ICONO_CALENDARIO_BF_ADELANTE_DESHAB;
	    PBtnCalendarioBF.iconoBtnAtras = ICONO_CALENDARIO_BF_ATRAS;
	    PBtnCalendarioBF.iconoBtnAtrasDeshab = ICONO_CALENDARIO_BF_ATRAS_DESHAB;
	    //Decoración del CLBotonesTabla
	    PBotonesTabla.iconoBtnAgregar = ICONO_BOTONES_TABLA_AGREGAR;
	    PBotonesTabla.iconoBtnAgregarDeshab = ICONO_BOTONES_TABLA_AGREGAR_DESHAB;
	    PBotonesTabla.iconoBtnEliminar = ICONO_BOTONES_TABLA_ELIMINAR;
	    PBotonesTabla.iconoBtnEliminarDeshab = ICONO_BOTONES_TABLA_ELIMINAR_DESHAB;
	    PBotonesTabla.iconoBtnAgregarTodos = ICONO_BOTONES_TABLA_AGREGAR_TODOS;
	    PBotonesTabla.iconoBtnAgregarTodosDeshab = ICONO_BOTONES_TABLA_AGREGAR_TODOS_DESHAB;
	    //Decoración del CLBotonesTablaLight
	    PBotonesTablaLight.iconoBtnAgregar = ICONO_BOTONES_TABLA_LIGHT_AGREGAR;
	    PBotonesTablaLight.iconoBtnAgregarDeshab = ICONO_BOTONES_TABLA_LIGHT_AGREGAR_DESHAB;
	    PBotonesTablaLight.iconoBtnEliminar = ICONO_BOTONES_TABLA_LIGHT_ELIMINAR;
	    PBotonesTablaLight.iconoBtnEliminarDeshab = ICONO_BOTONES_TABLA_LIGHT_ELIMINAR_DESHAB;
	    //Decoración de CLBotonLimpiarFilaTabla
	    PBotonLimpiarFilaTabla.iconoBtnLimpiar = ICONO_BOTON_LIMPIAR_FILA_TABLA;
	    PBotonLimpiarFilaTabla.iconoBtnLimpiarDeshab = ICONO_BOTON_LIMPIAR_FILA_TABLA_DESHAB;
	}

	public Font getDefaultFont() {
		if(defaultFont == null) {
			try {
				defaultFont = MiscUtil.crearFont(DEFAULT_FONT_FILE, MiscUtil.TRUETYPE_FONT, MiscUtil.PLAIN_FONT_STYLE, MiscUtil.isMacOS() ? 9.0f : 10.0f);
			} catch(FontFormatException e) {
				BossError.gestionarError(new PException("Error de formato de tipografía", e));
			} catch(IOException e) {
				BossError.gestionarError(new PException("Error de lectura del archivo de tipografía", e));
			}
		}
		return defaultFont;
	}

	public Font getSecondaryFont() {
		return new Font("Dialog", Font.PLAIN, 11);
	}

	public String getIconoVentana() {
		return ICONO_VENTANA;
	}

	public String getIconoLogin() {
		return ICONO_LOGIN;
	}

	public Color getColorBarraTituloVentana() {
		return COLOR_MARRON_OSCURO;
	}

	public Color getColorFondoVentana() {
		return COLOR_MARRON_CLARO;
	}

	public Color getColorFondoPanel() {
		return COLOR_MARRON_MEDIO;
	}

	public Color getColorComponenteNormal() {
		return COLOR_MARRON_BOTONES;
	}

	public Color getColorComponenteRollover() {
		return COLOR_MARRON_BOTONES;
	}

	public Color getColorComponenteDeshabilitado() {
		return COLOR_GRIS;
	}

	public Color getColorComponenteSeleccionado() {
		return COLOR_MARRON_BOTONES;
	}

	public Color getColorHeaderTabla() {
		return COLOR_MARRON_MEDIO;
	}

	public Color getColorItemNormal() {
		return Color.BLACK;
	}

	public Color getColorItemResaltado() {
		return COLOR_BORDO;
	}

	public Color getColorCajaTexto() {
		return COLOR_BLANCO;
	}

}