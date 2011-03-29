package ar.pegasus.framework.entidades.enumeradores;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ar.pegasus.framework.entidades.Accion;
import ar.pegasus.framework.entidades.accion.AccionDefault;

/**
 * Enumerador de acciones.
 * 
 * @author oarias
 */
public class EnumAcciones {

	private static Hashtable<Integer, Integer> tipoAccionesPorMedio;
//	public static final int RECIBIR = 1;
//	public static final int IMPRIMIR_RECIBO = 2;
//	public static final int IMPRIMIR_PAPELETA = 3;
//	public static final int INGRESAR_ORIGINAL = 4;
//	public static final int SUBIR_MATERIAL_PROCESADO = 5;
//	public static final int BAJAR_MATERIAL = 6;
//	public static final int BAJAR_ORIGINAL = 7;
//	public static final int BORRAR_ORIGINAL = 8;
//	public static final int BORRAR_MATERIAL_PROCESADO = 9;
//	public static final int CAMBIAR_DESTINO = 10;
//	public static final int BUSCAR = 11;
//	public static final int PROCESAR_REPETICIONES = 12;
//	public static final int LIBERAR_MATERIAL = 13;
//	public static final int CAMBIAR_TIPO_CONTENIDO = 14;
//	public static final int INGRESAR_INCIDENTE = 15;
//	public static final int INGRESAR_RESOLUCION = 16;
	public static final int VER_AVISO = 17;
	//public static final int ABRIR_PRODUCTO = 18;
//	public static final int CERRAR_PRODUCTO = 19;
	//public static final int ASOCIAR_MATERIAL_CON_AVISO = 20;
//	public static final int DESASOCIAR_MATERIAL_CON_AVISO = 21;
//	public static final int DEVOLVER_MATERIAL = 22;
//	public static final int DESTRUIR_MATERIAL = 23;
	public static final int IMPRIMIR_LISTADO = 24;
//	public static final int INGRESO_DIRECTO = 25;
//	public static final int ASOCIAR_RELLENO_CON_AVISO = 26;
//	public static final int VERIFICAR = 27;
	public static final int AUTORIZAR = 28;
	public static final int CANCELAR_AUTORIZACION = 29;
	public static final int SELECCIONAR_TODO = 30;
//	public static final int ESTADO_ACTUAL = 32;
	public static final int SALDO_ACTUAL = 33;
	public static final int VER_DATOS_ADICIONALES = 34;
	public static final int EXPORTAR_A_EXCEL = 35;
//	public static final int MATERIAL_SUELTO = 36;
//	public static final int PREVIEW = 37;
//	public static final int APROBAR_ORIGINAL = 50;
//	public static final int RECHAZAR_ORIGINAL = 51;
//	public static final int APROBAR_CANJE = 52;
//	public static final int RECHAZAR_CANJE = 53;
//	public static final int CAMBIAR_COLOR_MATERIAL = 55;
//	public static final int CONGELAR_CLIENTE = 56;
//	public static final int DESCONGELAR_CLIENTE = 57;
//	public static final int MODIFICAR_CONGELAMIENTO_CLIENTE = 58;
//	public static final int CONGELAR_RESERVA = 59;
//	public static final int DESCONGELAR_RESERVA = 60;
//	public static final int BAJADA_MASIVA_MATERIALES = 69;
//	public static final int CONTROLAR_MATERIAL_OP = 70;
	// Heredada del Sistema de republicacion y control de avisos notables
	// Copia el .EPS de EPSBAK a EPSBAK/.. (RED1)
//	public static final int REENVIAR_ARCHIVO = 61;

//	public static final int HISTORIAL = 62;

	// Copiar el material terminado de SGP Materiales al Sistema Editorial
//	public static final int COPIAR_MATERIAL_SISTEMA_EDITORIAL = 63;

//	public static final int REQUERIR_APROBACION_ORIGINAL = 64;
//	public static final int CAMBIAR_CANAL_VENTA = 65;

	public static final int CONTROLAR_CANALES_VENTA = 66;
//	public static final int MODIFICAR_CONTROL_CANALES_VENTA = 67;
	public static final int DESACTIVAR_CONTROL_CANALES_VENTA = 68;

	// SIC
	//public static final int ALTA_DE_AVISO = 101;
//	public static final int MODIFICACION_DE_AVISO = 102;
//	public static final int CANCELACION_DE_AVISO = 103;

	//public static final int EXPORTACION_EN_LAYOUT = 104;

//	public static final int CONTROL_DE_ORDENES = 105;
//	public static final int CONTROL_DE_ORDENES_CANCELAR = 106;

//	public static final int INGRESAR_SUGERENCIAS_EJECUTIVOS = 107;
	//public static final int ESTADO_CANJE = 108;

//	public static final int INGRESAR_SUGERENCIA_DIAGRAMADOR = 109;
//	public static final int CANCELAR_SUGERENCIA_DIAGRAMADOR = 110;

//	public static final int INGRESAR_COMENTARIO_ORDEN_MATERIAL = 111;

	public static final int AUTORIZAR_CREDITO_PROHIBIDO_PUBLICAR = 112;
	public static final int CANCELAR_AUTORIZACION_CREDITO_PROHIBIDO_PUBLICAR = 113;
	
	/**
	 * Devuelve una lista con los ids de acciones de la jerarqu�a especificada, y de las jeraraqu�as inferiores a
	 * la misma.
	 * 
	 * @param tipo El tipo de acci�n solicitada.
	 * @return Los ids de acciones de la jerarqu�a especificada, y de las jerarqu�as inferiores a la misma.
	 * @see ar.pegasus.framework.entidades.enumeradores.EnumTipoAccion
	 */
	public static List<Integer> getAccionesPorTipoJerarquia(int tipo) {
		List<Integer> acciones = null;
		switch(tipo) {
		case EnumTipoAccion.OPERACION:
			acciones = getAccionesPorTipo(EnumTipoAccion.OPERACION);
			acciones.addAll(getAccionesPorTipo(EnumTipoAccion.CONSULTA));
			break;

		case EnumTipoAccion.CONSULTA:
			acciones = getAccionesPorTipo(EnumTipoAccion.CONSULTA);
			break;

		case EnumTipoAccion.NO_PERMITIDA:
			acciones = getAccionesPorTipo(EnumTipoAccion.NO_PERMITIDA);
			break;
		}
		return acciones;
	}

	/**
	 * Devuelve la lista de ids de las acciones que son del tipo especificado.
	 * 
	 * @param tipo El tipo de acciones solicitadas.
	 * @return Las ids de las acciones del tipo especificado.
	 * @see ar.pegasus.framework.entidades.enumeradores.EnumTipoAccion
	 */
	public static List<Integer> getAccionesPorTipo(int tipo) {
		List<Integer> acciones = new ArrayList<Integer>();
		switch(tipo) {
		case EnumTipoAccion.OPERACION:
//			acciones.add(RECIBIR);
//			acciones.add(IMPRIMIR_RECIBO);
//			acciones.add(IMPRIMIR_PAPELETA);
//			acciones.add(INGRESAR_ORIGINAL);
//			acciones.add(SUBIR_MATERIAL_PROCESADO);
//			acciones.add(BAJAR_ORIGINAL);
//			acciones.add(CAMBIAR_DESTINO);
//			acciones.add(PROCESAR_REPETICIONES);
//			acciones.add(CAMBIAR_TIPO_CONTENIDO);
//			acciones.add(INGRESAR_INCIDENTE);
//			acciones.add(INGRESAR_RESOLUCION);
//			acciones.add(ASOCIAR_MATERIAL_CON_AVISO);
//			acciones.add(INGRESO_DIRECTO);
//			acciones.add(ASOCIAR_RELLENO_CON_AVISO);
			acciones.add(AUTORIZAR);
			acciones.add(CANCELAR_AUTORIZACION);
			acciones.add(AUTORIZAR_CREDITO_PROHIBIDO_PUBLICAR);
			acciones.add(CANCELAR_AUTORIZACION_CREDITO_PROHIBIDO_PUBLICAR);
//			acciones.add(APROBAR_ORIGINAL);
//			acciones.add(RECHAZAR_ORIGINAL);
//			acciones.add(APROBAR_CANJE);
//			acciones.add(ESTADO_CANJE);
//			acciones.add(RECHAZAR_CANJE);
//			acciones.add(ALTA_DE_AVISO);
//			acciones.add(MODIFICACION_DE_AVISO);
//			acciones.add(CANCELACION_DE_AVISO);
			acciones.add(EXPORTAR_A_EXCEL);
//			acciones.add(CAMBIAR_COLOR_MATERIAL);
//			acciones.add(CONGELAR_CLIENTE);
//			acciones.add(CONGELAR_RESERVA);
//			acciones.add(DESCONGELAR_CLIENTE);
//			acciones.add(DESCONGELAR_RESERVA);
			//acciones.add(MODIFICAR_CONGELAMIENTO_CLIENTE);
//			acciones.add(REENVIAR_ARCHIVO);
//			acciones.add(COPIAR_MATERIAL_SISTEMA_EDITORIAL);
//			acciones.add(CAMBIAR_CANAL_VENTA);
//			acciones.add(MATERIAL_SUELTO);
//			acciones.add(EXPORTACION_EN_LAYOUT);
//			acciones.add(BAJADA_MASIVA_MATERIALES);
//			acciones.add(INGRESAR_COMENTARIO_ORDEN_MATERIAL);
//			acciones.add(CONTROLAR_MATERIAL_OP);
			break;

//		case EnumTipoAccion.CONSULTA:
////			acciones.add(BAJAR_MATERIAL);
//			acciones.add(VER_AVISO);
//			acciones.add(IMPRIMIR_LISTADO);
////			acciones.add(VERIFICAR);
//			acciones.add(SELECCIONAR_TODO);
////			acciones.add(ESTADO_ACTUAL);
//			acciones.add(SALDO_ACTUAL);
//			acciones.add(VER_DATOS_ADICIONALES);
////			acciones.add(PREVIEW);
//			break;

		case EnumTipoAccion.NO_PERMITIDA:
			break;
		}
		return acciones;
	}

	/**
	 * Devuelve una lista con todas las acciones disponibles.
	 * 
	 * @return Las acciones disponibles.
	 */
	public static List<Accion> getListaAcciones() {
		List<Accion> acciones = new ArrayList<Accion>();
//		acciones.add(createAccion(RECIBIR));
//		acciones.add(createAccion(IMPRIMIR_RECIBO));
//		acciones.add(createAccion(IMPRIMIR_PAPELETA));
//		acciones.add(createAccion(INGRESAR_ORIGINAL));
//		acciones.add(createAccion(SUBIR_MATERIAL_PROCESADO));
//		acciones.add(createAccion(BAJAR_MATERIAL));
//		acciones.add(createAccion(BAJAR_ORIGINAL));
//		acciones.add(createAccion(BORRAR_ORIGINAL));
//		acciones.add(createAccion(BORRAR_MATERIAL_PROCESADO));
//		acciones.add(createAccion(CAMBIAR_DESTINO));
//		acciones.add(createAccion(BUSCAR));
//		acciones.add(createAccion(PROCESAR_REPETICIONES));
//		acciones.add(createAccion(LIBERAR_MATERIAL));
//		acciones.add(createAccion(CAMBIAR_TIPO_CONTENIDO));
//		acciones.add(createAccion(INGRESAR_INCIDENTE));
		//acciones.add(createAccion(INGRESAR_RESOLUCION));
		acciones.add(createAccion(VER_AVISO));
//		acciones.add(createAccion(ABRIR_PRODUCTO));
//		acciones.add(createAccion(CERRAR_PRODUCTO));
//		acciones.add(createAccion(ASOCIAR_MATERIAL_CON_AVISO));
//		acciones.add(createAccion(DESASOCIAR_MATERIAL_CON_AVISO));
//		acciones.add(createAccion(DEVOLVER_MATERIAL));
//		acciones.add(createAccion(DESTRUIR_MATERIAL));
		acciones.add(createAccion(IMPRIMIR_LISTADO));
//		acciones.add(createAccion(INGRESO_DIRECTO));
//		acciones.add(createAccion(ASOCIAR_RELLENO_CON_AVISO));
//		acciones.add(createAccion(VERIFICAR));
		acciones.add(createAccion(AUTORIZAR));
		acciones.add(createAccion(CANCELAR_AUTORIZACION));
		acciones.add(createAccion(AUTORIZAR_CREDITO_PROHIBIDO_PUBLICAR));
		acciones.add(createAccion(CANCELAR_AUTORIZACION_CREDITO_PROHIBIDO_PUBLICAR));
		acciones.add(createAccion(SELECCIONAR_TODO));
//		acciones.add(createAccion(ESTADO_ACTUAL));
		acciones.add(createAccion(VER_DATOS_ADICIONALES));
//		acciones.add(createAccion(APROBAR_ORIGINAL));
//		acciones.add(createAccion(RECHAZAR_ORIGINAL));
//		acciones.add(createAccion(APROBAR_CANJE));
//		acciones.add(createAccion(ESTADO_CANJE));
//		acciones.add(createAccion(RECHAZAR_CANJE));
		acciones.add(createAccion(EXPORTAR_A_EXCEL));
//		acciones.add(createAccion(CAMBIAR_COLOR_MATERIAL));
//		acciones.add(createAccion(CONGELAR_CLIENTE));
//		acciones.add(createAccion(CONGELAR_RESERVA));
//		acciones.add(createAccion(DESCONGELAR_CLIENTE));
//		acciones.add(createAccion(DESCONGELAR_RESERVA));
		//acciones.add(createAccion(MODIFICAR_CONGELAMIENTO_CLIENTE));
//		acciones.add(createAccion(REENVIAR_ARCHIVO));
//		acciones.add(createAccion(COPIAR_MATERIAL_SISTEMA_EDITORIAL));
//		acciones.add(createAccion(CAMBIAR_CANAL_VENTA));
//		acciones.add(createAccion(MATERIAL_SUELTO));
//		acciones.add(createAccion(PREVIEW));
//		acciones.add(createAccion(BAJADA_MASIVA_MATERIALES));
//		acciones.add(createAccion(INGRESAR_COMENTARIO_ORDEN_MATERIAL));
//		acciones.add(createAccion(CONTROLAR_MATERIAL_OP));
		return acciones;
	}

	/**
	 * Instancia la acci�n correspondiente al id recibida como par�metro.
	 * 
	 * @param idAccion.
	 * @return La acci�n cuyo id es idAccion.
	 */
	public static Accion createAccion(int idAccion) {
		Accion accion = null;
		switch(idAccion) {
//		case EnumAcciones.RECIBIR:
//			accion = new AccionRecibir();
//			break;
//		case EnumAcciones.IMPRIMIR_RECIBO:
//			accion = new AccionImprimirRecibo();
//			break;
//		case EnumAcciones.IMPRIMIR_PAPELETA:
//			accion = new AccionImprimirPapeleta();
//			break;
//		case EnumAcciones.INGRESAR_ORIGINAL:
//			accion = new AccionIngresarOriginal();
//			break;
//		case EnumAcciones.SUBIR_MATERIAL_PROCESADO:
//			accion = new AccionSubirMaterialProcesado();
//			break;
//		case EnumAcciones.BAJAR_MATERIAL:
//			accion = new AccionBajarMaterial();
//			break;
//		case EnumAcciones.BAJAR_ORIGINAL:
//			accion = new AccionBajarOriginal();
//			break;
//		case EnumAcciones.BORRAR_ORIGINAL:
//			accion = new AccionBorrarOriginal();
//			break;
//		case EnumAcciones.BORRAR_MATERIAL_PROCESADO:
//			accion = new AccionBorrarMaterialProcesado();
//			break;
//		case EnumAcciones.CAMBIAR_DESTINO:
//			accion = new AccionCambiarDestino();
//			break;
//		case EnumAcciones.BUSCAR:
//			accion = new AccionBuscar();
//			break;
//		case EnumAcciones.PROCESAR_REPETICIONES:
//			accion = new AccionProcesarRepeticiones();
//			break;
//		case EnumAcciones.LIBERAR_MATERIAL:
//			accion = new AccionLiberarMaterial();
//			break;
//		case EnumAcciones.CAMBIAR_TIPO_CONTENIDO:
//			accion = new AccionCambiarTipoContenido();
//			break;
//		case EnumAcciones.INGRESAR_INCIDENTE:
//			accion = new AccionIngresarIncidente();
//			break;
//		case EnumAcciones.INGRESAR_RESOLUCION:
//			accion = new AccionIngresarResolucion();
//			break;
//		case EnumAcciones.VER_AVISO:
//			accion = new AccionVerAviso();
//			break;
//		case EnumAcciones.ABRIR_PRODUCTO:
//			accion = new AccionAbrirProducto();
//			break;
//		case EnumAcciones.CERRAR_PRODUCTO:
//			accion = new AccionCerrarProducto();
//			break;
//		case EnumAcciones.ASOCIAR_MATERIAL_CON_AVISO:
//			accion = new AccionAsociarMaterialConAviso();
//			break;
//		case EnumAcciones.DESASOCIAR_MATERIAL_CON_AVISO:
//			accion = new AccionDesasociarMaterialConAviso();
//			break;
//		case EnumAcciones.DEVOLVER_MATERIAL:
//			accion = new AccionDevolverMaterial();
//			break;
//		case EnumAcciones.DESTRUIR_MATERIAL:
//			accion = new AccionDestruirMaterial();
//			break;
		case EnumAcciones.IMPRIMIR_LISTADO:
//			accion = new AccionImprimirListado();
			break;
//		case EnumAcciones.INGRESO_DIRECTO:
//			accion = new AccionIngresoDirecto();
//			break;
//		case EnumAcciones.VERIFICAR:
//			accion = new AccionVerificar();
//			break;
		case EnumAcciones.SELECCIONAR_TODO:
//			accion = new AccionSeleccionarTodo();
			break;
//		case EnumAcciones.ESTADO_ACTUAL:
//			accion = new AccionEstadoActual();
//			break;
		case EnumAcciones.VER_DATOS_ADICIONALES:
//			accion = new AccionVerDatosAdicionales();
			break;
		case EnumAcciones.EXPORTAR_A_EXCEL:
//			accion = new AccionExportarAExcel();
			break;
//		case EnumAcciones.PREVIEW:
//			accion = new AccionPreview();
//			break;
		default:
			accion = new AccionDefault();
			break;
		}
		return accion;
	}

	/**
	 * Devuelve si un tipo de acci�n se encuentra habilitado para un medio determinado.
	 * 
	 * @param idMedio El id del medio.
	 * @param tipo El id del tipo de accion.
	 * @return <b>true</b> si la acci�n se encuentra habilitada para el medio especificado.
	 * @see ar.pegasus.framework.entidades.enumeradores.EnumTipoAccion
	 */
	public static boolean accionHabilitada(int idMedio, int tipo) {
		int tipoAccionPorMedio = tipoAccionesPorMedio.get(idMedio);
		return EnumTipoAccion.esMenor(tipo, tipoAccionPorMedio);
	}

	/**
	 * Setea los tipos de acci�n por medio.
	 * 
	 * @param tipoAccionesPorMedio Los tipos de accion por medio.
	 */
	public static void setTipoAccionesPorMedio(Hashtable<Integer, Integer> tipoAccionesPorMedio/*
																								 * List<MedioEditorial>
																								 * medios,
																								 * Hashtable<MedioEditorial,
																								 * List<Accion>>
																								 * accionesPorMedio
																								 */) {
		EnumAcciones.tipoAccionesPorMedio = tipoAccionesPorMedio;
	}

	/**
	 * Devuelve el tipo de acci�n de mayor jerarqu�a de una lista de acciones.
	 * 
	 * @param acciones La lista de acciones.
	 * @return El id del tipo de accion de mayor jerarqu�a de la lista.
	 */
	@SuppressWarnings("unchecked")
	public static int getTipoAccion(List<Accion> acciones) {
		boolean consulta = false;
		for(Accion accion : acciones) {
			int tipoAccion = accion.getTipo();
			if(tipoAccion == EnumTipoAccion.OPERACION)
				return EnumTipoAccion.OPERACION;
			else if(tipoAccion == EnumTipoAccion.CONSULTA)
				consulta = true;
		}
		if(consulta)
			return EnumTipoAccion.CONSULTA;
		return EnumTipoAccion.NO_PERMITIDA;
	}

}