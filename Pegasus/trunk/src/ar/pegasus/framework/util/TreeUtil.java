package ar.pegasus.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
/**
 * Clase con funciones útiles para la utilización del componente javax.swing.JTree.
 * @author oarias
 */
public class TreeUtil {

	private static Vector<Vector<Integer>> nodos = new Vector<Vector<Integer>>();

	/**
	 * Inicializa el �rbol con el nombre del nodo raiz y devuelve el modelo del mismo.
	 * @param raiz Nombre del nodo raiz.
	 * @return treeModel El modelo del �rbol.
	 */
	public static DefaultTreeModel inicializarArbol(String raiz) {
		DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode(new IndexItem(0, raiz));
		DefaultTreeModel treeModel = new DefaultTreeModel(nodoRaiz);
		return treeModel;
	}

	/**
	 * Inicializa el �rbol con el nombre del nodo raiz y devuelve el modelo del mismo.
	 * @param raiz Objeto IndexItem que representa al nodo raiz.
	 * @return treeModel El modelo del �rbol.
	 */
	public static DefaultTreeModel inicializarArbol(IndexItem raiz) {
		DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode(raiz);
		DefaultTreeModel treeModel = new DefaultTreeModel(nodoRaiz);
		return treeModel;
	}

	/**
	 * Devuelve el <b>nivel</b> del nodo seleccionado del �rbol.
	 * Si no existe ning�n nodo seleccionado devuelve 0.
	 * @param arbol El componente JTree.
	 * @return El nivel del nodo seleccionado.
	 */
	public static int getNivelNodoSeleccionadoArbol(JTree arbol) {
		if(arbol.isSelectionEmpty())
			return 0;
		else
			return arbol.getSelectionPath().getPathCount();
	}

	/**
	 * Devuelve el <b>nivel</b> en el �rbol del nodo pasado por par�metro.
	 * @param nodo El nodo del �rbol.
	 * @return El nivel del nodo del �rbol.
	 */
	public static int getNivelNodoArbol(DefaultMutableTreeNode nodo) {
		return nodo.getLevel();
	}

	/**
	 * Elimina <b>todos los nodos</b> del �rbol.
	 * @param arbol El componente JTree.
	 */
    public static void vaciarArbol(JTree arbol) {
		DefaultMutableTreeNode nodoRaiz = ((DefaultMutableTreeNode)arbol.getModel().getRoot());
		if(nodoRaiz.getChildCount() != 0) {
			nodoRaiz.removeAllChildren();
			colapsarArbol(arbol);
		}
    }

	/**
	 * Agrega un nodo al �rbol.
	 * @param arbol El componente JTree.
	 * @param nodoPadre El nodo padre del nodo a agregar.
	 * @param indexItem El objeto a agregar como nodo.
	 * @see ar.clarin.fwjava.IndexItem
	 */
	public static void agregarNodoArbol(JTree arbol, DefaultMutableTreeNode nodoPadre, IndexItem indexItem) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(indexItem);
		nodoPadre.add(nodo);
		arbol.setSelectionPath(getPathNodoArbol(nodo)); 
	}

	/**
	 * Devuelve el <b>nodo ra�z</b> del �rbol.
	 * @param arbol El componente JTree.
	 * @return El nodo ra�z del �rbol.
	 */
	public static DefaultMutableTreeNode getNodoRaizArbol(JTree arbol) {
		return ((DefaultMutableTreeNode)arbol.getModel().getRoot());
	}

	/**
	 * Selecciona el <b>nodo ra�z</b> del �rbol.
	 * @param arbol El componente JTree.
	 */
	public static void seleccionarNodoRaizArbol(JTree arbol) {
		arbol.setSelectionPath(new TreePath(((DefaultTreeModel)arbol.getModel()).getPathToRoot((DefaultMutableTreeNode)arbol.getModel().getRoot())));
	}

	/**
	 * Devuelve el <b>id</b> del nodo del �rbol pasado por par�metro.
	 * @param arbol El componente JTree.
	 * @param nodo El nodo del cual se obtiene el id.
	 * @return El id del nodo.
	 * @see ar.clarin.fwjava.IndexItem
	 */
	public static int getIdNodoArbol(JTree arbol, DefaultMutableTreeNode nodo) {
		return ((IndexItem)nodo.getUserObject()).getId();
	}

	/**
	 * Devuelve el <b>id</b> del nodo seleccionado en el �rbol.
	 * @param arbol El componente JTree.
	 * @return El id del nodo seleccionado en el �rbol.
	 * @see ar.pegasus.framework.IndexItem
	 */
	public static int getIdNodoSeleccionadoArbol(JTree arbol) {
		return((IndexItem)((DefaultMutableTreeNode)arbol.getSelectionPath().getLastPathComponent()).getUserObject()).getId();
	}

	/**
	 * Devuelve el <b>id</b> del <b>nodo padre</b> del nodo seleccionado en el �rbol.
	 * @param arbol El componente JTree.
	 * @return El id del nodo padre del nodo seleccionado en el �rbol.
	 * @see ar.pegasus.framework.IndexItem
	 */
	public static int getIdPadreNodoSeleccionadoArbol(JTree arbol) {
		return((IndexItem)((DefaultMutableTreeNode)((DefaultMutableTreeNode)arbol.getSelectionPath().getLastPathComponent()).getParent()).getUserObject()).getId();
	}

	/**
	 * Devuelve el <b>id</b> del <b>nodo padre</b> del nodo seleccionado en el �rbol.
	 * @param arbol El componente JTree.
	 * @return El id del nodo padre del nodo seleccionado en el �rbol.
	 * @see ar.pegasus.framework.IndexItem
	 */
	public static DefaultMutableTreeNode getNodoPadreNodoSeleccionadoArbol(JTree arbol) {
		return ((DefaultMutableTreeNode)((DefaultMutableTreeNode)arbol.getSelectionPath().getLastPathComponent()).getParent());
	}

	/**
	 * Devuelve el <b>nodo</b> seleccionado en el �rbol.
	 * @param arbol El componente JTree.
	 * @return El nodo seleccionado en el �rbol o null.
	 */
	public static DefaultMutableTreeNode getNodoSeleccionadoArbol(JTree arbol) {
		if(arbol.getSelectionPath() == null)
			return null;
		return(DefaultMutableTreeNode)arbol.getSelectionPath().getLastPathComponent();
	}

    /**
     * Devuelve la cantidad de hijos que tiene el padre del nodo recibido
     * como par�metro. 
     * @param nodo
     * @return La cantidad de hijos del nodo padre o -1 si es el nodo raiz
     */
    public static int getCantidadHijosNodoPadre(DefaultMutableTreeNode nodo) {
        if(nodo != null) {
            TreeNode nodoPadre = nodo.getParent();
            if(nodoPadre != null) {
                return nodoPadre.getChildCount();
            } else {
                return -1;
            }            
        } else {
            return -1;
        }
    }

    /**
	 * Modifica la <b>descripci�n</b> del <b>nodo selecccionado</b> en el �rbol.
	 * @param arbol El componente JTree.
	 * @param descripcion La descripci�n del nodo seleccionado en el �rbol.
	 */
	public static void setDescripcionNodoSeleccionadoArbol(JTree arbol, String descripcion) {
		DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode)arbol.getSelectionPath().getLastPathComponent();
		((IndexItem)nodoSeleccionado.getUserObject()).setNombre(descripcion);
		refrescarNodo(arbol, nodoSeleccionado);
	}

	/** Modifica la <b>descripci�n</b> de un nodo en el �rbol.
	 * @param arbol El componente JTree.
	 * @param nodo El nodo al cual se le modificar� la descripci�n.
	 * @param descripcion La descripci�n a asignar al nodo.
	 */
	public static void setDescripcionNodoArbol(JTree arbol, DefaultMutableTreeNode nodo, String descripcion) {
		((IndexItem)nodo.getUserObject()).setNombre(descripcion);
		refrescarNodo(arbol, nodo);
	}

	/**
	 * Actualiza un nodo. Util para cuando se cambia el nombre de un nodo.
	 * @param arbol El componente JTree.
	 * @param nodo El nodo que ser� actualizado.
	 */
	public static void refrescarNodo(JTree arbol, DefaultMutableTreeNode nodo) {
		((DefaultTreeModel)arbol.getModel()).nodeChanged(nodo);
	}

	/**
	 * Salva el estado actual del árbol (los nodos expandidos/colapsados).
	 * @param arbol El componente JTree.
	 * @return nodosExpandidos
	 */
	public static List salvarNodosExpandidosArbol(JTree arbol) {
		Vector<Integer> descripciones = null;
		List nodosExpandidos = new ArrayList();
		TreeModel treeModel = arbol.getModel();
		DefaultMutableTreeNode nodo;
		nodos.removeAllElements();
		if(treeModel == null)
			return nodosExpandidos;
		Object nodoRaiz = treeModel.getRoot();
		TreePath treePathRaiz = new TreePath(new Object[] { nodoRaiz });
		Enumeration e = arbol.getExpandedDescendants(treePathRaiz);
		if(e == null)
			return nodosExpandidos;
		while(e.hasMoreElements()) {
			TreePath treePath = (TreePath)e.nextElement();
			nodo = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			descripciones = new Vector<Integer>();
			while(!nodo.isRoot()) {
				descripciones.add(new Integer(((IndexItem)nodo.getUserObject()).getId()));
				nodo = (DefaultMutableTreeNode)nodo.getParent();
			}
			descripciones.add(new Integer(((IndexItem)nodo.getUserObject()).getId()));
			Collections.reverse(descripciones);
			nodos.add(descripciones);
		}
		return nodosExpandidos;
	}

	/**
	 * Recarga el árbol dejándolo en el estado previamente salvado.
	 * @param arbol El componente JTree.
	 * @param nodosExpandidos La lista de los nodos expandidos en el árbol.
	 * @param descripciones
	 * @see 'ar.pegasus.framework.util.TreeUtil.salvarNodosExpandidosArbol(JTree arbol)'
	 */
	public static void recuperarNodosExpandidosArbol(JTree arbol, List nodosExpandidos, Vector descripciones) {
		TreeModel treeModel = arbol.getModel();
		TreeNode raiz = (TreeNode)arbol.getModel().getRoot();	
		if(treeModel == null) {
			return;
		}
		for(int i = 0; i < nodos.size(); i++) {
			arbol.expandPath(buscarNodoArbol(arbol, new TreePath(raiz), ((Vector)nodos.elementAt(i)).toArray(), 0));
		}
		if(descripciones != null) {
			if(descripciones.size() != 0) {
				Collections.reverse(descripciones);
				arbol.setSelectionPath(buscarNodoArbol(arbol, new TreePath(raiz), descripciones.toArray(), 0));
			}
		}
	}

	/**
	 * Guarda el <b>id del nodo seleccionado</b> en el �rbol y los ids del padre para
	 * arriba.
	 * @param arbol El componente JTree.
	 * @return nodos Los nodos guardados.
	 */
	public static Vector<Integer> salvarNodoSeleccionado(JTree arbol) {
		Vector<Integer> nodos = new Vector<Integer>();
		DefaultMutableTreeNode nodo = getNodoSeleccionadoArbol(arbol);
		if(nodo != null) {
			while(!nodo.isRoot()) {
				nodos.add(new Integer(((IndexItem)nodo.getUserObject()).getId()));
				nodo = (DefaultMutableTreeNode)nodo.getParent();
			}
			nodos.add(new Integer(((IndexItem)nodo.getUserObject()).getId()));
		}
		return nodos;
	}

	/**
	 * Busca y devuelve (en caso de que exista) el <b>TreePath</b> del nodo
	 * seleccionado del �rbol. Si no existe devuelve <b>null</b>.
	 * @param arbol El componente JTree.
	 * @param treePathPadre
	 * @param nodos
	 * @param profundidad
	 * @return
	 */
	private static TreePath buscarNodoArbol(JTree arbol, TreePath treePathPadre, Object[] nodos, int profundidad) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)treePathPadre.getLastPathComponent();
		Object obj = new Integer(((IndexItem)nodo.getUserObject()).getId());
		//Si es igual bajar en la jerarqu�a
		if(obj.equals(nodos[profundidad])) {
			//Si es el fin devolver el nodo
			if(profundidad == nodos.length - 1) {
				return treePathPadre;
			}
			//Recorrer los nodos hijos
			if(nodo.getChildCount() >= 0) {
				for(Enumeration e = nodo.children(); e.hasMoreElements();) {
					TreeNode nodoSiguiente = (TreeNode)e.nextElement();
				   	TreePath treePath = treePathPadre.pathByAddingChild(nodoSiguiente);
				   	TreePath resultado = buscarNodoArbol(arbol, treePath, nodos, profundidad + 1);
				   	//Se encontr� igualdad
					if(resultado != null)
						return resultado;											
				}
			}
			return treePathPadre;
		}
		//No se encontr� igualdad en esta jerarqu�a
		return null;
	}

	/**
	 * Devuelve el <b>TreePath</b> en el �rbol del nodo pasado por par�metro.
	 * @param nodo El nodo del cual obtener el TreePath.
	 * @return treePath El TreePath del nodo.
	 */
	public static TreePath getPathNodoArbol(DefaultMutableTreeNode nodo) {
		TreePath treePath;
		List<DefaultMutableTreeNode> lista = new ArrayList<DefaultMutableTreeNode>();
		//Crea el path que devolver�
		while(nodo != null) {
			lista.add(nodo);
			nodo = (DefaultMutableTreeNode)nodo.getParent();
		}
		Collections.reverse(lista);				
		treePath = new TreePath(lista.toArray());
		return treePath;
	}

	/**
	 * Selecciona un nodo en el �rbol a partir de su objeto IndexItem asociado.
	 * @param arbol El componente JTree.
	 * @param indexItem El objeto IndexItem asociado al nodo.
	 * @see ar.pegasus.framework.IndexItem
	 */
	public static void seleccionarNodoArbol(JTree arbol, IndexItem indexItem) {
		DefaultMutableTreeNode nodo = buscarNodo(arbol, indexItem);
		arbol.setSelectionPath(new TreePath(nodo.getPath()));
	}

	/**
	 * Busca un nodo en el �rbol. Si no lo encuentra retorna <b>null</b>.
	 * @param nodo El nodo ra�z del �rbol.
	 * @param obj El objeto asociado al nodo.
	 * @return nodo El nodo buscado o null.
	 */
	public static DefaultMutableTreeNode buscarNodo(JTree arbol, Object obj) {
		DefaultMutableTreeNode nodo;
		for(Enumeration e = getNodoRaizArbol(arbol).breadthFirstEnumeration(); e.hasMoreElements();) {
			nodo = (DefaultMutableTreeNode)e.nextElement();
			if(nodo.getUserObject().equals(obj))
				return nodo;
		}
		return null;
	}

	/**
	 * Devuelve el nodo inmediatamente <b>superior</b> al nodo seleccionado.
	 * Si el nodo seleccionado no tiene ning�n nodo inmediatamente superior o
	 * no hay ning�n nodo seleccionado en el �rbol devuelve <b>null</b>.
	 * @param arbol El componente JTree.
	 */
	public static DefaultMutableTreeNode getNodoSuperiorArbol(JTree arbol) {
		DefaultMutableTreeNode nodoSeleccionado = getNodoSeleccionadoArbol(arbol);
		if(nodoSeleccionado != null)
			return nodoSeleccionado.getPreviousSibling();
		return null;
	}

	/**
	 * Devuelve el nodo inmediatamente <b>inferior</b> al nodo seleccionado.
	 * Si el nodo seleccionado no tiene ning�n nodo inmediatamente inferior o
	 * no hay ning�n nodo seleccionado en el �rbol devuelve <b>null</b>.
	 * @param arbol El componente JTree.
	 */
	public static DefaultMutableTreeNode getNodoInferiorArbol(JTree arbol) {
		DefaultMutableTreeNode nodoSeleccionado = getNodoSeleccionadoArbol(arbol);
		if(nodoSeleccionado != null)
			return nodoSeleccionado.getNextSibling();
		return null;
	}

	/**
	 * Intercambia el nodo1 por el nodo2.
	 * Devuelve <b>true</b> si se pudo hacer el cambio. En caso contrario devuelve false.
	 * @param arbol El componente JTree.
	 * @param nodo1 El nodo 1.
	 * @param nodo2 El nodo 2.
	 */
	public static boolean intercambiarNodosArbol(JTree arbol, DefaultMutableTreeNode nodo1, DefaultMutableTreeNode nodo2) {
		if(nodo1 != null && nodo2 != null && nodo1 != nodo2) {
			DefaultTreeModel treeModel = (DefaultTreeModel)arbol.getModel();
			DefaultMutableTreeNode padre1 = (DefaultMutableTreeNode)nodo1.getParent();
			DefaultMutableTreeNode padre2 = (DefaultMutableTreeNode)nodo2.getParent();
			int indice1 = padre1.getIndex(nodo1);
			int indice2 = padre2.getIndex(nodo2);
			padre1.insert(nodo2, indice1);
			padre2.insert(nodo1, indice2);
			treeModel.reload();
			return true;
		}
		return false;
	}

	/**
	 * Devuelve todos los nodos que son hojas del �rbol.
	 * @param arbol
	 * @return hojas Los nodos hojas.
	 */
	public static Vector<DefaultMutableTreeNode> getHojas(JTree arbol) {
		return getHojas(arbol, getNodoRaizArbol(arbol));
	}

	/**
	 * Devuelve todos los nodos que son hojas del �rbol a partir de un nodo padre.
	 * @param arbol
	 * @param nodoPadre
	 * @return hojas Los nodos hojas a partir del nodo padre.
	 */
	public static Vector<DefaultMutableTreeNode> getHojas(JTree arbol, DefaultMutableTreeNode nodoPadre) {
		Vector<DefaultMutableTreeNode> hojas = new Vector<DefaultMutableTreeNode>();
		Enumeration e = nodoPadre.depthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)e.nextElement();
			if(nodo.isLeaf()) {
				hojas.add(nodo);
			}
		}
		return hojas;
	}

	/**
	 * Devuelve todos los nodos en el nivel especificado.
	 * @param arbol
	 * @param nivel
	 * @return
	 */
	public static Vector<DefaultMutableTreeNode> getNodosPorNivel(JTree arbol, int nivel) {
		Vector<DefaultMutableTreeNode> nodos = new Vector<DefaultMutableTreeNode>();
		Enumeration e = getNodoRaizArbol(arbol).breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)e.nextElement();
			if(nodo.getLevel() == nivel) {
				nodos.add(nodo);
			} else if(nodo.getLevel() > nivel) {
				break;
			}
		}
		return nodos;
	}

	/**
	 * Expande todos los nodos del �rbol.
	 * @param arbol
	 */
	public static void expandirArbol(JTree arbol) {
		for(int i = 0; i < arbol.getRowCount(); i++) {
			expandirArbol(arbol, i);
		}
	}

	/**
	 * Expande los nodos del �rbol a partir de la fila indicada.
	 * @param arbol
	 * @param row
	 */
	public static void expandirArbol(JTree arbol, int row) {
		arbol.expandRow(row);
	}

	/**
	 * Colapsa todos los nodos del �rbol.
	 * @param arbol
	 */
//	public static void colapsarArbol(JTree arbol) {
//		for(int i = 0; i < arbol.getRowCount(); i++) {
//			arbol.collapseRow(i);
//		}
//	}
    public static void colapsarArbol(JTree arbol) {
        ((DefaultTreeModel)arbol.getModel()).reload();
    }
    
	/**
	 * Busca un nodo en el �rbol por nombre. Si no lo encuentra retorna <b>null</b>.
	 * @param nodo El nodo ra�z del �rbol.
	 * @param obj El objeto asociado al nodo.
	 * @return nodo El nodo buscado o null.
	 */
    public static DefaultMutableTreeNode buscarNodoPorNombre(JTree arbol, String nombre) {
		DefaultMutableTreeNode nodo;
		for(Enumeration e = TreeUtil.getNodoRaizArbol(arbol).breadthFirstEnumeration(); e.hasMoreElements();) {
			nodo = (DefaultMutableTreeNode)e.nextElement();
			if(((IndexItem)nodo.getUserObject()).getNombre().equals(nombre))
				return nodo;
		}
		return null;
	}

    /**
     * Selecciona el nodo directamente superior al que se encuentra seleccionado actualmente en el �rbol.
     * @param arbol El arbol de de donde se intenta seleccionar el nodo.
     */
	public static void seleccionarNodoPadreNodoSeleccionadoArbol(JTree arbol) {
		IndexItem indexItem = (IndexItem)((DefaultMutableTreeNode)TreeUtil.getNodoPadreNodoSeleccionadoArbol(arbol)).getUserObject();	
		if(indexItem != null)
			TreeUtil.seleccionarNodoArbol(arbol, indexItem);
	}

}