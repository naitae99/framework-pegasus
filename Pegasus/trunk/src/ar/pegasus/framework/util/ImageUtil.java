package ar.pegasus.framework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.jibble.epsgraphics.EpsGraphics2D;

import ar.pegasus.framework.componentes.PException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * @author oarias
 */
public class ImageUtil {

    /**
     * Crea un objeto <b>javax.swing.ImageIcon</b>.
     * @param path El path de la imágen.
     * @return Un objeto javax.swing.ImageIcon.
     * @see javax.swing.ImageIcon
     */
    public static Icon loadIcon(String path) {
        return new ImageIcon(FileUtil.getResource(path));
    }

    /**
     * Crea un objeto <b>javax.swing.ImageIcon</b>.
     * @param aClass La clase de referencia para cargar el recurso de im�gen.
     * @param path El path de la im�gen.
     * @return Un objeto javax.swing.ImageIcon.
     * @see javax.swing.ImageIcon
     */
    public static Icon loadIcon(Class<?> aClass, String path) {
        return new ImageIcon(FileUtil.getResource(aClass, path));
    }

	/**
	 * Crea una imágen de tipo <b>java.awt.Image</b> a partir del archivo de la imágen.
	 * @param file El archivo de la imágen.
	 * @return Un objeto de tipo java.awt.Image que contiene a la imágen.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static Image readImg(File file) throws IOException {
		return ImageIO.read(file);
	}

	/**
	 * Crea una imágen de tipo <b>java.awt.Image</b> a partir del InputStream de la imágen.
	 * @param is El InputStream de la imágen.
	 * @return Un objeto de tipo java.awt.Image que contiene a la imágen.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static Image readImg(InputStream is) throws IOException {
		return ImageIO.read(is);
	}

	/**
	 * Crea una imágen de tipo <b>java.awt.Image</b> a partir de la URL de la imágen.
	 * @param url La URL de la imágen.
	 * @return Un objeto de tipo java.awt.Image que contiene a la imágen.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static Image readImg(URL url) throws IOException {
		return ImageIO.read(url);
	}

	/**
	 * Convierte una imágen al formato EPS.
	 * @param sourceFile El archivo de origen de la imágen a convertir (JPG).
	 * @param destFile El archivo de destino de la imágen EPS resultante.
	 * @throws IOException
	 */
	public static void toEps(File sourceFile, File destFile) throws IOException {
		toEps(new FileInputStream(sourceFile), new FileOutputStream(destFile));
	}

	/**
	 * Convierte una imágen al formato EPS.
	 * @param img La imágen de origen a convertir (JPG).
	 * @param destFile El archivo de destino de la imágen EPS resultante.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static void toEps(Image img, File destFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(destFile);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		EpsGraphics2D g = new EpsGraphics2D(null, fos, 0, 0, width, height);
		g.drawImage(img, 0, 0, null);
		g.flush();
		g.close();
	}

	/**
	 * Convierte una im�gen al formato EPS.
	 * @param is El InputStream de origen de la im�gen a convertir (JPG).
	 * @param os El OutputStream de destino de la im�gen EPS resultante.
	 * @throws IOException
	 */
	public static void toEps(InputStream is, OutputStream os) throws IOException {
		Image img = readImg(is);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		EpsGraphics2D g = new EpsGraphics2D(null, os, 0, 0, width, height);
		g.drawImage(img, 0, 0, null);
		g.flush();
		g.close();
	}

	/**
	 * Convierte una im�gen al formato EPS.
	 * N�tese que si se var�a la resoluci�n de la im�gen (dpi) de 72 la im�gen resultante puede variar su tama�o.
	 * @param img La im�gen a convertir (JPG).
	 * @param destFile El archivo de destino de la im�gen EPS resultante.
	 * @param width El ancho en pixeles de la im�gen EPS resultante.
	 * @param height El alto en pixeles de la im�gen EPS resultante.
	 * @param dpi La cantidad de puntos por pulgada de la im�gen EPS resultante.
	 * @throws IOException
	 * @throws InterruptedException
	 * @see java.awt.Image
	 */
	public static void toEps(Image img, File destFile, int width, int height, int dpi) throws IOException, InterruptedException {
		toEps(img, destFile, width, height, dpi, true);
	}

	/**
	 * Convierte una im�gen al formato EPS.
	 * N�tese que si se var�a la resoluci�n de la im�gen (dpi) de 72 la im�gen resultante puede variar su tama�o.
	 * @param img La im�gen a convertir (JPG).
	 * @param destFile El archivo de destino de la im�gen EPS resultante.
	 * @param width El ancho en pixeles de la im�gen EPS resultante.
	 * @param height El alto en pixeles de la im�gen EPS resultante.
	 * @param dpi La cantidad de puntos por pulgada de la im�gen EPS resultante.
	 * @param proportional Indica si el escalado ser� proporcional.
	 * @throws IOException
	 * @throws InterruptedException
	 * @see java.awt.Image
	 */
	public static void toEps(Image img, File destFile, int width, int height, int dpi, boolean proportional) throws IOException, InterruptedException {
		float scale = (float)72 / dpi;
		int swidth = Math.round(width * scale);
		int sheight = Math.round(height * scale);
		Image scaledImg = scale(toBufferedImage(img), width, height, proportional);
		FileOutputStream fos = new FileOutputStream(destFile);
		int x = (width - scaledImg.getWidth(null)) / 2;
		int y = (height - scaledImg.getHeight(null)) / 2;
		EpsGraphics2D g = new EpsGraphics2D(null, fos, 0, 0, swidth, sheight);
		g.scale(scale, scale);
		g.drawImage(scaledImg, x, y, null);
		g.flush();
		g.close();
	}

	/**
	 * Convierte un objeto <b>java.awt.Image</b> a <b>java.awt.image.BufferedImage</b>.
	 * @param img El objeto java.awt.Image.
	 * @return Un objeto java.awt.image.BufferedImage.
	 * @throws InterruptedException
	 * @see java.awt.Image
	 * @see java.awt.image.BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) throws InterruptedException {
		BufferedImage bimg = null;
		boolean hasAlpha = hasAlpha(img);
		int transparency = Transparency.OPAQUE;
		if(hasAlpha) {
			transparency = Transparency.BITMASK;
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		if(width > 0 && height > 0) {
			bimg = gc.createCompatibleImage(width, height, transparency);
			if(bimg == null) {
				int type = BufferedImage.TYPE_INT_RGB;
				if(hasAlpha) {
					type = BufferedImage.TYPE_INT_ARGB;
				}
				bimg = new BufferedImage(width, height, type);
			}
			Graphics g = bimg.createGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
		}
		return bimg;
	}

	/**
	 * Devuelve <b>true</b> si la im�gen tiene pixeles transparentes.
	 * @param img La im�gen.
	 * @return True si la im�gen tiene pixeles transparentes.
	 * @throws InterruptedException
	 * @see java.awt.Image
	 */
	public static boolean hasAlpha(Image img) throws InterruptedException {
		if(img instanceof BufferedImage) {
			BufferedImage bimg = (BufferedImage)img;
			return bimg.getColorModel().hasAlpha();
		} else {
			PixelGrabber pg = new PixelGrabber(img, 0, 0, 1, 1, false);
			pg.grabPixels();
			ColorModel cm = pg.getColorModel();
			return cm.hasAlpha();
		}
	}

	/**
	 * Convierte un objeto <b>java.awt.image.BufferedImage</b> a <b>java.awt.Image</b>.
	 * @param bimg El objeto java.awt.image.BufferedImage.
	 * @return Un objeto java.awt.Image.
	 * @see java.awt.image.BufferedImage
	 * @see java.awt.Image
	 */
	public static Image toImage(BufferedImage bimg) {
		return Toolkit.getDefaultToolkit().createImage(bimg.getSource());
	}

	/**
	 * Convierte un array de bytes (array con los pixeles de la im�gen) a un objeto <b>java.awt.Image</b>.
	 * @param buf El array de pixeles de la im�gen.
	 * @return Un objeto java.awt.Image.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static Image toImage(byte[] buf) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		Image img = ImageIO.read(is);
		return img;
	}

	/**
	 * Convierte un im�gen de tipo <b>java.awt.image.BufferedImage</b> a un <b>array de bytes</b>.
	 * @param bimg La im�gen de tipo java.awt.image.BufferedImage a convertir.
	 * @return Un array de bytes de los pixeles de la im�gen.
	 * @throws IOException
	 * @see java.awt.image.BufferedImage
	 */
	public static byte[] toByteArray(BufferedImage bimg) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bimg, "jpeg", os);
		byte[] buff = os.toByteArray();
		os.close();
		return buff;
	}

	/**
	 * Convierte un im�gen de tipo <b>java.awt.Image</b> a un <b>array de bytes</b>.
	 * @param bimg La im�gen de tipo java.awt.Image a convertir.
	 * @return Un array de bytes de los pixeles de la im�gen.
	 * @throws IOException
	 * @see java.awt.Image
	 */
	public static byte[] toByteArray(Image img) throws InterruptedException, IOException {
		BufferedImage bimg = toBufferedImage(img);
		return toByteArray(bimg);
	}

	/**
	 * Escala la imágen al tama�o pasado por parámetro.
	 * @param img La imágen a escalar.
	 * @param width El ancho de la imágen escalada.
	 * @param height El alto de la imágen escalada.
	 * @param proportional Indica si el escalado será proporcional.
	 * @return scaledImg La nueva imágen escalada.
	 */
	public static Image scale(Image img, int width, int height, boolean proportional) {
		Image scaledImg = null;
		if(proportional) {
			float scale = Math.min((float)width / img.getWidth(null), (float)height / img.getHeight(null));
			int swidth = (int)Math.round(scale * img.getWidth(null));
			int sheight = (int)Math.round(scale * img.getHeight(null));
			scaledImg = img.getScaledInstance(swidth, sheight, Image.SCALE_DEFAULT);
		} else {
			scaledImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		}
		return scaledImg;
	}

//	public static byte[] scale(byte[] buffer, int width, int height, boolean proportional) throws PException {
//		try {
//			BufferedImage previewImg = ImageIO.read(new ByteArrayInputStream(buffer));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
//			encoder.encode( toBufferedImage(scale(previewImg, width, height, proportional)) );
//			return baos.toByteArray();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new PException("No se pudo escalar la im�gen", e) ;
//		}
//	}

	public static byte[] scale(byte[] buffer, int width, int height) throws PException {
		try {
			BufferedImage previewImg = ImageIO.read(new ByteArrayInputStream(buffer));
			if(previewImg.getWidth() > previewImg.getHeight()) {
				height = -1;
			} else {
				width = -1;
			}
			Image scaledImg = previewImg.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			previewImg = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = previewImg.createGraphics();
			g.drawImage(scaledImg, 0, 0, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
			encoder.encode(previewImg);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PException("No se pudo escalar la imágen", e) ;
		}
	}

	/**
	 * Devuelve <b>true</b> si la imágen es cuadrada.
	 * @param img La imágen.
	 * @return True si la im�gen es cuadrada.
	 */
	public static boolean isSquare(Image img) {
		return img.getWidth(null) == img.getHeight(null);
	}
	
	
	/**
	 * Copia un ImageIcon al directorio especificado
	 * @param img La imagen a copiar
	 * @param dir El directorio destino (sin \ al final)
	 */
	public static void toFile(ImageIcon imag, String dir) throws IOException
	{
		File e = new File(dir);
		String name = getName(imag);
		e.mkdirs();
		Image img = imag.getImage();

		BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
	
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		
		ImageIO.write(bi, "jpg", new File(dir+"\\"+name));
	}

	private static String getName(ImageIcon imag)
	{
		StringBuffer sb = new StringBuffer();
		String path = imag.getDescription();
		
		for(int i = path.lastIndexOf("\\")+1;i<path.length();i++)
			sb.append(path.charAt(i));
		
		return sb.toString();
	}

	/**
	 * Copia un ImageIcon al directorio especificado
	 * @param img La imagen a copiar
	 * @param dir El directorio destino (sin \ al final)
	 * @return name El nombre de la imagen
	 */
	public static String imageToFile(ImageIcon imag, String dir) throws IOException
	{
		File e = new File(dir);
		String name = getName(imag);
		e.mkdirs();
		Image img = imag.getImage();

		BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
	
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		
		ImageIO.write(bi, "jpg", new File(dir+"\\"+name));
		return name;
	}

	/**
	 * Retorna un BufferdImage a partir de un byte array que contiene una foto.
	 * 
	 * @param foto
	 * @return
	 */
	public static BufferedImage getImagenFoto(byte[] foto) {
		BufferedImage imagen = null;
		if(foto != null) {
			try {
				imagen = getImagenAsBufferedImage(foto);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 
		}
		return imagen;
	}

	/**
	 * Metodo interno para obtener una imagen a partir de un byte array.
	 * 
	 * @param foto
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage getImagenAsBufferedImage(byte[] foto) throws IOException {
		BufferedImage image = null;
		if (foto != null) {
			InputStream in = new ByteArrayInputStream(foto);
			image = javax.imageio.ImageIO.read(in);
		}
		return image;
	}		
}