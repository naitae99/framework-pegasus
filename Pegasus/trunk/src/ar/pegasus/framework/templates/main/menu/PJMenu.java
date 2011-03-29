package ar.pegasus.framework.templates.main.menu;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JMenu;

import ar.pegasus.framework.templates.main.menu.decorator.MenuDecorator;
import ar.pegasus.framework.util.GuiUtil;

public class PJMenu extends JMenu {
	private static final long serialVersionUID = -5088951601541551285L;

	private MenuDecorator decorator;

	public PJMenu() {
		super();
	}

	public PJMenu(String nombre) {
		super(nombre);
	}

	public PJMenu(String nombre, char mnemonic) {
		super(nombre);
		setMnemonic(mnemonic);
	}

	public PJMenu(Action action) {
		super(action);
	}

	public MenuDecorator getMenuDecorator() {
		return decorator;
	}

	public void setMenuDecorator(MenuDecorator decorator) {
		this.decorator = decorator;
	}

	public Frame getFrame() {
		return GuiUtil.getFrameForComponent(this);
	}

}