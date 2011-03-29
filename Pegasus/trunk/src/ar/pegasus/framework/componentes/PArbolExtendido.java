package ar.pegasus.framework.componentes;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import ar.pegasus.framework.util.TreeUtil;

/**
 * Arbol cuyos nodos pueden ser invisibles. Estos nodos deben ser de tipos
 * InvisibleNode en un modelo InvisibleTreeNode (ambas inner clases de �sta) 
 * @author DFogelman
 *
 */
@SuppressWarnings("serial")
public class PArbolExtendido extends JTree {

	/**
	 * Nodo invisibilizable que extiene de DefaultMutableTreeNode. Debe estar dentro 
	 * de un InvisibleTreeModel para que se puedan usar sus caracter�sticas.
	 * @author DFogelman
	 */
	@SuppressWarnings("serial")
	public static class InvisibleNode extends DefaultMutableTreeNode {
		protected boolean isVisible;

		public InvisibleNode() {
			this(null);
		}

		@Override
		public void add(MutableTreeNode newChild) {
			super.add(newChild);
		}

		public InvisibleNode(DefaultMutableTreeNode nodo) {
			this(nodo.getUserObject());
		}

		public InvisibleNode(Object userObject) {
			this(userObject, true, true);
		}

		public InvisibleNode(Object userObject, boolean allowsChildren, boolean isVisible) {
			super(userObject, allowsChildren);
			this.isVisible = isVisible;
		}

		public TreeNode getChildAt(int index, boolean filterIsActive) {
			if(!filterIsActive) {
				return super.getChildAt(index);
			}
			if(children == null) {
				throw new ArrayIndexOutOfBoundsException("node has no children");
			}
			int realIndex = -1;
			int visibleIndex = -1;
			Enumeration<?> enume = children.elements();
			while(enume.hasMoreElements()) {
				InvisibleNode node = (InvisibleNode)enume.nextElement();
				if(node.isVisible()) {
					visibleIndex++;
				}
				realIndex++;
				if(visibleIndex == index) {
					return (TreeNode)children.elementAt(realIndex);
				}
			}
			throw new ArrayIndexOutOfBoundsException("index unmatched");
//			return (TreeNode)children.elementAt(index);
		}

		public int getChildCount(boolean filterIsActive) {
			if(!filterIsActive) {
				return super.getChildCount();
			}
			if(children == null) {
				return 0;
			}
			int count = 0;
			Enumeration<?> enume = children.elements();
			while(enume.hasMoreElements()) {
				InvisibleNode node = (InvisibleNode)enume.nextElement();
				if(node.isVisible()) {
					count++;
				}
			}
			return count;
		}

		public void setVisible(boolean visible) {
			this.isVisible = visible;
		}

		public boolean isVisible() {
			return isVisible;
		}
	}

	/**
	 * Modelo de arbol extiende DefaultTreeModel. Para invisibilizar nodos,
	 * �stos deben ser InvisibleNode. De todos modos, el modelo soporta cualquier
	 * tipo de TreeNode
	 * @author DFogelman
	 *
	 */
	@SuppressWarnings("serial")
	public static class InvisibleTreeModel extends DefaultTreeModel {
		protected boolean filterIsActive;

		public InvisibleTreeModel(TreeNode root) {
			this(root, false);
		}

		public InvisibleTreeModel(TreeNode root, boolean asksAllowsChildren) {
			this(root, false, false);
		}

		public InvisibleTreeModel(TreeNode root, boolean asksAllowsChildren, boolean filterIsActive) {
			super(root, asksAllowsChildren);
			this.filterIsActive = filterIsActive;
		}

		public void activateFilter(boolean newValue) {
			filterIsActive = newValue;
		}

		public boolean isActivatedFilter() {
			return filterIsActive;
		}

		public Object getChild(Object parent, int index) {
			if(filterIsActive) {
				if(parent instanceof InvisibleNode) {
					return ((InvisibleNode)parent).getChildAt(index, filterIsActive);
				}
			}
			return ((TreeNode)parent).getChildAt(index);
		}

		public int getChildCount(Object parent) {
			if(filterIsActive) {
				if(parent instanceof InvisibleNode) {
					return ((InvisibleNode)parent).getChildCount(filterIsActive);
				}
			}
			return ((TreeNode)parent).getChildCount();
		}
	}

	public PArbolExtendido() {
		this(new InvisibleTreeModel(new InvisibleNode((DefaultMutableTreeNode)TreeUtil.inicializarArbol("Arbol").getRoot())));
	}

	public PArbolExtendido(TreeNode root) {
		this(new InvisibleTreeModel(root));
	}
	
	public PArbolExtendido(TreeModel model) {
		super(model);
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	public void activarVisiblidadNodosEnArbol(boolean visibilidad) {
		((InvisibleTreeModel)getModel()).activateFilter(visibilidad);
	}

}