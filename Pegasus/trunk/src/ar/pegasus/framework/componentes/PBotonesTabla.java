package ar.pegasus.framework.componentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import ar.pegasus.framework.util.DecorateUtil;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaImagenes;
/**
 * Componente que representa un conjunto de botones para Agregar, Eliminar y Agregar Todos,
 * relacionados con una tabla.
 * @author oarias
 */
public class PBotonesTabla extends JPanel implements PBotonesTablaEventListener, ListDataListener {
	private static final long serialVersionUID = 1564476094502074216L;

	public static String iconoBtnAgregar = null;
	public static String iconoBtnAgregarDeshab = null;
	public static String iconoBtnEliminar = null;
	public static String iconoBtnEliminarDeshab = null;
	public static String iconoBtnAgregarTodos = null;
	public static String iconoBtnAgregarTodosDeshab = null;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnAgregarTodos;
	private PJTable tabla;
	private JComboBox cbTabla;
	private int col;
	private boolean listenerAgregado;
	private static final int DEFAULT_BUTTON_WIDTH = 20;
	private static final int DEFAULT_BUTTON_HEIGHT = 20;
	private static final int DEFAULT_VERTICAL_GAP = 10;
	public static final int DEFAULT_WIDTH = 20;
	public static final int DEFAULT_HEIGHT = 80;
	private boolean modoEdicion = true;

	/**
	 * M�todo constructor.
	 * @param tabla
	 * @param col = Indice de la columna en la tabla que contiene el ComboBox
	 */
	public PBotonesTabla(PJTable tabla, int col) {
		this.tabla = tabla;
		this.col = col;
		cbTabla = getComboTabla();
		construct();
	}

	//Construye gr�ficamente el componente
	private void construct() {
		setLayout(new BorderLayout(0, DEFAULT_VERTICAL_GAP));
		//Botón 'Agregar'
		btnAgregar = new JButton();
		String iconoAgregar = (iconoBtnAgregar == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR : iconoBtnAgregar);
		String iconoAgregarDeshab = (iconoBtnAgregarDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR_DESHAB : iconoBtnAgregarDeshab);
		DecorateUtil.decorateButton(btnAgregar, iconoAgregar, iconoAgregarDeshab);
		btnAgregar.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		btnAgregar.setToolTipText("Agregar");
		add(btnAgregar, BorderLayout.NORTH);
		btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonAgregarPresionado();
            }
		});
		//Botón 'Eliminar'
		btnEliminar = new JButton();
		String iconoEliminar = (iconoBtnEliminar == null ? RutaImagenes.DEFAULT_ICONO_BTN_ELIMINAR : iconoBtnEliminar);
		String iconoEliminarDeshab = (iconoBtnEliminarDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_ELIMINAR_DESHAB : iconoBtnEliminarDeshab);
		DecorateUtil.decorateButton(btnEliminar, iconoEliminar, iconoEliminarDeshab);
		btnEliminar.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		btnEliminar.setToolTipText("Eliminar");
		add(btnEliminar, BorderLayout.CENTER);
		btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonEliminarPresionado();
            }
		});
		//Botón 'Agregar Todos'
		btnAgregarTodos = new JButton();
		String iconoAgregarTodos = (iconoBtnAgregarTodos == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR_TODOS : iconoBtnAgregarTodos);
		String iconoAgregarTodosDeshab = (iconoBtnAgregarTodosDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_AGREGAR_TODOS_DESHAB : iconoBtnAgregarTodosDeshab);
		DecorateUtil.decorateButton(btnAgregarTodos, iconoAgregarTodos, iconoAgregarTodosDeshab);
		btnAgregarTodos.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		btnAgregarTodos.setToolTipText("Agregar Todos");
		add(btnAgregarTodos, BorderLayout.SOUTH);
		btnAgregarTodos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonAgregarTodosPresionado();
            }
		});
	}

	/** Obtiene el Botón <b>Agregar</b> */
	public JButton getBotonAgregar() {
		return btnAgregar;
	}

	/** Obtiene el Botón <b>Eliminar</b> */
	public JButton getBotonEliminar() {
		return btnEliminar;
	}

	/** Obtiene el Botón <b>Agregar todos</b> */
	public JButton getBotonAgregarTodos() {
		return btnAgregarTodos;
	}

	//Habilita o deshabilita los botones del componente 
	public void habilitarBotones(boolean b) {
		btnAgregar.setEnabled(b);
		btnEliminar.setEnabled(b);
		btnAgregarTodos.setEnabled(b);
	}

	/** Manejo de eventos del Botón <b>Agregar</b> */
	public void botonAgregarPresionado() {
		tabla.addRow();
		synchronize();
	}

	/** Manejo de eventos del Botón <b>Eliminar</b> */
	public void botonEliminarPresionado() {
        if(preBotonEliminarPresionado()) {
            if(tabla.getSelectedRow() != -1) {
                tabla.removeRow(tabla.getSelectedRow());
                synchronize();
            }
        }
	}
    
    public boolean preBotonEliminarPresionado() {
        return true;
    }
    
	/** Manejo de eventos del Botón <b>Agregar Todos</b> */
	public void botonAgregarTodosPresionado() {
		if(tabla.getTipoColumna(col) instanceof PJTable.ComboColumn) {
			cbTabla = getComboTabla();
			for(int i = 0; i < cbTabla.getItemCount(); i++) {				
				if(!tabla.foundData(col, cbTabla.getItemAt(i)) && (tabla.getRowCount() < cbTabla.getItemCount())) {
					if(tabla.getFirstEmptyRow(col) == -1) {
						botonAgregarPresionado();
						tabla.setValueAt(cbTabla.getItemAt(i), tabla.getRowCount() - 1, col);
					} else
						tabla.setValueAt(cbTabla.getItemAt(i), tabla.getFirstEmptyRow(col), col);
				}
			}
		}
	}

	/** Sincroniza el estado del componente con el estado de la tabla asociada */
	public void synchronize() {
		if(tabla.isEnabled()) {
			if((tabla.getRowCount() == 0) && (getComboTabla().getItemCount() != 0)) {
				btnAgregar.setEnabled(true);
				btnEliminar.setEnabled(false);
				btnAgregarTodos.setEnabled(true);
			} else {
				if(getComboTabla().getItemCount() == 0) {
					btnEliminar.setEnabled(false);
					btnAgregar.setEnabled(false);
					btnAgregarTodos.setEnabled(false);
				} else {
					btnEliminar.setEnabled(tabla.getSelectedRow() != -1 && isModoEdicionBtn());
					if(todosAgregados()) {
						btnAgregar.setEnabled(false);
						btnAgregarTodos.setEnabled(false);
					} else {
						btnAgregar.setEnabled(true);
						btnAgregarTodos.setEnabled(true);
					}
				}
			}
		} else {
			habilitarBotones(false);			
		}

		if(!listenerAgregado) {
			cbTabla = getComboTabla();
			if(cbTabla != null) {
				((DefaultComboBoxModel)cbTabla.getModel()).addListDataListener(this);
				listenerAgregado = true;
			}
		}
	}

	//Obtiene el ComboBox de la tabla
	private JComboBox getComboTabla() {
		return tabla.getComboColumn(col);
	}

	//Retorna true si la cantidad de items del ComboBox asociado es igual a la cantidad
	//de filas de la tabla asociada
	private boolean todosAgregados() {
		return getComboTabla().getItemCount() == tabla.getRowCount();
	}

	//Se agrega un item al ComboBox
	public void intervalAdded(ListDataEvent evt) {
		synchronize();
	}

	//Se elimina un item del ComboBox
	public void intervalRemoved(ListDataEvent evt) {
		synchronize();
	}

	public void contentsChanged(ListDataEvent evt) {
	}

    public String getIconoBtnAgregar() {
        return iconoBtnAgregar;
    }

    public void setIconoBtnAgregar(String iconoBtnAgregar) {
        PBotonesTabla.iconoBtnAgregar = iconoBtnAgregar;
        Icon icon = ImageUtil.loadIcon(iconoBtnAgregar);
        btnAgregar.setIcon(icon);
        btnAgregar.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    }

    public String getIconoBtnAgregarDeshab() {
        return iconoBtnAgregarDeshab;
    }

    public void setIconoBtnAgregarDeshab(String iconoBtnAgregarDeshab) {
        PBotonesTabla.iconoBtnAgregarDeshab = iconoBtnAgregarDeshab;
        btnAgregar.setDisabledIcon(ImageUtil.loadIcon(iconoBtnAgregarDeshab));
    }

    public String getIconoBtnAgregarTodos() {
        return iconoBtnAgregarTodos;
    }

    public void setIconoBtnAgregarTodos(String iconoBtnAgregarTodos) {
        PBotonesTabla.iconoBtnAgregarTodos = iconoBtnAgregarTodos;
        Icon icon = ImageUtil.loadIcon(iconoBtnAgregarTodos);
        btnAgregarTodos.setIcon(icon);
        btnAgregarTodos.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    }

    public String getIconoBtnAgregarTodosDeshab() {
        return iconoBtnAgregarTodosDeshab;
    }

    public void setIconoBtnAgregarTodosDeshab(String iconoBtnAgregarTodosDeshab) {
        PBotonesTabla.iconoBtnAgregarTodosDeshab = iconoBtnAgregarTodosDeshab;
        btnAgregarTodos.setDisabledIcon(ImageUtil.loadIcon(iconoBtnAgregarTodosDeshab));
    }

    public String getIconoBtnEliminar() {
        return iconoBtnEliminar;
    }

    public void setIconoBtnEliminar(String iconoBtnEliminar) {
        PBotonesTabla.iconoBtnEliminar = iconoBtnEliminar;
        Icon icon = ImageUtil.loadIcon(iconoBtnEliminar);
        btnEliminar.setIcon(icon);
        btnEliminar.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    }

    public String getIconoBtnEliminarDeshab() {
        return iconoBtnEliminarDeshab;
    }

    public void setIconoBtnEliminarDeshab(String iconoBtnEliminarDeshab) {
       PBotonesTabla.iconoBtnEliminarDeshab = iconoBtnEliminarDeshab;
       btnEliminar.setDisabledIcon(ImageUtil.loadIcon(iconoBtnEliminarDeshab));
    }

	public boolean isModoEdicionBtn() {
		return modoEdicion;
	}

	public void setModoEdicionBtn(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
	/**
	 * @author mfazzito
	 * @param 
	 * metodo que oculta el boton agregar todos , reconstruye graficamnete el componete sin el boton
	 */
	public void ocultarBotonAgregarTodos(){
		this.removeAll();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(getBotonAgregar());
		Dimension minSize = new Dimension(3, 10);
		Dimension prefSize = new Dimension(3, 10);
		Dimension maxSize = new Dimension(3, 10);
		this.add(new Box.Filler(minSize, prefSize, maxSize));
		this.add(getBotonEliminar());
	}


}