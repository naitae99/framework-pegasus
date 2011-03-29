package ar.pegasus.framework.componentes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.RutaImagenes;

/**
 * Componente que representa una versión reducida de PBotonesTabla.
 * @author oarias
 */
@SuppressWarnings("serial")
public class PBotonesTablaLight extends JPanel {

	private JButton btnAgregar;
	private JButton btnEliminar;
	private PJTable tabla;
	private boolean modoEdicion;
	public static String iconoBtnAgregar = null;
	public static String iconoBtnAgregarDeshab = null;
	public static String iconoBtnEliminar = null;
	public static String iconoBtnEliminarDeshab = null;
	public static final int DEFAULT_WIDTH = 20;
	public static final int DEFAULT_HEIGHT = 50;

	/**
	 * Método constructor.
	 * @param tabla
	 */
	public PBotonesTablaLight(PJTable tabla) {
		this(tabla, true);
	}

	/**
	 * Método constructor.
	 * @param tabla
	 * @param modoEdicion
	 */
	public PBotonesTablaLight(PJTable tabla, boolean modoEdicion) {
		this.tabla = tabla;
		construct();
		setModoEdicion(modoEdicion);
	}

	//Construye gráficamente el componente
	private void construct() {
		setLayout(new BorderLayout(0, 10));
		//Botón 'Agregar'
		add(getBotonAgregar(), BorderLayout.NORTH);
		//Botón 'Eliminar'
		add(getBotonEliminar(), BorderLayout.SOUTH);
		//Tabla
		tabla.getSelectionModel().addListSelectionListener(new TablaListener());
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
		//Lockeo de tabla y sincronizaci�n de los botones
		tabla.setTableLock(!modoEdicion);
		btnAgregar.setEnabled(modoEdicion);
		btnEliminar.setEnabled(modoEdicion ? (tabla.getSelectedRow() != -1) : false);
	}

	public PJTable getTabla() {
		return tabla;
	}

	public JButton getBotonAgregar() {
		if(btnAgregar == null){
			if(debeMostrarImagenes()){
				String iconoAgregar = (iconoBtnAgregar == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR : iconoBtnAgregar);
				String iconoAgregarDeshab = (iconoBtnAgregarDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR_DESHAB : iconoBtnAgregarDeshab);
				btnAgregar = BossEstilos.createButton(iconoAgregar, iconoAgregarDeshab);
			}else{
				btnAgregar = BossEstilos.createButton(EtiquetasBotones.AGREGAR);
			}
				
			btnAgregar.setEnabled(false);
			btnAgregar.setToolTipText("Agregar fila");
			btnAgregar.addActionListener(new BotonAgregarListener());
		}
		return btnAgregar;
	}

	public JButton getBotonEliminar() {
		if(btnEliminar == null){
			if(debeMostrarImagenes()){
				String iconoEliminar = (iconoBtnEliminar == null ? RutaImagenes.DEFAULT_ICONO_BTN_ELIMINAR : iconoBtnEliminar);
				String iconoEliminarDeshab = (iconoBtnEliminarDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_ELIMINAR_DESHAB : iconoBtnEliminarDeshab);
				btnEliminar = BossEstilos.createButton(iconoEliminar, iconoEliminarDeshab);
			}else{
				btnEliminar = BossEstilos.createButton(EtiquetasBotones.ELIMINAR);
			}				
			btnEliminar.setEnabled(false);
			btnEliminar.setToolTipText("Eliminar fila");
			btnEliminar.addActionListener(new BotonEliminarListener());
		}
		return btnEliminar;
	}

	public void botonAgregarPresionado() {
	}

	public void botonQuitarPresionado() {
	}

	public boolean validarAgregar() {
	    return true;
	}

	public boolean validarQuitar() {
	    return true;
	}

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        btnAgregar.setEnabled(enabled);
        btnEliminar.setEnabled(enabled);
    }

    class BotonAgregarListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(validarAgregar()) {
				tabla.addRow();
				tabla.scroll(tabla.getRowCount() - 1);
				botonAgregarPresionado();
			}
		}
    }

    class BotonEliminarListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(tabla.getSelectedRow() != -1) {
				if(validarQuitar()) {
					tabla.removeRow(tabla.getSelectedRow());
					if(tabla.getRowCount() == 0) {
						btnEliminar.setEnabled(false);
					}
					botonQuitarPresionado();
				}
			}
		}
    }

    class TablaListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent evt) {
			if(modoEdicion) {
				btnEliminar.setEnabled((tabla.getSelectedRow() != -1));
			}
		}
	}
    
    public boolean debeMostrarImagenes(){
    	return true;
    }

}