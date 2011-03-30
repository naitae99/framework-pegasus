package ar.pegasus.framework.util;

import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFRegionUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.boss.AppConstantesBase;
import ar.pegasus.framework.componentes.IndicadorProgreso;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PFileSelector;
import ar.pegasus.framework.componentes.PJList;
import ar.pegasus.framework.componentes.PProgressDialog;
import ar.pegasus.framework.util.filtros.FiltroArchivosExcel;

/**
 * Esta clase se encarga de exportar los datos desde una lista a un archivo Excel
 * 
 * @author oarias
 *
 * @param <T>
 */
public abstract class ExcelExporter<T> {

	private String rutaImagen;
	
	public abstract String getTitulo();

	public abstract String getSubtitulo();

	public abstract List<String> getCabecerasColumnas();
	
	public abstract String getValorCelda(T elemento, int nroCelda);
	
	public void exportar(PJList lista) {
		exportar(lista,RutaImagenes.DEFAULT_LOGO);
	}
	
	public void exportar(PJList lista, String rutaImagen){
		setRutaImagen(rutaImagen);
		File directorioCorriente = PFileSelector.getLastSelectedFile();
		if (directorioCorriente != null) {
			if (directorioCorriente.isFile())
				directorioCorriente = directorioCorriente.getParentFile();
			String nombreSugerido = null;
			try {
				nombreSugerido = directorioCorriente.getCanonicalPath()	+ File.separator + getTitulo();
			} catch (IOException e) {
				BossError.gestionarError(new PException(e.getMessage()));
			}
			File archivoSugerido = new File(nombreSugerido.concat(EtiquetasBotones.EXTENSION_EXCEL));
			PFileSelector.setLastSelectedFile(archivoSugerido);
		}
		File archivoIngresado = PFileSelector.obtenerArchivo(PFileSelector.SAVE, PFileSelector.FILES_ONLY,new FiltroArchivosExcel(), null);
		if (archivoIngresado != null) {
			if (!archivoIngresado.getAbsolutePath().toLowerCase().endsWith(EtiquetasBotones.EXTENSION_EXCEL)) {
				archivoIngresado = new File(archivoIngresado.getAbsolutePath().concat(EtiquetasBotones.EXTENSION_EXCEL));
			}
			Frame owner = (Frame) SwingUtilities.windowForComponent(lista);
			PProgressDialog dialog = PProgressDialog.createDialog(owner, -1,getRutaImagen(), true);
			dialog.setMessage("Exportando a Excel...");
			ThExportacionExcel thExportacion = new ThExportacionExcel(dialog,lista, getTitulo().replace('/', '-'), getSubtitulo().replace('/', '-'), archivoIngresado.getAbsolutePath());
			thExportacion.start();
			dialog.setVisible(true);
		}
	}

	public void exportarAExcelLista(PJList lista, String titulo,String subtitulo, String archivoIngresado,IndicadorProgreso indicador) {
		HSSFWorkbook libro = new HSSFWorkbook();
		short contColumnas = 0;
		int cantColumnasLista = getCabecerasColumnas().size();
		short[] anchosColumnas;
		int cantFilas = lista.getItemCount();
		HSSFCell celda;
		HSSFRow fila;
		HSSFSheet hoja = libro.createSheet(AppConstantesBase.NOMBRE_HOJA_DEFAULT);
		hoja.setMargin(HSSFSheet.LeftMargin, 0.5d);
		hoja.setMargin(HSSFSheet.TopMargin, 0.5d);
		hoja.setMargin(HSSFSheet.RightMargin, 0.5d);
		hoja.setMargin(HSSFSheet.BottomMargin, 0.5d);
		HSSFPrintSetup configImpresion = hoja.getPrintSetup();
		configImpresion.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		configImpresion.setLandscape(true);
		hoja.setAutobreaks(true);
		configImpresion.setFitWidth((short) 1);
		configImpresion.setFitHeight((short) 0);
		Font fuenteHeader = lista.getFont();
		HSSFFont fuenteHeaderExcel = libro.createFont();
		fuenteHeaderExcel.setFontName(fuenteHeader.getFontName());
		fuenteHeaderExcel.setBoldweight(fuenteHeader.isBold() ? HSSFFont.BOLDWEIGHT_BOLD: HSSFFont.BOLDWEIGHT_NORMAL);
		fuenteHeaderExcel.setItalic(fuenteHeader.isItalic());
		fuenteHeaderExcel.setColor(new HSSFColor.WHITE().getIndex());
		
		Font fuenteTabla = lista.getFont();
		HSSFFont fuenteTablaExcel = libro.createFont();
		fuenteTablaExcel.setFontName(fuenteTabla.getFontName());
		fuenteTablaExcel.setBoldweight(fuenteTabla.isBold() ? HSSFFont.BOLDWEIGHT_BOLD: HSSFFont.BOLDWEIGHT_NORMAL);
		fuenteTablaExcel.setItalic(fuenteTabla.isItalic());
		HSSFFont fuenteTitulo = libro.createFont();
		fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fuenteTitulo.setColor(new HSSFColor.WHITE().getIndex());
		HSSFCellStyle cellStyleTitulo = libro.createCellStyle();
		cellStyleTitulo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleTitulo.setFillForegroundColor(new HSSFColor.PLUM().getIndex());
		cellStyleTitulo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleTitulo.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleTitulo.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleTitulo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleTitulo.setFont(fuenteTitulo);
		cellStyleTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		fila = hoja.createRow(0);
		celda = fila.createCell((short) 0);
		celda.setCellStyle(cellStyleTitulo);
		celda.setCellValue(titulo);
		
		HSSFFont fuenteSubtitulo = libro.createFont();
		fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyleSubtitulo = libro.createCellStyle();
		cellStyleSubtitulo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleSubtitulo.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleSubtitulo.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleSubtitulo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleSubtitulo.setFont(fuenteSubtitulo);
		cellStyleSubtitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		fila = hoja.createRow(1);
		celda = fila.createCell((short) 0);
		celda.setCellStyle(cellStyleSubtitulo);
		celda.setCellValue(subtitulo);
		fila = hoja.createRow(3);

		List<String> columnas = getCabecerasColumnas();
		anchosColumnas = new short[columnas.size()];

		for (String columna : columnas) {

			String nombreColumna = columna.toString();
			HSSFCellStyle cellStyleHeader = libro.createCellStyle();
			cellStyleHeader.setFont(fuenteHeaderExcel);
			cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleHeader.setFillForegroundColor(new HSSFColor.PLUM().getIndex());
			cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			celda = fila.createCell(contColumnas);
			celda.setCellStyle(cellStyleHeader);
			celda.setCellValue(nombreColumna);
			anchosColumnas[contColumnas] = (short) nombreColumna.length();
			contColumnas++;
		}
		if (indicador != null) {
			indicador.setReady(0, cantFilas * columnas.size());
		}
		for (int i = 0; i < cantFilas; i++) {
			contColumnas = 1;
			fila = hoja.createRow(i + getPosicionFilaInicial());
			@SuppressWarnings("unchecked")
			T elemento = ((T) lista.getItem(i));
			if (elemento == null)
				continue;
			for (short j = 1; j <= cantColumnasLista; j++) {
				celda = fila.createCell((short) (contColumnas - 1));
				celda.setCellValue(getValorCelda(elemento,j));

				contColumnas++;

				if (indicador != null) {
					indicador.setValorActual(indicador.getValorActual() + 1);
				}
			}
		}
		if (indicador != null) {
			indicador.setFinished(true);
		}
		int anchoColumnas = 0;
		boolean calcularColsTitulos = true;
		for (short i = 0; i < cantColumnasLista; i++) {
			hoja.setColumnWidth(i,
					(short) ((anchosColumnas[i] * 256) + (256 * 2)));
			anchoColumnas += anchosColumnas[i];
			if (calcularColsTitulos && anchoColumnas >= 50) {
				int dif = anchoColumnas - 50;
				int difAnterior = anchoColumnas - anchosColumnas[i] - 50;
				short colFin;
				if (i > 0 && difAnterior < dif) {
					colFin = (short) (i - 1);
				} else {
					colFin = i;
				}
				Region regionTitulo = new Region((short) 0, (short) 0,
						(short) 0, colFin);
				Region regionSubtitulo = new Region((short) 1, (short) 0,
						(short) 1, colFin);
				hoja.addMergedRegion(regionTitulo);
				hoja.addMergedRegion(regionSubtitulo);
				try {
					HSSFRegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,regionTitulo, hoja, libro);
					HSSFRegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN,regionTitulo, hoja, libro);
					HSSFRegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN,	regionTitulo, hoja, libro);
					HSSFRegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN,regionTitulo, hoja, libro);
					HSSFRegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,regionSubtitulo, hoja, libro);
					HSSFRegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN,regionSubtitulo, hoja, libro);
					HSSFRegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN,	regionSubtitulo, hoja, libro);
					HSSFRegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN,regionSubtitulo, hoja, libro);
				} catch (NestableException e) {
					e.printStackTrace();
				}
				calcularColsTitulos = false;
			}
		}

		libro.setRepeatingRowsAndColumns(0, -1, -1, 0, 3);
		libro.setPrintArea(0, 0, cantColumnasLista - 1, 0, cantFilas + 4);
		MiscUtil.sleep(1000);

		guardarLibro(libro, archivoIngresado);
	}

	
	private void guardarLibro(HSSFWorkbook libro, String ruta) {
		try {
			FileOutputStream fileOut = new FileOutputStream(ruta);
			libro.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Determina la primera fila desde la cual se empiezan a mostrar los datos en el archivo
	 * excel.
	 * @return nro de fila a partir de la cual cargar los datos.
	 */
	protected int getPosicionFilaInicial() {
		return 4;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	/**
	 * 
	 * @author oarias
	 * 
	 */
	class ThExportacionExcel extends Thread {
		PProgressDialog dialog;
		PJList lista;
		String subtitulo;
		String archivoIngresado;
		String titulo;

		public ThExportacionExcel(PProgressDialog dialog, PJList lista,
				String titulo, String subtitulo, String archivoIngresado) {
			this.dialog = dialog;
			this.lista = lista;
			this.subtitulo = subtitulo;
			this.archivoIngresado = archivoIngresado;
			this.titulo = titulo;
		}

		public void run() {
			exportarAExcelLista(lista, titulo, subtitulo, archivoIngresado,
					null);
			dialog.dispose();
		}
	}

}
