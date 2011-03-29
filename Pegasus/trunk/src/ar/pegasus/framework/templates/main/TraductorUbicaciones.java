package ar.pegasus.framework.templates.main;

import java.util.HashMap;
import java.util.Map;

import ar.pegasus.framework.entidades.core.Modulo;

public class TraductorUbicaciones {

	private static TraductorUbicaciones instance = new TraductorUbicaciones();

	public static TraductorUbicaciones getInstance() {
		return instance;
	}

	private Map<String, String> traduccionUbicaciones = new HashMap<String, String>();

	private TraductorUbicaciones () {
		// Publicidad
		traduccionUbicaciones.put("abonos.gui.GuiAbmUsuariosyMediosPublicitarios", "ar.clarin.abonos.gui.GuiAbmUsuariosyMediosPublicitarios");
		traduccionUbicaciones.put("gui.ModuloConsultarCanje", "ar.clarin.publicidad2.gui.ModuloConsultarCanje");
		traduccionUbicaciones.put("gui.ModuloAprobarCanje", "ar.clarin.publicidad2.gui.ModuloAprobarCanje");
		traduccionUbicaciones.put("gui.listados.ListadoTransitoLayout", "ar.clarin.publicidad2.gui.listados.ListadoTransitoLayout");
		traduccionUbicaciones.put("gui.control.ModuloControlarCanalesVenta", "ar.clarin.publicidad2.gui.control.ModuloControlarCanalesVenta");
		traduccionUbicaciones.put("gui.congelamiento.ModuloCongelador", "ar.clarin.publicidad2.gui.congelamiento.ModuloCongelador");
		traduccionUbicaciones.put("gui.ModuloAprobarOriginal", "ar.clarin.publicidad2.gui.ModuloAprobarOriginal");
		traduccionUbicaciones.put("gui.ModuloAdministrarCondicionVenta", "ar.clarin.publicidad2.gui.ModuloAdministrarCondicionVenta");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosAprobacionCanje", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosAprobacionCanje");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosRechazoCanje", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosRechazoCanje");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosCongelamientoCliente", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosCongelamientoCliente");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosDescongelamientoCliente", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosDescongelamientoCliente");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosAprobacionOriginal", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosAprobacionOriginal");
		traduccionUbicaciones.put("gui.motivos.ModuloAbmMotivosRechazoOriginal", "ar.clarin.publicidad2.gui.motivos.ModuloAbmMotivosRechazoOriginal");
		traduccionUbicaciones.put("gui.GuiAdministrarRecargos", "ar.clarin.publicidad2.gui.GuiAdministrarRecargos");
		traduccionUbicaciones.put("abonos.gui.GuiAdministrarOrdenesAbonosIntranet", "ar.clarin.abonos.gui.GuiAdministrarOrdenesAbonosIntranet");
		traduccionUbicaciones.put("abonos.gui.GuiAprobarOrdenesAbonosIntranet", "ar.clarin.abonos.gui.GuiAprobarOrdenesAbonosIntranet");
		traduccionUbicaciones.put("abonos.gui.GuiAbmTiposAbonosIntranet", "ar.clarin.abonos.gui.GuiAbmTiposAbonosIntranet");
		traduccionUbicaciones.put("abonos.gui.GuiAbmMotivosAccionesOrdenIntranet", "ar.clarin.abonos.gui.GuiAbmMotivosAccionesOrdenIntranet");
		traduccionUbicaciones.put("gui.ModuloPersonalizarColores", "ar.clarin.publicidad2.gui.ModuloPersonalizarColores");
		traduccionUbicaciones.put("gui.listados.ListadoControlReservas", "ar.clarin.publicidad2.gui.listados.ListadoControlReservas");
		traduccionUbicaciones.put("gui.listados.ListadoReservasUnMedio", "ar.clarin.publicidad2.gui.listados.ListadoReservasUnMedio");
		traduccionUbicaciones.put("gui.listados.ListadoReservasLiberadas", "ar.clarin.publicidad2.gui.listados.ListadoReservasLiberadas");
		traduccionUbicaciones.put("gui.listados.ListadoOrdenesPublicidad", "ar.clarin.publicidad2.gui.listados.ListadoControlReservas");
		traduccionUbicaciones.put("gui.listados.ListadoSugerenciaEjecutivos", "ar.clarin.publicidad2.gui.listados.ListadoSugerenciaEjecutivos");
		traduccionUbicaciones.put("gui.listados.ListadoSugerenciaDiagramador", "ar.clarin.publicidad2.gui.listados.ListadoSugerenciaDiagramador");
		traduccionUbicaciones.put("gui.reportes.GuiReportesEstadosyMotivosCanje", "ar.clarin.publicidad2.gui.reportes.GuiReportesEstadosYMotivosCanje");
		traduccionUbicaciones.put("gui.listados.GuiIngresoMaterialesyOrdenesxHorario", "ar.clarin.publicidad2.gui.listados.GuiIngresoMaterialesyOrdenesxHorario");
		
		//Credito
		//traduccionUbicaciones.put("ar.clarin.credito.gui.GuiAprobarOrdenesPublicidad","ar.clarin.credito.gui.GuiAprobarOrdenesPublicidadNew");
		//traduccionUbicaciones.put("ar.clarin.credito.gui.GuiAprobarOrdenesPublicidadRestriccion","ar.clarin.credito.gui.GuiAprobarOrdenesPublicidadRestriccionNew");
		//traduccionUbicaciones.put("ar.clarin.credito.gui.GuiAprobarOrdenesPublicidadSaldo","ar.clarin.credito.gui.GuiAprobarOrdenesPublicidadSaldoNew");
		//traduccionUbicaciones.put("ar.clarin.credito.gui.consultar.GuiConsultarOrdenesPublicidad","ar.clarin.credito.gui.GuiConsultarOrdenesPublicidad");
		//traduccionUbicaciones.put("ar.clarin.credito.gui.GuiConsultarOrdenesPublicidadSaldo","ar.clarin.credito.gui.GuiConsultarOrdenesPublicidadSaldoNew");
		
		
		// Producto
		traduccionUbicaciones.put("gui.GuiAdministrarCaidasColor", "ar.clarin.producto.gui.GuiAdministrarCaidasColor");
		traduccionUbicaciones.put("gui.GuiAdministrarGeometrias", "ar.clarin.producto.gui.GuiAdministrarGeometrias");
		traduccionUbicaciones.put("gui.GuiAdministrarUbicacionesExtendidas", "ar.clarin.producto.gui.GuiAdministrarUbicacionesExtendidas");
		traduccionUbicaciones.put("gui.GuiMedidasPaginas", "ar.clarin.producto.gui.GuiMedidasPaginas");
		traduccionUbicaciones.put("horariocierre.gui.GuiAdministrarListadoHorarioCierre", "ar.clarin.producto.horariocierre.gui.GuiAdministrarListadoHorarioCierre");
		traduccionUbicaciones.put("gui.GuiAdministrarUbicaciones", "ar.clarin.producto.gui.GuiAdministrarUbicaciones");
		traduccionUbicaciones.put("gui.incompatibilidades.GuiAdministrarIncompatibilidades", "ar.clarin.producto.gui.incompatibilidades.GuiAdministrarIncompatibilidades");
		// Comentar la línea de abajo para generar SGP-Materiales
//		traduccionUbicaciones.put("gui.GuiConfigurarProductos", "ar.clarin.producto.gui.GuiConfigurarProductos");
		traduccionUbicaciones.put("gui.GuiGenerarSalidaProductos", "ar.clarin.producto.gui.GuiGenerarSalidaProductos");
		traduccionUbicaciones.put("gui.GuiAdministrarProductos", "ar.clarin.producto.gui.GuiAdministrarProductos");
		traduccionUbicaciones.put("gui.GuiAdministrarPrefProductoSeccion", "ar.clarin.producto.gui.GuiAdministrarPrefProductoSeccion");
		traduccionUbicaciones.put("gui.GuiAdministrarPreferenciasPagina", "ar.clarin.producto.gui.GuiAdministrarPreferenciasPagina");
		
		// Retorno
//		traduccionUbicaciones.put("ar.clarin.retorno.gui.GuiAdministrarRetorno", "ar.clarin.retorno.gui.ModuloAdministrarRetorno");
//		traduccionUbicaciones.put("ar.clarin.retorno.gui.GuiAdministrarRetornoConsulta", "ar.clarin.retorno.gui.ModuloAdministrarRetorno");
//		traduccionUbicaciones.put("ar.clarin.retorno.gui.GuiAdministrarRetornoEstadistico", "ar.clarin.retorno.gui.ModuloAdministrarRetorno");
//		traduccionUbicaciones.put("ar.clarin.retorno.gui.GuiAdministrarRetornoControl", "ar.clarin.retorno.gui.ModuloAdministrarRetorno");
//		traduccionUbicaciones.put("gui.GuiAdministrarProductosRetornoBase", "ar.clarin.retorno.gui.GuiAdministrarProductosRetornoBase");
//		traduccionUbicaciones.put("ar.clarin.retorno.gui.reportes.GuiReporteEstadisticasPorPagina", "ar.clarin.retorno.gui.ModuloReportePorPagina");

		// ADC

		traduccionUbicaciones.put("ar.clarin.adc.gui.mascaras.GUIAdministrarMascaras","ar.clarin.adc.gui.administracion.GUIAdministrarMascaras");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeOrganizacionADC", "ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeOrganizacionADC");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeEventoADC", "ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeEventoADC");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeFuncion", "ar.clarin.adc.gui.administracion.GuiAdministrarTiposDeFuncion");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarEventosADC", "ar.clarin.adc.gui.administracion.GuiAdministrarEventosADC");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarPaises", "ar.clarin.adc.gui.administracion.GuiAdministrarPaises");
		traduccionUbicaciones.put("ar.clarin.adc.gui.administracion.GuiAdministrarPersonas", "ar.clarin.adc.gui.administracion.GuiAdministrarPersonas");
		traduccionUbicaciones.put("ar.clarin.adc.gui.catalogador.paginas.ModuloSeleccionarEdicionesPaginas", "ar.clarin.adc.gui.catalogador.paginas.ModuloSeleccionarEdicionesPaginas");
	}

	public String traducirUbicacion(Modulo modulo) {
		if(traduccionUbicaciones.containsKey(modulo.getUbicacion())) {
			return traduccionUbicaciones.get(modulo.getUbicacion());
		}
		return modulo.getUbicacion();
	}
}
