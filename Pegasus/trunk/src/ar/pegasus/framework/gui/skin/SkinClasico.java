package ar.pegasus.framework.gui.skin;

import javax.swing.Icon;

import ar.pegasus.framework.templates.main.skin.AbstractSkin;
import ar.pegasus.framework.util.ImageUtil;

public class SkinClasico extends AbstractSkin {

	public SkinClasico() {
		super("ar/pegasus/framework/recursos/sgpthemepack.zip", new SkinDecoratorClasico(), "Estilo Clásico");
	}

	@Override
	public boolean isSkinLF() {
		return true;
	}

	@Override
	public Icon getPreview() {
		return ImageUtil.loadIcon(SkinClasico.class, "ar/pegasus/framework/gui/skin/img/preview_clasico.png");
	}

}