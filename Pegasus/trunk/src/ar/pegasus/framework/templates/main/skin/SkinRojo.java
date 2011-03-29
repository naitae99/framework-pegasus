package ar.pegasus.framework.templates.main.skin;

import javax.swing.Icon;

import ar.pegasus.framework.gui.skin.SkinClasico;
import ar.pegasus.framework.gui.skin.SkinDecoratorModerno;
import ar.pegasus.framework.util.ImageUtil;

public class SkinRojo extends AbstractSkin {

	public SkinRojo() {
		super("ar/pegasus/framework/recursos/sgpthemepack_rojo.zip", new SkinDecoratorModerno(), "Estilo Rojizo");
	}

	@Override
	public boolean isSkinLF() {
		return true;
	}

	@Override
	public Icon getPreview() {
		return ImageUtil.loadIcon(SkinClasico.class, "ar/pegasus/framework/gui/skin/img/preview_rojo.png");
	}

}