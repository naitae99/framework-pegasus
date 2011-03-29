package ar.pegasus.framework.templates;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import ar.pegasus.framework.util.Accion;

/**
 * Clase base para los dialogos con un formato predefinido.
 * 
 * @author oarias
 *
 */
public class LayeredDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private static Accion action = new Accion();

	private boolean hide = false ;
	private boolean cambiosSinGuardar = false;
	
	public LayeredDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		keyStrokeBinding(getRootPane()) ;
	}

	public LayeredDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		keyStrokeBinding(getRootPane()) ;
	}
	
	public LayeredDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
//		keyStrokeBinding(getRootPane()) ;
		keyStrokeBinding(createRootPane()) ;
	}
	
	public LayeredDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
//		keyStrokeBinding(getRootPane()) ;
		keyStrokeBinding(createRootPane()) ;
	}

	protected JRootPane createRootPane() {
		  KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		  JRootPane rootPane = new JRootPane();
		  ActionListener actionListener = new ActionListener() {
			  public void actionPerformed(ActionEvent actionEvent) {
			    cerrarDialogo();
			  }
			};

		  rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		  return rootPane;
		}

	
	private static void keyStrokeBinding (JRootPane rootPane) {
        KeyStroke ks = KeyStroke.getKeyStroke("ESCAPE");
        Object binding = action.getValue(javax.swing.Action.NAME);
        rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(ks, binding);
        rootPane.getActionMap().put(binding, action);
	}

	public void cerrarDialogo(){
		setVisible(false);
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
	        action.add(this) ;
		} else {
			action.remove(this) ;
		}
		super.setVisible(b);
	}
	
	/**
	 * Permite agregar el Frame principal para que se capture la tecla.
	 * @param frame
	 */
	public static void add(JFrame frame) {
		keyStrokeBinding(frame.getRootPane());
	}

	@Override
	public String toString() {
		return this.getName() + this.getBounds().toString().substring(18);
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public void setCambiosSinGuardar(boolean cambiosSinGuardar) {
		this.cambiosSinGuardar = cambiosSinGuardar;
	}

	public boolean isCambiosSinGuardar() {
		return cambiosSinGuardar;
	}
	
}
