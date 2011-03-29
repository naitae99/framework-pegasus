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
	 * que refrescar despu�s de ejecutar la acci�n. 
	 * @param accion, identificador de la acci�n��n a ejecutar
	 * @param tabla, necesaria para las acciones de impresi�n y selecci�n 
	 * @param titulo, necesario para el titulo de los reportes
	 * @param subtitulo, Idem Anterior
	 * @return rv, un booleando indicando si debe refrescar los datos al terminar la acci�n
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
	 * Ejecuta la acci�n para la lista de reservas, devuelve <b>true</b> si hay
	 * que refrescar despu�s de ejecutar la acci�n.
	 * @param accion, identificador de acci�n a ejecutar
	 * @param idModulo, no usado
	 * @param ordenes, Lista de elementos sobre los cuales se ejecuta la acci�n 
	 * @return Un valor booleando indicando si debe refrescar los datos al terminar la acci�n
	 */
//	public boolean procesarAccion(int accion, int idModulo, List<? extends OrdenBasica> ordenes) {
//		switch(accion) {
////			case EnumAcciones.ESTADO_ACTUAL:
////			mostrarEstadoActual(ordenes.get(0));
////			break;
//			case EnumAcciones.SALDO_ACTUAL:
////			mostrarEstadoActualSaldo(ordenes.get(0));
//			break;
////			case EnumAcciones.VER_AVISO:
////			verAviso(ordenes);
////			break;
//		}
//		return false;
//	}

	/**
	 * Muestra el estado actual de una Orden de acuerdo a la informaci�n
	 * de SAP.
	 * @param orden
	 */
//	public boolean mostrarEstadoActual(OrdenBasica orden) {
//		if(orden.getTipoPublicidad() == EnumTipoPublicidad.PUBLICIDAD) { //TODO: sacar este if para clasificados
//			AgenciaSAP estadoAgencia;
//			try {
//				// estadoAgencia = BossPublicidadCredito.traerAgenciaConInfoRetencion(orden.getIdPublicidad());
//				estadoAgencia = BeanFactoryFWSGPRemote.getInstance().getBean(AgenciaSapFacadeRemote.class).traerAgenciaConInfoRetencion(orden.getIdPublicidad());
//				String estado = "";
//				if(estadoAgencia.isInhabilitado()) {
//					estado = "Inhabilitado";
//				} else {
//					estado = "Habilitado";
//				}
//				CLJOptionPane.showInformationMessage(AbstractMainTemplate.getFrameInstance() , "El estado actual de la agencia es: " + estado, "Estado actual");
//				return true;
//			} catch(PException e) {
//				BossError.gestionarError("No se puede obtener el estado de la agencia.", e);
//			}
//		}
//		return false;
//	}

	/**
	 * Muestra el saldo de la agencia.
	 * @param orden la orden con la agencia de la que se quiere mostrar el saldo.
	 */
//	public void mostrarEstadoActualSaldo(OrdenBasica orden) {
//		if(orden.getTipoPublicidad() == EnumTipoPublicidad.PUBLICIDAD) { //TODO: sacar este if para clasificados
//			try {
//				// SaldoSAP saldoAgencia = BossPublicidadCredito.traerAgenciaConSaldoDeCredito(orden.getIdPublicidad());
//				SaldoSAP saldoAgencia = BeanFactoryFWSGPRemote.getInstance().getBean(SaldoAgenciaSapFacadeRemote.class).traerAgenciaConSaldoDeCredito(orden.getIdPublicidad());
//				JDialog dialog = new SaldoSAPDialog(orden, saldoAgencia);
//				dialog.setVisible(true);
//			} catch(PException e) {
//				BossError.gestionarError("No se puede obtener el saldo", e);
//			}
//		}
//	}


	/** Devuelve una lista de acciones posible segun una orden basica 
	 * @throws PException */
//	protected abstract List<?> getAcciones(OrdenBasica orden) throws PException;

	/**
	 * Acci�n que exporta una CLJTable a un archivo Excel.
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
	 * Acci�n que selecciona toda las filas de una tabla.
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
//		Hashtable<Integer,Integer> accionesPorMedio = new Hashtable<Integer, Integer>();
		Usuario usuario = BossUsuarioVerificado.getUsuarioVerificado();
		if(usuario != null) {
			Rol rol = usuario.getRol();
			if(rol != null) {				
//				List<MedioConsulta> medios ;
//				BossMedioProductoConsultaRemote bossMedioProductoConsulta = BeanFactoryFWSGPRemote.getInstance().getBean(BossMedioProductoConsultaRemote.class);
//				medios = bossMedioProductoConsulta.getMedioConsultaList() ;
//				int tipoAccion = EnumTipoAccion.OPERACION;
//				for(MedioConsulta medio : medios) {
//					accionesPorMedio.put(medio.getIdPublicidad(), tipoAccion);
//				}
//				EnumAcciones.setTipoAccionesPorMedio(accionesPorMedio);
			}
		}
	}

	/**
	 * Presenta un dialogo avisando que la accion no es editable y pide
	 * confirmaci�n del usuario.
	 * @return true si el usuario confirma la acci�n.
	 */
	protected static boolean confirmarAccion() {
		if(PJOptionPane.showQuestionMessage(null, "El aviso ya ha sido tomado por diagramaci�n.\nDesea realizar la acci�n de todos modos?",
				"Confirmaci�n de modificaci�n") == PJOptionPane.YES_OPTION) {
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