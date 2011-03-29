package ar.pegasus.framework.componentes;

import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
/**
 * @author oarias
 */
public class EdicionPopupMenu extends JPopupMenu {

    private int x;
    private int y;
    private JTree tree;
    public JMenuItem opcionAgregar;
	public JMenuItem opcionModificar;
	public JMenuItem opcionEliminar;

	/** M�todo constructor */
	public EdicionPopupMenu() {
		opcionAgregar = new JMenuItem("Agregar");
		add(opcionAgregar);
		opcionModificar = new JMenuItem("Modificar");
		add(opcionModificar);
		opcionEliminar = new JMenuItem("Eliminar");
		add(opcionEliminar);
	}

	/**
	 * Setea la posici�n de la esquina superior izquierda del popup men�
	 * utilizando coordenadas x, y.
	 */
    public void setLocation(int x, int y) {
		super.setLocation(x, y);
		this.x = x;
		this.y = y;
    }

	/** Muestra el popup men� en la posici�n x, y del componente que lo invoc� */
    public void show(Component invoker, int x, int y) {
		if(invoker instanceof JTree)
			tree = (JTree)invoker;
		else
			throw new IllegalArgumentException("TreePopupMenu can only be used with JTree, not " + invoker.getClass().getName());
		super.show(invoker, x, y);
    }

	/** Devuelve true si el popup men� fue invocado al menos una vez */
    public boolean isInvoked() {
		return tree != null;
    }

	/**
	 * Devuelve un TreePath para el nodo del JTree que estaba debajo del
	 * puntero del mouse cuando el popup men� fue invocado. 
	 * @return
	 */
    public TreePath getPath() {
		if(isInvoked())
			return tree.getClosestPathForLocation(x, y);
		return null;
    }

	/**
	 * Devuelve un TreePath para el nodo del JTree que estaba debajo del
	 * puntero del mouse cuando el popup men� fue invocado.
	 * @return
	 */
    public TreeNode[] getNodePath() {
		TreePath treePath = getPath();
		if(treePath != null) {
			TreeNode[] path = new TreeNode[treePath.getPathCount()];
			return path;
		}
		return null;
    }

	/**
	 * Devuelve un TreeNode para el nodo del JTree que estaba debajo del
	 * puntero del mouse cuando el popup men� fue invocado.
	 * @return treeNode
	 */
    public TreeNode getNode() {
    	TreeNode treeNode = (TreeNode)tree.getSelectionPath().getLastPathComponent();
		return treeNode;
    }

	/**
	 * Permite establecer el estado enabled de todos los items del men� popup
	 * de una sola vez.
	 * @param state
	 */
    public void setItemsEnabled(boolean b) {
		Component[] comp = getComponents();
		if(comp != null) {
			for(int i = 0; i < comp.length; i++)
				((JMenuItem)comp[i]).setEnabled(b);
		}
    }

}