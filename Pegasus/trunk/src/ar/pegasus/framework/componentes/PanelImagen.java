package ar.pegasus.framework.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.util.FileUtil;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaImagenes;


public class PanelImagen extends JPanel {
	private static final long serialVersionUID = -4559679676293856841L;
	//Imagen a mostrar
	Image imagen;

	//constructor
//	public PanelImagen(Image imagen) {
//		setImagen(imagen);
//	}

	//paint
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		if (getImagen() != null) { 
			g2.drawImage(getImagen(), 0, 0, this);
			g2.setColor(Color.MAGENTA);
		}
	}
	
	private Image escalarImagen(Image imagen) {
		try {
			return escalarImagen(ImageUtil.toBufferedImage(imagen));
		} catch (InterruptedException e) {
			BossError.gestionarError(new PException(e.getMessage()));
		}
		return null;
	}

	public Image escalarImagen(BufferedImage imagen){
		if(getWidth() == 0 || getHeight() == 0) return null;
		return ImageUtil.scale(imagen,getWidth(), getHeight(),true);
	}
	
	public void clear(){
		try {
			setImagen(escalarImagen(ImageUtil.readImg(FileUtil.getResource(RutaImagenes.RUTA_IMAGEN_NO_DISPONIBLE))));
		} catch (IOException e) {
			BossError.gestionarError(new PException(e.getMessage()));
		}
	}


	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = escalarImagen(imagen);
		
	}

	public void setImagen(byte[] foto) {
		try {
			setImagen(ImageUtil.toImage(foto));
		} catch (IOException e) {
			BossError.gestionarError(new PException(e.getMessage()));
		}
	}
}
