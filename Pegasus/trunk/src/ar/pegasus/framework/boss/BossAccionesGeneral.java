package ar.pegasus.framework.boss;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.SwingUtilities;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PFileSelector;
import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.componentes.PJTable;
import ar.pegasus.framework.componentes.PProgressDialog;
import ar.pegasus.framework.entidades.accion.GrupoAcciones;
import ar.pegasus.framework.entidades.core.Rol;
import ar.pegasus.framework.entidades.core.Usuario;
import ar.pegasus.framework.entidades.enumeradores.EnumAcciones;
import ar.pegasus.framework.util.MiscUtil;
import ar.pegasus.framework.util.filtros.FiltroArchivosExcel;

/**
 * Clase que permite la ejecución de acciones generales y que son independiente
 * de la Aplicacion en donde están incluidas. 
 * @author oarias
 */
public abstract class BossAccionesGeneral {


	private static Hashtable<String, GrupoAcciones> gruposAcciones = new Hashtable<String, GrupoAcciones>();

	/**
	 * Ejecuta acciones generales, devuelve <b>true</b> si hay
	 * que refrescar después de ejecutar la acción. 
	 * @param accion, identificador de la acción a ejecutar
	 * @param tabla, necesaria para las acciones de impresión y selección 
	 * @param titulo, necesario para el titulo de los reportes
	 * @param subtitulo, Idem Anterior
	 * @return rv, un booleando indicando si debe refrescar los datos al terminar la acción
	 */
	public static boolean procesarAccionGeneral(int accion, PJTable tabla, String titulo, String subtitulo, String filtros) {
		boolean rv = false;
		switch(accion) {
			case EnumAcciones.EXPORTAR_A_EXCEL:
			exportarAExcel(tabla, titulo, subtitulo);
			break;
			case EnumAcciones.SELECCIONAR_TODO:
			seleccionarTodo(tabla);
			break;
			case EnumAcciones.IMPRIMIR_LISTADO:
			imprimirListado(tabla, titulo, subtitulo, filtros);
			break;
		}
		return rv;
	}

	/**
	 * Acción que exporta una PJTable a un archivo Excel.
	 * @param tabla La tabla a exportar.
	 * @param titulo El nombre sugerido para el archivo.
	 * @param subtitulo El nombre de la hoja. Si su longitud es mayor a 31
	 *            caracteres lo trunca. No puede contener ninguno de los
	 *            siguientes caracteres: /\*?[]. Si no se especifica se le
	 *            asigna un nombre predeterminado.
	 */
	private static void exportarAExcel(PJTable tabla, String titulo, String subtitulo) {
		File directorioCorriente = PFileSelector.getLastSelectedFile();
		if(directorioCorriente != null) {
			if(directorioCorriente.isFile())
				directorioCorriente = directorioCorriente.getParentFile();
			String nombreSugerido = "";
			try {
				nombreSugerido = directorioCorriente.getCanonicalPath() + File.separator + titulo;
			} catch(IOException e) {
				e.printStackTrace();
			}
			File archivoSugerido = new File(nombreSugerido.concat(AppConstantesBase.EXTENSION_EXCEL));
			PFileSelector.setLastSelectedFile(archivoSugerido);
		}
		File archivoIngresado = PFileSelector.obtenerArchivo(PFileSelector.SAVE, PFileSelector.FILES_ONLY, new FiltroArchivosExcel(), null);
		if(archivoIngresado != null) {
			if(!archivoIngresado.getAbsolutePath().toLowerCase().endsWith(AppConstantesBase.EXTENSION_EXCEL)) {
				archivoIngresado = new File(archivoIngresado.getAbsolutePath().concat(AppConstantesBase.EXTENSION_EXCEL));
			}
			Frame owner = (Frame)SwingUtilities.windowForComponent(tabla);
			PProgressDialog dialog = PProgressDialog.createDialog(owner, -1, "ar/pegasus/framework/imagenes/SGP.gif", true);
			dialog.setMessage("Exportando a Excel...");
			ThExportacionExcel thExportacion = new ThExportacionExcel(dialog, tabla, titulo.replace('/', '-'), subtitulo.replace('/', '-'), archivoIngresado.getAbsolutePath());
			thExportacion.start();
			dialog.setVisible(true);
		}
	}

	static class ThExportacionExcel extends Thread {
		PProgressDialog dialog;
		PJTable tabla;
		String subtitulo;
		String archivoIngresado;
		String titulo;

		public ThExportacionExcel(PProgressDialog dialog, PJTable tabla, String titulo, String subtitulo, String archivoIngresado) {
			this.dialog = dialog;
			this.tabla = tabla;
			this.subtitulo = subtitulo;
			this.archivoIngresado = archivoIngresado;
			this.titulo = titulo;
		}

		@Override
		public void run() {
			MiscUtil.exportarAExcel(tabla, titulo, subtitulo," ",archivoIngresado, dialog.getIndicadorProgreso(), System.getProperty("intercalarColoresFilas") != null && System.getProperty("intercalarColoresFilas").equals(String.valueOf(true)));
			dialog.dispose();
		}
	}

	/**
	 * Acción que selecciona toda las filas de una tabla.
	 * @param tabla
	 */
	private static void seleccionarTodo(PJTable tabla) {
		if(tabla.getRowCount() > 0) {
			if(tabla.getSelectedRowCount() == tabla.getRowCount()) {
				tabla.getSelectionModel().clearSelection();
			} else {
				tabla.setRowSelectionInterval(0, tabla.getRowCount() - 1);
			}
		}
	}

	/**
	 * Accion que imprime la tabla.
	 * @param tabla
	 * @param titulo
	 * @param subtitulo
	 */
	private static void imprimirListado(PJTable tabla, String titulo, String subtitulo, String filtros) {
		BossImpresion.getInstance().imprimirListado(tabla, titulo, subtitulo, filtros, true);
	}

	/** Configura las acciones 
	 * @throws PException */
	public static void configurarAcciones() throws PException {
		Usuario usuario = BossUsuarioVerificado.getUsuarioVerificado();
		if(usuario != null) {
			Rol rol = usuario.getRol();
			if(rol != null) {				
			}
		}
	}

	/**
	 * Presenta un dialogo avisando que la accion no es editable y pide
	 * confirmación del usuario.
	 * @return true si el usuario confirma la acción.
	 */
	protected static boolean confirmarAccion() {
		if(PJOptionPane.showQuestionMessage(null, "El aviso ya ha sido tomado por diagramación.\nDesea realizar la acción de todos modos?",
				"Confirmación de modificación") == PJOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

    public static GrupoAcciones getGrupoAcciones(int orden, String nombre) {
    	String clave = orden + ", " + nombre;
    	GrupoAcciones grupoAcciones = gruposAcciones.get(clave);
    	if(grupoAcciones == null) {
    		grupoAcciones = new GrupoAcciones(orden, nombre);
    		gruposAcciones.put(clave, grupoAcciones);
    	}
    	return grupoAcciones;
    }

}