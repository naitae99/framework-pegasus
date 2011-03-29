package ar.pegasus.framework.componentes;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import org.apache.taglibs.string.util.StringW;

import ar.pegasus.framework.util.StringUtil;

/**
 * Clase que sirve para acumular mensajes provenientes de validaciones de GUI
 * para luego mostrarlos todos juntos (armar el mensaje global). 
 * @author oarias
 * @see PJOptionPane
 */
public class ValidationManager {

	private List<String> inputMessages;
	private Component owner;
	private String title;
	private int type;
	private JComponent currentComponent;
	private Set<JComponent> components;
	private int wrap;
	private Color highlightColor;
	private boolean highlight;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.RED;
	private static final int DEFAULT_WRAP = 100;

	/**
	 * Método Constructor
	 * @param owner El owner desde donde se está validando
	 * @param title El título a mostrar en la ventana que mostrará el mensaje global
	 * @param type Un entero denotando el tipo de ventana a mostrar de acuerdo a la criticidad del mensaje.
	 * 		   	   Puede ser {@link PJOptionPane#WARNING_MSG}, {@link PJOptionPane#ERROR_MSG} 
	 * 			   {@link PJOptionPane#INFO_MSG}
	 */
	public ValidationManager(Component owner, String title, int type) {
		this.owner = owner;
		this.title = title;
		this.type = type;
		this.inputMessages = new ArrayList<String>();
		this.components = new HashSet<JComponent>();
		this.wrap = DEFAULT_WRAP;
		this.highlightColor = DEFAULT_HIGHLIGHT_COLOR;
		this.highlight = true;
	}

	/**
	 * Agrega un mensaje.
	 * @param msg El mensaje a agregar
	 */
	public void addMessage(String msg) {
		addMessage(msg, new JComponent[0]);
	}

	/**
	 * Agrega un mensaje y especifica en que componente poner el foco
	 * luego de mostrarse el mensaje global.
	 * @param msg El mensaje a agregar
	 * @param currentComponent El componente donde se originó el mensaje
	 */
	public void addMessage(String msg, JComponent... currentComponent) {
		if(msg != null && msg.length() > 0) {
			StringBuffer sb = new StringBuffer("* ");
			sb.append(msg);
			inputMessages.add(sb.toString());
			if(this.currentComponent == null && currentComponent != null && currentComponent.length > 0 && currentComponent[0] != null) {
				this.currentComponent = currentComponent[0];
			}
			if(currentComponent != null) {
				components.addAll(Arrays.asList(currentComponent));
			}
		}
	}

	/**
	 * Permite setear la longitud del renglón del p�rrafo que contendrá
	 * el mensaje global.
	 * @param wrap La longitud del renglón que contendr� el mensaje global 
	 */
	public void setWrap(int wrap) {
		this.wrap = wrap;
	}

	/**
	 * Permite setear el color de resaltado de los componentes.
	 * @param highlightColor El color de resaltado de los componentes 
	 */
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	/**
	 * Muestra el mensaje global concatenando todos los mensajes agregados,
	 * el foco será puesto en el primer componente recibido. Los mensajes se mostrarán uno debajo del otro.
	 * @return <code>true</code> Si no hubo ningún mensaje agregado (no falló ninguna validación),
	 * 		   <code>false</code> caso contrario	 
	 */
	public boolean validate() {
		if(!inputMessages.isEmpty()) {
			String fullMsg = StringUtil.getCadena(inputMessages, "\n");
			fullMsg = StringW.wordWrap(fullMsg, wrap);
			boolean returnValue = false;
			if(type == PJOptionPane.ERROR_MSG) {
				PJOptionPane.showErrorMessage(owner, fullMsg, title);
			} else if(type == PJOptionPane.WARNING_MSG) {
				PJOptionPane.showWarningMessage(owner, fullMsg, title);
			} else if (type == PJOptionPane.QUESTION_MSG){
				int result = PJOptionPane.showQuestionMessage(owner, fullMsg + "\n     ¿Desea Continuar?", title);
				returnValue = PJOptionPane.YES_OPTION == result;
				if (!returnValue){
					doFocus();
					doHighlighting();
				}
				return returnValue;
			} else {
				PJOptionPane.showInformationMessage(owner, fullMsg, title);
			} 
			doFocus();
			doHighlighting();
			return returnValue;
		}
		return true;
	}

	public void doFocus() {
		if(currentComponent != null) {
			currentComponent.requestFocus();
		}
	}

	private void doHighlighting() {
		if(highlight){
			for(JComponent c : components) {
				c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(highlightColor), c.getBorder()));
			}				
		}
	}

	/** Reestablece el borde original de los componentes y borra los mensajes */
	public void clear() {
		inputMessages.clear();
		currentComponent = null;
		for(JComponent c : components) {
			Border border = c.getBorder();
			if(border instanceof CompoundBorder) {
				c.setBorder(((CompoundBorder)border).getInsideBorder());
			}
		}
		components.clear();
	}
	
	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

}