package ar.pegasus.framework.componentes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import ar.pegasus.framework.util.DecorateUtil;
import ar.pegasus.framework.util.ImageUtil;
/**
 * Componente que representa un botón asociado a una tabla, cuya función es eliminar
 * el contenido de la fila de la tabla seleccionada a partir de la columna especificada.
 * @author oarias
 */
public class PBotonLimpiarFilaTabla extends JButton {
	private static final long serialVersionUID = -35456612625117050L;

	public static String iconoBtnLimpiar = null;
	public static String iconoBtnLimpiarDeshab = null;
	private PJTable tabla;
	private int colDesde = 0;
	private static final String DEFAULT_ICONO_LIMPIAR = "ar/pegasus/framework/imagenes/b_limpiar_fila_tabla.png";
	private static final String DEFAULT_ICONO_LIMPIAR_DESHAB = "ar/pegasus/framework/imagenes/b_limpiar_fila_tabla_des.png";
	private static final int DEFAULT_BUTTON_WIDTH = 20;
	private static final int DEFAULT_BUTTON_HEIGHT = 20;

	/**
	 * Método constructor.
	 * @param tabla La tabla a la que pertenece la fila.
	 */
	public PBotonLimpiarFilaTabla(PJTable tabla) {
		super();
		this.tabla = tabla;
		construct();
	}

	/**
	 * Método constructor.
	 * @param tabla La tabla a la que pertenece la fila.
	 * @param colDesde El nro. de la columna a partir de la cual se eliminará el
	 * 		  contenido de la fila.
	 */
	public PBotonLimpiarFilaTabla(PJTable tabla, int colDesde) {
	    this(tabla);
	    this.colDesde = colDesde;
	}

	/**
	 * Método constructor.
	 * @param tabla La tabla a la que pertenece la fila.
	 * @param icono El icono del botón.
	 * @param iconoDeshabilitado El icono del botón en estado deshabilitado.
	 */
	public PBotonLimpiarFilaTabla(PJTable tabla, String icono, String iconoDeshabilitado) {
	    this(tabla);
	    setIconoBtnLimpiar(icono);
	    setIconoBtnLimpiarDeshabilitado(iconoDeshabilitado);
	}

	//Construye gráficamente el componente
	private void construct() {
	    String icono = (iconoBtnLimpiar == null ? DEFAULT_ICONO_LIMPIAR : iconoBtnLimpiar);
	    String iconoDeshab = (iconoBtnLimpiarDeshab == null ? DEFAULT_ICONO_LIMPIAR_DESHAB : iconoBtnLimpiarDeshab);
	    DecorateUtil.decorateButton(this, icono, iconoDeshab);
		setToolTipText("Borra el contenido de la fila seleccionada");
		setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                if(preBotonLimpiarFilaPresionado()) {
                    tabla.unselectCell();
                    if(tabla.getSelectedRowCount() != 0)
                        for(int i = colDesde; i < tabla.getColumnCount(); i++)
                            tabla.setValueAt(null, tabla.getSelectedRow(), i);
                    botonLimpiarFilaPresionado();
                }
			}
		});
	}

	/**
	 * Devuelve el nro. de la columna a partir de la cual se eliminará el contenido
	 * de la fila.
	 * @return colDesde El nro. de la columna a partir de la cual se eliminará el
	 *         contenido de la fila.
	 */
	public int getColDesde() {
	    return colDesde;
	}

	/**
	 * Setea la columna a partir de la cual se eliminará el contenido de la fila.
	 * @param colDesde El nro. de la columna a partir de la cual se eliminará el
	 *        contenido de la fila.
	 */
	public void setColDesde(int colDesde) {
	    this.colDesde = colDesde;
	}

	/**
	 * Devuelve el icono del botón.
	 * @return icono El icono del botón.
	 */
	public String getIconoBtnLimpiar() {
	    return iconoBtnLimpiar;
	}

	/**
	 * Setea el icono del botón.
	 * @param icono El icono del botón.
	 */
	public void setIconoBtnLimpiar(String icono) {
	    PBotonLimpiarFilaTabla.iconoBtnLimpiar = icono;
	    Icon icon = ImageUtil.loadIcon(icono);
	    super.setIcon(icon);
	    super.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}

	/**
	 * Devuelve el icono del botón en estado deshabilitado.
	 * @return iconoDeshabilitado El icono del botón en estado deshabilitado.
	 */
	public String getIconoBtnLimpiarDeshabilitado() {
	    return iconoBtnLimpiarDeshab;
	}

	/**
	 * Setea el icono del botón en estado deshabilitado.
	 * @param iconoDeshabilitado El icono del botón en estado deshabilitado.
	 */
	public void setIconoBtnLimpiarDeshabilitado(String iconoDeshabilitado) {
	    PBotonLimpiarFilaTabla.iconoBtnLimpiarDeshab = iconoDeshabilitado;
	    super.setDisabledIcon(ImageUtil.loadIcon(iconoDeshabilitado));
	}

	/** Manejo del evento del botón 'Limpiar fila' */
	public void botonLimpiarFilaPresionado() {
	}

    public boolean preBotonLimpiarFilaPresionado() {
        return true;
    }

    public void synchronize() {
    	if(tabla.isEnabled()) {
    		setEnabled(tabla.getSelectedRow() != -1);
    	}
    }

}