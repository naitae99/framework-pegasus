package ar.pegasus.framework.gui.skin;

import javax.swing.Icon;

import ar.pegasus.framework.templates.main.skin.AbstractSkin;
import ar.pegasus.framework.util.ImageUtil;

public class SkinModerno extends AbstractSkin {

	public SkinModerno() {
		super("ar/pegasus/framework/recursos/sgpthemepack_azul.zip", new SkinDecoratorModerno(), "Estilo Moderno");
	}

	@Override
	public boolean isSkinLF() {
		return true;
	}

	@Override
	public Icon getPreview() {
		return ImageUtil.loadIcon(SkinClasico.class, "ar/pegasus/framework/gui/skin/img/preview_moderno.png");
	}

}