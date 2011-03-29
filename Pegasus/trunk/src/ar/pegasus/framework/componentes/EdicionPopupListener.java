package ar.pegasus.framework.componentes;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JTree;
/**
 * EdicionPopupListener
 * @author AGEA
 */
public final class EdicionPopupListener extends PopupListener {

	private JPopupMenu menu;
	private int nivelMinimo = -1;
	private int nivelMaximo = -1;
	private final int NIVEL_MINIMO_DEFAULT = 1;

	/**
	 * M�todo constructor.
	 * @param menu
	 */
	public EdicionPopupListener(JPopupMenu menu) {
		super(menu);
		this.menu = menu;
	}

	/**
	 * M�todo constructor.
	 * @param menu
	 * @param nivelMinimo
	 * @param nivelMaximo
	 */
	public EdicionPopupListener(JPopupMenu menu, int nivelMinimo, int nivelMaximo) {
		super(menu);
		this.menu = menu;
		this.nivelMinimo = nivelMinimo;
		this.nivelMaximo = nivelMaximo;
	}

	/**
	 * Retorna el <b>nivel m�nimo</b> definido para el �rbol asociado.
	 * @return nivelMinimo
	 */
	public int getNivelMinimo() {
		return nivelMinimo;
	}

	/**
	 * Setea el <b>nivel m�nimo</b> para el �rbol asociado.
	 * @param nivelMinimo
	 */
	public void setNivelMinimo(int nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}

	/**
	 * Retorna el <b>nivel m�ximo</b> para el �rbol asociado.
	 * @return nivelMaximo
	 */
	public int getNivelMaximo() {
		return nivelMaximo;
	}

	/**
	 * Setea el <b>nivel m�ximo</b> para el �rbol asociado.
	 * @param nivelMaximo
	 */
	public void setNivelMaximo(int nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}

	/**
	 * Invocado cuando el bot�n del mouse es presionado sobre un componente.
	 */
	public final void mousePressed(MouseEvent evt) {
		showPopup(evt);
	}

	/**
	 * Invocado cuando el bot�n del mouse es soltado sobre un componente.
	 */
	public final void mouseReleased(MouseEvent evt) {
		showPopup(evt);
	}

	/**
	 * Muestra el men� popup. Si selectTreeOnPopup es true entonces el nodo del
	 * tree por debajo del puntero del mouse se seleccionar�.
	 * @param evt
	 */
	private final void showPopup(MouseEvent evt) {
		if(evt.isPopupTrigger()) {
			if(isSelectTreeOnPopup() && evt.getSource() instanceof JTree) {
				JTree tree = (JTree)evt.getSource();
				if(tree.isEnabled())
					tree.setSelectionRow(tree.getRowForLocation(evt.getX(), evt.getY()));

				/* Si selectTreeOnPopup es true y hay alg�n nodo del tree seleccionado
				 * entonces mostrar el popup men�.
				 */
				if(!tree.isSelectionEmpty() && tree.isEnabled()) {
					evt = new MouseEvent((Component)evt.getSource(), evt.getID(),
										 evt.getWhen(), evt.getModifiers(), evt.getX(),
										 evt.getY(), evt.getClickCount(),
										 evt.isPopupTrigger());

					//Si el nodo seleccionado pertenece al nivel 'nivelMaximo'
					if(tree.getSelectionPath().getPathCount() == nivelMaximo)
						((JMenuItem)menu.getComponent(0)).setEnabled(false);
					else
						((JMenuItem)menu.getComponent(0)).setEnabled(true);

					//Si el nodo seleccionado pertenece al nivel 'nivelMinimo'
					if(tree.getSelectionPath().getPathCount() == nivelMinimo) {
						((JMenuItem)menu.getComponent(1)).setEnabled(false);
						((JMenuItem)menu.getComponent(2)).setEnabled(false);
					} else {
						((JMenuItem)menu.getComponent(1)).setEnabled(true);
						((JMenuItem)menu.getComponent(2)).setEnabled(true);
					}
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		}
	}

}