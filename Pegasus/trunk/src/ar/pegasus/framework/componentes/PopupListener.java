package ar.pegasus.framework.componentes;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
/**
 * Clase para el manejo de eventos de un popup men� o men� emergente.
 * @author oarias
 */
public class PopupListener extends MouseAdapter {

    private JPopupMenu menu;
    private MouseEvent evt;
    private boolean selectTreeOnPopup;

	/**
	 * M�todo constructor.
	 * @param menu
	 */
    public PopupListener(JPopupMenu menu) {
		this.menu = menu;
		selectTreeOnPopup = false;
    }

	/**
	 * Devuelve el JPopupMenu que se est� monitoreando.
	 * @return menu
	 */
    public JPopupMenu getPopupMenu() {
		return menu;
    }

	/**
	 * Devuelve <b>true</b> si el popup men� seleccionar� el item del �rbol.
	 * S�lo v�lido si el componente al que el popup men� est� atachado es
	 * un JTree. De lo contrario es ignorado.
	 * @return selectTreeOnPopup
	 */
    public boolean isSelectTreeOnPopup() {
		return selectTreeOnPopup;
    }

	/**
	 * Pone en true al modo selectTreeOnPopup. Cuando es true y el componente
	 * que lanza el popup men� es un JTree, el nodo del tree por debajo del
	 * puntero del mouse se seleccionar� al lanzar el evento popup.
	 * @param flag
	 */
    public void setSelectTreeOnPopup(boolean flag) {
		selectTreeOnPopup = flag;
    }

	/**
	 * Invocado cuando el bot�n del mouse es presionado sobre un componente.
	 */
    public void mousePressed(MouseEvent evt) {
		showPopup(evt);
    }

	/**
	 * Invocado cuando el bot�n del mouse es soltado sobre un componente.
	 */
    public void mouseReleased(MouseEvent evt) {
		showPopup(evt);
    }

	/**
	 * Muestra el men� popup. Si selectTreeOnPopup es true entonces el nodo del
	 * tree por debajo del puntero del mouse se seleccionar�.
	 * @param evt
	 */
    private void showPopup(MouseEvent evt) {
		if(evt.isPopupTrigger()) {
			if(isSelectTreeOnPopup() && evt.getSource() instanceof JTree) {
				JTree tree = (JTree)evt.getSource();
				if(tree.isEnabled()) {
					tree.setSelectionRow(tree.getRowForLocation(evt.getX(), evt.getY()));
					/* Si selectTreeOnPopup es true y hay alg�n nodo del tree seleccionado
					 * entonces mostrar el popup men�.
					 */
					if(!tree.isSelectionEmpty()) {
						evt = new MouseEvent((Component)evt.getSource(), evt.getID(),
											 evt.getWhen(), evt.getModifiers(), evt.getX(),
											 evt.getY(), evt.getClickCount(),
											 evt.isPopupTrigger());
						menu.show(evt.getComponent(), evt.getX(), evt.getY());
					}
				}

			} else {
				evt = new MouseEvent((Component)evt.getSource(), evt.getID(),
									 evt.getWhen(), evt.getModifiers(), evt.getX(),
									 evt.getY(), evt.getClickCount(),
									 evt.isPopupTrigger());
				menu.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
    }

	/**
	 * Devuelve el �ltimo evento que caus� que el popup men� se mostrara.
	 * Util para saber la acci�n del mouse que lo caus�.
	 * @return evt
	 */
    public MouseEvent getLastEvent() {
		return evt;
    }

}