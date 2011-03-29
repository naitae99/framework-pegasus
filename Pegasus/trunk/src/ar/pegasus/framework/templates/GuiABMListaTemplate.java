package ar.pegasus.framework.templates;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.taglibs.string.util.StringW;

import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PJList;
import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.componentes.PJTable;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.util.GuiUtil;

/**
 * Template para la creación de formularios MDI con la siguiente estructura básica:
 * Un combobox en la parte superior izquierda, una lista (JList) a la izquierda,
 * y a la derecha de la pantalla, un conjunto de tabs o solapas para mostrar/editar
 * los datos relacionados a los items de la lista, con botones para <b>Modificar</b>
 * y <b>Grabar</b> esos datos.
 * @author oarias
 * @see ar.pegasus.framework.templates.GuiABMArbolTemplate
 */
public abstract class GuiABMListaTemplate extends GuiABMTemplate {
	private static final long serialVersionUID = -6641581387811079296L;

	protected PJList lista;
    protected JTabbedPane panTabs;
	protected boolean mensajeSalir;

    /** Método constructor */
    public GuiABMListaTemplate() {
        super();
        construct();
    }

    public GuiABMListaTemplate(Modulo modulo) throws PException {
		super(modulo);
		construct();
    }

    /** Construye graficamente la pantalla */
    private void construct() {
        //Lista
        lista = new PJList();
        getPanSelector().setViewportView(lista);
        lista.addListSelectionListener(new ListaListener());
        setSelector(lista);
        //Tabs
        panTabs = new JTabbedPane();
        panTabs.setFont(BossEstilos.getDefaultFont());
        getContentPane().add(panTabs);
        panTabs.setBounds(330, 60, 650, 530);
    }

    /** Método abstracto de llenado de la lista */
    public abstract void cargarLista();

    /** Actualiza el contenido de la lista */
    @SuppressWarnings("rawtypes")
	public void refrescarSelector() {
        if(isHijoCreado()) {
            Object sel = null;
            List oldValues = lista.getItemList();
            if(lista.getModel().getSize() > 0) {
                sel = lista.getSelectedValue();
            }
            lista.clear();
            cargarLista();
            Object newObject = getNewObject(oldValues, lista.getItemList());
            if(newObject != null) {
                lista.setSelectedValue(newObject, true);
            } else if(sel != null) {
                lista.setSelectedValue(sel, true);
            }
        }
    }

    /**
     * Devuelve el elemento reci�n agregado. Este m�todo deber�a encontrar un elemento nuevo
     * sólo en el caso de 'Agregar'. 
     * @param oldValues La lista de elementos de la lista antes de agregar uno nuevo
     * @param newValues La lista de elementos de la lista después de agregar uno nuevo
     * @return El elemento reci�n agregado si existe, <code>null</code> en caso contrario
     */
    @SuppressWarnings("rawtypes")
	private Object getNewObject(List oldValues, List newValues) {
        if(oldValues != null && !oldValues.isEmpty()) {
            for(Object obj : newValues) {
                if(!oldValues.contains(obj)) {
                    return obj;
                }
            }
        }
        return null;
    }

    /** Devuelve <b>true</b> si la lista no tiene ningún ítem seleccionado */
    protected boolean isSelectorSelectionEmpty() {
        return lista.isSelectionEmpty();
    }

    protected void setEstadoInicialTemplate() {
        super.setEstadoInicialTemplate();
        getBtnAgregar().setEnabled(true);
        if(isHijoCreado())
            habilitarTabSeleccionado(false);
    }

    /** Define el estado de los botones y del selector en el modo edición */
    public void setModoEdicionTemplate(boolean estado) {
        habilitarTabSeleccionado(estado);
        super.setModoEdicionTemplate(estado);
    }

    /**
     * Habilita/Deshabilita el tab seleccionado.
     * @param estado
     */
    public void habilitarTabSeleccionado(boolean estado) {
        JComponent c = (JComponent)panTabs.getSelectedComponent();
        if(c != null) {
            GuiUtil.setEstadoPanel(c, estado);
        }
    }

    /**
     * Habilita/Deshabilita todos los tabs.
     * @param estado
     */
    public void habilitarTabs(boolean estado) {
        for(int i = 0; i < panTabs.getTabCount(); i++) {
            GuiUtil.setEstadoPanel((JComponent)panTabs.getComponentAt(i), estado);
        }
    }

    /** Clase listener de eventos de selección de items de la lista */
    class ListaListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent evt) {
			if(!evt.getValueIsAdjusting()) { //Para que el evento no se dispare dos veces
				if(lista.isEnabled()) {
	                habilitarAgregarEliminar();
				}
                if(lista.getSelectedIndex() != -1) {
                    itemSelectorSeleccionado(lista.getSelectedIndex());
                } else {
                    limpiarDatos();
                }
			}
        }
    }

    /**
     * Selecciona el tab donde ocurrió la validación y muestra el mensaje
     * correspondiente.
     * @param msg El mensaje a mostrar que contiene el texto de validación.
     * @param title El título del mensaje a mostrar.
     * @param tab El tab donde ocurrió la validación.
     */
    protected void mostrarMensajeValidacion(String msg, String title, JPanel tab) {
        panTabs.setSelectedComponent(tab);
        PJOptionPane.showWarningMessage(GuiABMListaTemplate.this, StringW.wordWrap(msg), title);
    }

    /**
     * Selecciona el tab donde ocurrió la validación y muestra el mensaje
     * correspondiente.
     * @param msg El mensaje a mostrar que contiene el texto de validaci�n.
     * @param title El título del mensaje a mostrar.
     * @param tab El tab donde ocurrió la validación.
     * @param tf El TextField que obtiene el foco.
     */
    protected void mostrarMensajeValidacion(String msg, String title, JPanel tab, JTextField tf) {
        panTabs.setSelectedComponent(tab);
        PJOptionPane.showWarningMessage(GuiABMListaTemplate.this, StringW.wordWrap(msg), title);
        tf.requestFocus();
        tf.selectAll();
    }

    /**
     * Selecciona el tab donde ocurrió la validación y muestra el mensaje
     * correspondiente.
     * @param msg El mensaje a mostrar que contiene el texto de validación.
     * @param title El título del mensaje a mostrar.
     * @param tab El tab donde ocurrió la validación.
     * @param tabla
     * @param filas
     * @param color
     */
    protected void mostrarMensajeValidacion(String msg, String title, JPanel tab, PJTable tabla, Integer[] filas, Color color) {
        panTabs.setSelectedComponent(tab);
        PJOptionPane.showWarningMessage(GuiABMListaTemplate.this, StringW.wordWrap(msg), title);
        tabla.clearSelection();
        for(int fila : filas) {
        	tabla.setBackgroundRow(fila, color);
        }
    }

}