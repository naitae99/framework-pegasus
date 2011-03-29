package ar.pegasus.framework.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
/**
 * Componente que permite la entrada de texto para la b�squeda autom�tica de items de una lista
 * que concuerden con dicho texto.
 * @author oarias
 */
public class PBusquedaTextField extends JTextField {
	private static final long serialVersionUID = 4620740957653086018L;

	private JList list;
	private DefaultListModel defaultModel;
	private DefaultListModel currentModel;

	/**
	 * M�todo constructor.
	 * @param list La lista asociada en la cual se realizar� la b�squeda del texto.
	 * @param filterList Indica si se debe filtrar la lista autom�ticamente con cada entrada de texto.
	 */
	public PBusquedaTextField(JList list, boolean filterList) {
		this.list = list;
		if(filterList) {
			createModels(list.getModel());
			addKeyListener(new FindAndFilterListener());
		} else {
			addKeyListener(new FindListener());
		}
	}

	private void createModels(ListModel model) {
		defaultModel = new DefaultListModel();
		currentModel = new DefaultListModel();
		for(int i = 0; i < model.getSize(); i++) {
			defaultModel.add(i, model.getElementAt(i));
			currentModel.add(i, model.getElementAt(i));
		}
		list.setModel(currentModel);
	}

	private void restoreModel() {
		for(int i = 0; i < defaultModel.getSize(); i++) {
			currentModel.add(i, defaultModel.get(i));
		}
	}

	class FindListener extends KeyAdapter {
		public void keyReleased(KeyEvent evt) {
			String text = getText();
			if(text != null && text.trim().length() > 0) {
				for(int i = 0; i < list.getModel().getSize(); i++) {
					Object obj = list.getModel().getElementAt(i);
					if(obj.toString().toLowerCase().startsWith(text.toLowerCase())) {
						list.setSelectedValue(obj, true);
						break;
					}
				}
			} else {
				list.clearSelection();
			}
		}
	}

	class FindAndFilterListener extends KeyAdapter {
		public void keyReleased(KeyEvent evt) {
			String text = getText();
			currentModel.clear();
			if(text != null && text.trim().length() > 0) {
				Object selectedItem = null;
				for(int i = 0; i < defaultModel.getSize(); i++) {
					Object obj = defaultModel.get(i);
					if(obj.toString().toLowerCase().startsWith(text.toLowerCase())) {
						if(selectedItem == null) {
							selectedItem = obj;
						}
						currentModel.addElement(obj);
					}
				}
				list.setSelectedValue(selectedItem, true);
			} else {
				restoreModel();
				list.clearSelection();
			}
		}
	}

}