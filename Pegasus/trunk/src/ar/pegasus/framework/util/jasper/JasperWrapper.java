package ar.pegasus.framework.util.jasper;

import java.awt.Dimension;
import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.io.InvalidClassException;

import javax.print.PrintService;
import javax.swing.JDialog;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.templates.main.AbstractMainTemplate;
import ar.pegasus.framework.util.AnchorTrick;
import ar.pegasus.framework.util.GuiUtil;

/**
 * Clase �til para el sencillo manejo del paquete <b>Jasper Reports</b> (ver
 * http://jasperreports.sourceforge.net/).
 * 
 * @author oarias
 */
public class JasperWrapper {

	private static boolean seImprimio = false;

	/**
	 * Genera un reporte a partir de un reporte Jasper previamente compilado.
	 * 
	 * @param properties
	 *            Las propiedades de configuraci�n de Jasper.
	 * @return Un objeto JasperPrint o null en caso de error.
	 * @throws JRException
	 */
	public static JasperPrint generarReporte(JasperWrapperProperties properties) throws JRException {
		ClassLoader cl = new AnchorTrick().getClass().getClassLoader();
		InputStream xmlCompileFile = cl.getResourceAsStream(properties.getXmlReport());
		JasperPrint jasperPrint = JasperFillManager.fillReport(xmlCompileFile, properties.getParameters(), new JRBeanCollectionDataSource(properties.getData()));
		return jasperPrint;
	}
	public static boolean impresionComprobante(JasperWrapperProperties properties, boolean preview, int idImpresora, String titulo, Dimension dimension) {
		try {
			JasperPrint jasperPrint = generarReporte(properties);
			if (preview) {
				JRPrintServiceExporter exporter = setearComprobante(jasperPrint, idImpresora);
				JasperPanel jrv = new JasperPanel(exporter, jasperPrint);
				if (exporter == null) {
					jrv.printButton.setEnabled(false);
				}
				JDialog viewer = new JDialog();
				viewer.setTitle(titulo);
				viewer.getContentPane().add(jrv);
				viewer.setSize(dimension);
				viewer.setModal(true);
				GuiUtil.centrar(viewer);
				viewer.setVisible(true);
				return seImprimio;
			} else {
				imprimirComprobante(jasperPrint, idImpresora);
				return true;
			}
		}
		catch (PException e) {
			if (e.getCause() != null && e.getCause().getMessage().equals("Printer is not accepting job.")) {
				BossError.gestionarError(new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no est� accesible", e, new String[] {"Verifique el estado de la impresora"}));
			} else {
				BossError.gestionarError(new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no est� accesible", e, null));				
			}
			return false;
		}
		catch (Exception e) {
			BossError.gestionarError(new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no est� accesible", e, null));
			return false;
		}
	}
	public static boolean imprimirReporte(JasperWrapperProperties properties, boolean preview, String titulo, Dimension dimension) {
		try {
			JasperPrint jasperPrint = generarReporte(properties);
			if (preview) {
				PJRViewer jrv = new PJRViewer(jasperPrint);
				JDialog viewer = new JDialog(AbstractMainTemplate.getFrameInstance());
				viewer.setTitle(titulo);
				viewer.getContentPane().add(jrv);
				viewer.setSize(dimension);
				viewer.setModal(true);
				GuiUtil.centrar(viewer);
				viewer.setVisible(true);
				return seImprimio;
			} else {
				imprimirComprobante(jasperPrint);
				return true;
			}
		}
		catch (PException e) {
			BossError.gestionarError(e);
			return false;
		}
		catch (JRException e) {
			int tipoDeError = BossError.ERR_INDETERMINADO ;
			if (e.getCause() != null && e.getCause() instanceof InvalidClassException) {
				tipoDeError = BossError.ERR_APLICACION ;
			}
			BossError.gestionarError(new PException(tipoDeError, "No se pudo imprimir el comprobante", "Error al generar el Reporte", e, new String[] {}));
			return false;
		}
	}
	
	public static void imprimirComprobante(JasperPrint jasperPrint) throws PException {
		try {
			JasperPrintManager.printReport(jasperPrint, true);
		}
		catch (JRException je) {
			if (je.getCause() != null && je.getCause().getMessage().equals("Printer is not accepting job.")) {
				throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no esta accesible", je.getCause(), new String[] {"Verifique el estado de la impresora"});
			} else {
				throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no esta accesible", je.getCause(), null);
			}
		}
	}
	
	public static boolean imprimirReporte(JasperWrapperProperties properties, boolean preview, String titulo, Dimension dimension, boolean confirmacion) {
		try {
			JasperPrint jasperPrint = generarReporte(properties);
			if (preview) {
				PJRViewer jrv = new PJRViewer(jasperPrint);
				JDialog viewer = new JDialog(AbstractMainTemplate.getFrameInstance());
				viewer.setTitle(titulo);
				viewer.getContentPane().add(jrv);
				viewer.setSize(dimension);
				viewer.setModal(true);
				GuiUtil.centrar(viewer);
				viewer.setVisible(true);
				return seImprimio;
			} else {
				imprimirComprobante(jasperPrint, confirmacion);
				return true;
			}
		}
		catch (PException e) {
			BossError.gestionarError(e);
			return false;
		}
		catch (JRException e) {
			int tipoDeError = BossError.ERR_INDETERMINADO ;
			if (e.getCause() != null && e.getCause() instanceof InvalidClassException) {
				tipoDeError = BossError.ERR_APLICACION ;
			}
			BossError.gestionarError(new PException(tipoDeError, "No se pudo imprimir el comprobante", "Error al generar el Reporte", e, new String[] {}));
			return false;
		}
	}
	public static void imprimirComprobante(JasperPrint jasperPrint, boolean confirmacion) throws PException {
		try {
			JasperPrintManager.printReport(jasperPrint, confirmacion);
		}
		catch (JRException je) {
			if (je.getCause() != null && je.getCause().getMessage().equals("Printer is not accepting job.")) {
				throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no esta accesible", je.getCause(), new String[] {"Verifique el estado de la impresora"});
			} else {
				throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no esta accesible", je.getCause(), null);
			}
		}
	}
	public static void imprimirComprobante(JasperPrint jasperPrint, int idImpresora) throws PException {
		JRPrintServiceExporter exporter = setearComprobante(jasperPrint, idImpresora);
		if (exporter == null)
			throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "No esta seteada la impresora", null, new String[] { "Setear la impresora" });
		try {
			exporter.exportReport();
		}
		catch (JRException je) {
			throw new PException(BossError.ERR_CONEXION, "No se puede imprimir", "La Impresora no responde o no esta accesible", je.getCause(), null);
		}
	}
	public static JRPrintServiceExporter setearComprobante(JasperPrint jasperPrint, int idImpresoraComprobante) {
		if (idImpresoraComprobante == -1) return null;
		PrintService[] impresoras = PrinterJob.lookupPrintServices();
		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, impresoras[idImpresoraComprobante].getAttributes());
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		return exporter;
	}
	public static void seImprimio(boolean imprimio) {
		seImprimio = imprimio;
	}
}
