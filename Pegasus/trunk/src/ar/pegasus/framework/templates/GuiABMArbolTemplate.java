package ar.pegasus.framework.templates;

import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.TreePath;

import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.componentes.EdicionPopupListener;
import ar.pegasus.framework.componentes.EdicionPopupMenu;
import ar.pegasus.framework.componentes.PArbolExtendido;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.util.TreeUtil;
/**
 * Template para la creación de formularios MDI con la siguiente estructura básica:
 * Un combobox en la parte superior izquierda, un árbol jerárquico (JTree) a la
 * izquierda, y a la derecha de la pantalla, un conjunto de tabs o solapas para
 * mostrar/editar los datos relacionados a los nodos del árbol, con botones para
 * <b>Modificar</b> y <b>Grabar</b> esos datos.
 * El árbol cuenta con un menú contextual con las opciones <b>Agregar</b> (para agregar
 * un nodo hijo al nodo seleccionado), <b>Modificar</b> (para modificar los datos que se
 * muestran en las solapas relacionados a dicho nodo) y <b>Eliminar</b> (para eliminar
 * el nodo seleccionado).
 * @author oarias
 */
public abstract class GuiABMArbolTemplate extends GuiABMTemplate {
	private static final long serialVersionUID = -4180446679816896604L;

	public JTree arbol;
	public JScrollPane panArbol;
	public EdicionPopupMenu edicionPopupMenu;
	public EdicionPopupListener edicionPopupListener;
	protected int NIVEL_MAXIMO_ARBOL = 4;
	protected int NIVEL_MINIMO_ARBOL = 1;

	/** M�todo constructor */
	public GuiABMArbolTemplate() {
		//Construye gr�ficamente la interfaz
		construct();

		//Inicializa el popup men� asociado al �rbol
		inicializarPopupMenu();
	}

	public GuiABMArbolTemplate(Modulo modulo) throws PException {
		super(modulo);
		construct();

		//Inicializa el popup men� asociado al �rbol
		inicializarPopupMenu();
    }

	//Construye gr�ficamente la interfaz
	private void construct() {
		//Arbol
		arbol = new PArbolExtendido();
		panArbol = getPanSelector();
		arbol.setShowsRootHandles(true);
		arbol.setFont(BossEstilos.getSecondaryFont());
		panArbol.setViewportView(arbol);
		arbol.addTreeSelectionListener(new ArbolListener());
		arbol.addTreeWillExpandListener(new ArbolWillExpandListener());
		getContentPane().add(panArbol);
		setSelector(arbol);
	}

	/** Método abstracto de llenado del árbol */
	public abstract void cargarArbol();

	/** Inicializa el popup menú asociado al arbol */
	private void inicializarPopupMenu() {
		//Instancia el popup menú
		edicionPopupMenu = new EdicionPopupMenu();
		//Instancia el manejador de eventos para el popup menu
		edicionPopupListener = new EdicionPopupListener(edicionPopupMenu);
		edicionPopupListener.setSelectTreeOnPopup(true);
		//Agrega el manejador de eventos de mouse al arbol
		arbol.addMouseListener(edicionPopupListener);
		//Agrega los listeners a las opciones del popup menu 'Edición'
		edicionPopupMenu.opcionAgregar.addActionListener(new BotonAgregarListener());
		edicionPopupMenu.opcionModificar.addActionListener(new BotonModificarListener());
		edicionPopupMenu.opcionEliminar.addActionListener(new BotonEliminarListener());
	}

	/** Actualiza el contenido del árbol */
	public void refrescarSelector() {
		Vector<Integer> descrNodosArbol = TreeUtil.salvarNodoSeleccionado(arbol);
		List nodosArbol = TreeUtil.salvarNodosExpandidosArbol(arbol);
		cargarArbol();
		TreeUtil.recuperarNodosExpandidosArbol(arbol, nodosArbol, descrNodosArbol);
	}

	/** Devuelve <b>true</b> si el árbol no tiene ningún ítem seleccionado */
	public boolean isSelectorSelectionEmpty() {
	    return arbol.isSelectionEmpty();
	}

	/** Remueve el popup menú de edición */
	public void removerEdicionPopupMenu() {
		arbol.removeMouseListener(edicionPopupListener);
	}

	/** Clase para el manejo de eventos de selección de nodos del árbol */
	private class ArbolListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent evt) {
			if(arbol.isEnabled()) {
				arbol.setSelectionPath(evt.getPath());
				int nivel = TreeUtil.getNivelNodoSeleccionadoArbol(arbol);
				//Define el estado de los botones cuando se selecciona un nodo del �rbol
				getBtnAgregar().setEnabled(true);
				getBtnEliminar().setEnabled(true);
				getBtnModificar().setEnabled(true);
				getBtnGrabar().setEnabled(false);
				if(nivel == NIVEL_MINIMO_ARBOL) {
					getBtnEliminar().setEnabled(false);
					getBtnModificar().setEnabled(false);
				} else if(nivel == NIVEL_MAXIMO_ARBOL)
					getBtnAgregar().setEnabled(false);
				//Llama al m�todo abstracto de selecci�n de nodos del �rbol
				if(isHijoCreado())
					itemSelectorSeleccionado(nivel);
			}
		}
	}

	/** Clase que permite colapsar el arbol con un nodo hijo seleccionado */
	public class ArbolWillExpandListener implements TreeWillExpandListener {
		public void treeWillExpand(TreeExpansionEvent evt) {			   		  
		}

		public void treeWillCollapse(TreeExpansionEvent evt) {
			TreePath treePath = evt.getPath();
			if(arbol.getSelectionPath() != null) {
				if((treePath.isDescendant(arbol.getSelectionPath())) && (treePath.getParentPath() != null)) {
					arbol.setSelectionPath(treePath.getParentPath());
					arbol.collapsePath(treePath);
					arbol.setSelectionPath(treePath);								
				}

				if((!treePath.equals(arbol.getSelectionPath())) && (treePath.getParentPath() == null)) { //Es root
				  arbol.setSelectionRow(0);
				  arbol.collapseRow(0);
				}
			}
		}
	}

}