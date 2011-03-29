package ar.pegasus.framework.boss;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.JTableHeader;

import ar.pegasus.framework.componentes.PJTable;
import ar.pegasus.framework.util.DateUtil;
import ar.pegasus.framework.util.jasper.JasperWrapper;
import ar.pegasus.framework.util.jasper.JasperWrapperProperties;

/**
 * Clase encargada de imprimir reportes y recibos.
 * @author oarias
 */
public class BossImpresion {

	private final static int ANCHO_L = 801;
	private final static int ALTO_L = 468;
	private final static int ANCHO_P = 525;
	private final static int ALTO_P = 729;
	private String nombreAplicacion;

	private static BossImpresion instance = null;
	
	protected BossImpresion(){
		
	}
	
	public static BossImpresion getInstance(){
		if (instance == null){
			instance = new BossImpresion();
		}
		return instance;
	}
	
	/**
	 * Imprime el listado de reservas contenidas en la tabla.
	 * @param tabla
	 * @param titulo
	 * @param subtitulo
	 * @param confirmacion
	 */
	public void imprimirListado(PJTable tabla, String titulo, String subtitulo, String filtros, boolean confirmacion) {
		if( tabla.getRowCount()>0) {
			JasperWrapper.imprimirReporte(getProperties(titulo, subtitulo,filtros, getData(tabla), tabla.getWidth() > ANCHO_P), isVerPreview(), titulo, new Dimension(810, 630), confirmacion);
		}
	}
	
	/**
	 * Imprime el listado de reservas contenidas en la tabla.
	 * @param tabla
	 * @param titulo
	 * @param subtitulo
	 */
	public void imprimirListado(PJTable tabla, String titulo, String subtitulo, String filtros) {
		if( tabla.getRowCount()>0) {
			JasperWrapper.imprimirReporte(getProperties(titulo, subtitulo,filtros, getData(tabla), tabla.getWidth() > ANCHO_P), isVerPreview(), titulo, new Dimension(810, 630));
		}
	}

	protected List<ElementoListado> getData(PJTable tabla) {
		tabla.clearSelection();

		int anchoTabla = tabla.getWidth();
		int anchoDisponible;
		int altoDisponible;
		boolean landscape = anchoTabla > ANCHO_P;
		if(landscape) {
			anchoDisponible = ANCHO_L;
			altoDisponible = ALTO_L;
		} else {
			anchoDisponible = ANCHO_P;
			altoDisponible = ALTO_P;
		}

		int anchoImagen = Math.max(anchoTabla, anchoDisponible);
		double escala = (anchoTabla > anchoDisponible) ? (anchoDisponible / (double)anchoTabla) : 1;
		JTableHeader header = tabla.getTableHeader();
		int altoHeader = header.getHeight();
		double altoHeaderEscalado = Math.rint(altoHeader * escala);
		double altoDisponibleFilasPaginas = altoDisponible - altoHeaderEscalado;
		
		boolean copiar = tabla.getRowCount() > 0;
		List<ElementoListado> data = new ArrayList<ElementoListado>();

		BufferedImage imagenTabla = new BufferedImage(anchoTabla, tabla.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2ImagenTabla = getGraphics(imagenTabla);
		tabla.paint(g2ImagenTabla);
		
		/* Despu�s del paint se actualiza la altura de las celdas pero no la altura de la tabla... */
		int alturaTotal = 0;
		for(int i = 0; i < tabla.getRowCount(); i++) {
			alturaTotal += tabla.getRowHeight(i);
		}
		tabla.setSize(tabla.getWidth(), alturaTotal);
		
		/* Este 2do paint es para tomar una imagen de la tabla completamente repintadad (alto multilines no visibles actualizado) */
		imagenTabla = new BufferedImage(anchoTabla, alturaTotal, BufferedImage.TYPE_INT_ARGB);
		g2ImagenTabla = getGraphics(imagenTabla);
		tabla.getHeight();
		tabla.paint(g2ImagenTabla);
		
		int indicePagina = 0;
		int filaCorriente = 0;
		int altoAnterior = 0;
		while(copiar) {
			//Calcular alto (el alto de la imagen a incluir en la hoja)
			int alto = 0;
			while (filaCorriente < tabla.getRowCount()) {
				if ( (alto + tabla.getRowHeight(filaCorriente)) * escala < altoDisponibleFilasPaginas) {
					alto += tabla.getRowHeight(filaCorriente);
					filaCorriente++;
				} else {
					//Por si la fila corriente es mas ancha que el espacio reservado para las filas
					if(alto == 0) {
						alto += tabla.getRowHeight(filaCorriente);
						filaCorriente++;						
					}
					break;
				}
			}
			if(filaCorriente == tabla.getRowCount()) {
				copiar = false;
			} 
			if(alto > 0 && anchoTabla >  0) {
				BufferedImage subimagenTabla = imagenTabla.getSubimage(0, altoAnterior, anchoTabla, alto);
				BufferedImage imagenHoja = new BufferedImage(anchoImagen + 1, (int)(altoDisponible/escala), BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g2ImagenHoja = getGraphics(imagenHoja);
				//Poner la imagen en blanco
				g2ImagenHoja.setColor(Color.WHITE);
				g2ImagenHoja.fillRect(0, 0, anchoImagen + 1, (int)(altoDisponible/escala));
				//Dibujar el header
				g2ImagenHoja.translate(2, 0);
				header.paint(g2ImagenHoja);
				//?
				g2ImagenHoja.setColor(Color.WHITE);
				g2ImagenHoja.fillRect(header.getX() + anchoTabla, 0, anchoDisponible - anchoTabla, altoHeader);
				//Filas
				g2ImagenHoja.translate(-2, 0);
				g2ImagenHoja.drawImage(subimagenTabla, 2, altoHeader, Color.WHITE, null);
				//?
				g2ImagenHoja.setColor(tabla.getGridColor());
				g2ImagenHoja.drawLine(anchoTabla, 0, anchoTabla, alto + altoHeader);
				g2ImagenHoja.setColor(tabla.getGridColor());
				g2ImagenHoja.drawLine(1, 0, 1, alto + altoHeader);
				g2ImagenHoja.setColor(tabla.getGridColor());
				g2ImagenHoja.drawLine(0, alto + altoHeader - 1, anchoTabla, alto + altoHeader - 1);
				g2ImagenHoja.scale(escala, escala);
				data.add(new ElementoListado(imagenHoja));
				indicePagina++;
			}
			altoAnterior += alto;
		}
		return data;
	}

	private Graphics2D getGraphics(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return g;
	}

	private JasperWrapperProperties getProperties(String titulo, String subtitulo,String filtros, List<ElementoListado> data, boolean landscape) {
		JasperWrapperProperties properties = new JasperWrapperProperties();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("Logo", "ar/pegasus/framework/imagenes/AGEAOP.GIF");
		parameters.put("Usuario", BossUsuarioVerificado.getUsuarioVerificado().getNombre());
		parameters.put("FechaImpresion", new SimpleDateFormat(DateUtil.SHORT_DATE_WITH_HOUR).format(DateUtil.getAhora()));
		parameters.put("Titulo", titulo);
		parameters.put("SubTitulo", subtitulo);
		parameters.put("Filtros", filtros);
		parameters.put("Sistema", getNombreAplicacion());
		
		properties.setParameters(parameters);
		if(landscape) {
			properties.setXmlReport("ar/pegasus/framework/impresion/listadoTablaHorizontal.jasper");
		} else {
			properties.setXmlReport("ar/pegasus/framework/impresion/listadoTablaVertical.jasper");
		}
		properties.setData(data);
		return properties;
	}
	
	/**
	 * Devuelve el valor de la Previsualizaci�n de Impresi�n.
	 * @return Returns
	 */
	public boolean isVerPreview() {
		boolean verPreview = false;
		if(System.getProperty("verPreviewImpresion") != null)
			verPreview = Boolean.valueOf(System.getProperty("verPreviewImpresion")).booleanValue();
		return verPreview;
	}

	private String getNombreAplicacion() {
		return nombreAplicacion;
	}

	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}
}