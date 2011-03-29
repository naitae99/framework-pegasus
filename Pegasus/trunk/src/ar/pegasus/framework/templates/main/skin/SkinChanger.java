package ar.pegasus.framework.templates.main.skin;

import java.awt.Frame;

import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.componentes.PException;

/**
 * Clase helper para cambiar din�micamente el skin de un frame.
 * @author oarias
 */
public class SkinChanger {

	private AbstractSkin skin;
	private Frame frame;

	/**
	 * Método constructor.
	 * @param newSkin El nuevo skin a ser aplicado.
	 * @param frame El frame al que se le aplicar� el cambio de skin.
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public SkinChanger(AbstractSkin newSkin, Frame frame) {
		this.skin = newSkin;
	}

	/**
	 * @return Devuelve el skin.
	 */
	public AbstractSkin getSkin() {
		return skin;
	}

	/**
	 * Setea el skin a ser aplicado.
	 * @param skin
	 */
	public void setSkin(AbstractSkin skin) {
		this.skin = skin;
	}

	/**
	 * @return Devuelve el frame.
	 */
	public Frame getFrame() {
		return frame;
	}

	/**
	 * Setea el frame al que se le aplicar� el cambio de skin.
	 * @param frame
	 */
	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	/**
	 * Setea una propiedad de sistema con el nuevo skin a aplicar.
	 * @throws CLException
	 */
	public void changeSkin() throws PException {
		PJOptionPane.showInformationMessage(getFrame(), "Deberá reiniciar la aplicación para que los cambios del estilo visual sean efectuados.", "Estilo Visual");
		System.setProperty("skinDefault", skin.getCanonicalName());
	}

}