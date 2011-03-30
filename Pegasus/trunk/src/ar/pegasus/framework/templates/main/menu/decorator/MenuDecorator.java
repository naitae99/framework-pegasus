package ar.pegasus.framework.templates.main.menu.decorator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaElementos;

public class MenuDecorator {

	private Map<String, String> table;

	public MenuDecorator() {
		table = new HashMap<String, String>();
	}

	public void putIcono(String key, String iconPath) {
		table.put(key, iconPath);
	}

	public Icon getIcono(String key) {
		String iconPath = table.get(key);
		return iconPath == null ? ImageUtil.loadIcon(RutaElementos.BLANK_ICON) : ImageUtil.loadIcon(iconPath);
	}

}