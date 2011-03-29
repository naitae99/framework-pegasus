package ar.pegasus.framework.templates.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import org.apache.log4j.Logger;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PCursor;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.templates.config.IConfigClienteManager;
import ar.pegasus.framework.templates.login.PLoginManager;
import ar.pegasus.framework.templates.main.menu.ManejadorMenues;
import ar.pegasus.framework.templates.main.menu.MenuModulosSimple;
import ar.pegasus.framework.templates.main.skin.AbstractSkin;
import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.MiscUtil;

public abstract class PMainTemplate<T extends PLoginManager, V extends IConfigClienteManager> extends AbstractMainTemplate<T>  {
	private static final long serialVersionUID = -4275264271460344752L;

	protected IConfigClienteManager configClienteManager;


	/**
	 * Método constructor.
	 * @param idAplicacion El id de la aplicación.
	 * @param version La versión de la aplicación.
	 * @throws PException 
	 */
	protected PMainTemplate(int idAplicacion, String version) throws PException {
		super(idAplicacion, version);
		construirMenues();
	}

	/**
	 * Método constructor.
	 * @param idAplicacion El id de la aplicación.
	 * @param titulo El título de la aplicación.
	 * @param version La versión de la aplicación.
	 * @throws PException 
	 */
	protected PMainTemplate(int idAplicacion, String titulo, String version) throws PException {
		super(idAplicacion, titulo, version);
		construirMenues();
	}

	/**
	 * Crea el manager para el manejo de la configuración del cliente.
	 * @return IConfigClienteManager
	 * @see ar.pegasus.framework.templates.main.config.IConfigClienteManager
	 */
	protected abstract IConfigClienteManager crearConfigClienteManager();

	/** Método que se ejecuta <b>antes</b> de la construcción de la ventana */
	protected void preConstruccion() {
		//Fix del FileChooser
		if(MiscUtil.getOSName().equalsIgnoreCase("Windows NT") && MiscUtil.getOSVersion().equals("4.0")) {
			System.setProperty("swing.disableFileChooserSpeedFix", "true");
		}
	}

	/** Método que se ejecuta <b>después</b> de la construcción de la ventana 
	 * @throws PException */
	protected void postConstruccion() throws PException {
		//Carga la configuración del cliente
		cargarConfiguracionCliente();
	}

	/**
	 * Método llamado inmediatamente después de que se loguea el usuario.
	 * Una vez que el usuario se logueó se le piden al manager los módulos que tiene
	 * asignados y se llena el menú <b>Módulos</b>.
	 * @throws PException 
	 */
	protected void postLogin() throws PException {
		try {
			//Se carga la lista de módulos de usuario
			List<Modulo> modulosUsuario = loginManager.getModulosUsuario();
			if(modulosUsuario == null || modulosUsuario.isEmpty()) {
				PJOptionPane.showWarningMessage(this, "No tiene acceso a la aplicación.", getTitle());
				verDialogoLogin();
			} else {
				//Se llena el menu con los modulos
				agregarModulos();
			}
		} catch(PException e) {
			BossError.gestionarError(e);
		}
	}

	/** Método llamado antes de que se cierre la ventana principal */
	protected void preSalir() {
		//Guarda la configuración del cliente
		guardarConfiguracionCliente();
	}

	/**
	 * Devuelve el menú <b>Módulos</b>.
	 * @return menuModulos
	 * @see MenuModulos
	 * @deprecated devuelve solo el primero. Reemplazar por getxxxxxxxxx.get(0)
	 */
	protected MenuModulosSimple getMenuModulos() {
		return ManejadorMenues.getManejadorMenues(this).getMenuDefault();
	}

	/**
	 * Aplica el skin a la aplicaci�n.
	 * Deber� llamarse antes de hacer la llamada al m�todo <b>iniciarAplicacion()</b> para
	 * que se aplique el skin en su totalidad.
	 * @param skin El skin a ser aplicado.
	 * @throws PException En caso de que se produzca alg�n error en la inicializaci�n
	 * del skin.
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public static void aplicarSkin(AbstractSkin skin) throws PException {
		skin.init();
	}

	
	/* Construye el men� 'M�dulos' */
	private void construirMenues() {
		ManejadorMenues.getManejadorMenues(this);
	}

	static Logger logger = Logger.getLogger(PMainTemplate.class); 
	
	/* Agrega los m�dulos a partir de la lista de m�dulos obtenida del login manager */
	private void agregarModulos() throws PException {
		List<Modulo> modulos = construirListaModulos();
		ManejadorMenues manejadorMenues = ManejadorMenues.getManejadorMenues(this);
		
		manejadorMenues.limpiarModulos();
		manejadorMenues.iniciarSubmenues();
		
		for(Modulo modulo : manejadorMenues.reordenamiento(modulos)) {
			manejadorMenues.agregarModulo(modulo);
		}
		manejadorMenues.mostrarIconos();
	}

	/**
	 * {@link AbstractMainTemplate#agregarMenu(JMenu, int)}
	 * 
	 * Solo las subclases y ManejadorMenues(this) lo llaman.
	 */
	public void agregarMenu(JMenu menu, int pos) {
		super.agregarMenu(menu, pos);
	}
	
	/**
	 * {@link AbstractMainTemplate#agregarMenu(JMenu, int)}
	 * 
	 * Toma en cuenta el desplazamiento generado por los menues de modulos adicionales
	 * 
	 * 
	 */
	public void agregarMenuDespl(JMenu menu, int pos) {
		byte cantMArriba = ManejadorMenues.getManejadorMenues(this).getCantidadMenuesArriba();
		//desplaza segun los menues de modulos que se agregan
		//en conf. x defecto: (cantMArriba-1)+pos = pos
		super.agregarMenu(menu, (cantMArriba-1)+pos);
	}	
	
	
	
	/* Construye la lista de m�dulos obtenida del manager de login */
	private List<Modulo> construirListaModulos() throws PException {
		//Se quitan los m�dulos anulados y se ordena la lista de m�dulos que ve el usuario
		List<Modulo> modulosUsuario = loginManager.getModulosUsuario();
		eliminarModulosAnulados(modulosUsuario);
		Collections.sort(modulosUsuario);
		//Se hace lo mismo con la lista de m�dulos de la aplicaci�n
		List<Modulo> modulosAplicacion = loginManager.getModulosAplicacion();
		eliminarModulosAnulados(modulosAplicacion);
		List<Modulo> modulosAplicacionConSeparadores = new ArrayList<Modulo>();
		int ordenEsperado = Modulo.MODULO_NUEVO;
		//Se agregan los separadores a la lista de m�dulos de la aplicaci�n
		if(modulosAplicacion != null) {
			for(Modulo moduloAplicacion : modulosAplicacion) {
				if(ordenEsperado != Modulo.MODULO_NUEVO && ordenEsperado != moduloAplicacion.getOrden()) {
					modulosAplicacionConSeparadores.add(Modulo.crearModuloSeparador());
				}
				modulosAplicacionConSeparadores.add(moduloAplicacion);
				ordenEsperado = moduloAplicacion.getOrden() + 1;
			}
		}
		//Se agregan los separadores a la lista de m�dulos que ve el usuario usando
		//para ello a la lista de m�dulos de la aplicaci�n con separadores
		List<Modulo> modulosUsuarioAplicacion = new ArrayList<Modulo>();
		int nroGrupo = 1;
		if(modulosUsuario != null) {
			for(Modulo moduloUsuario : modulosUsuario) {
				int nroGrupoSig = getNroGrupoModulo(moduloUsuario, modulosAplicacionConSeparadores);
				if(nroGrupoSig != nroGrupo) {
					modulosUsuarioAplicacion.add(Modulo.crearModuloSeparador());
					nroGrupo = nroGrupoSig;
				}
				modulosUsuarioAplicacion.add(moduloUsuario);
			}
		}
		
		ManejadorMenues.getManejadorMenues(this).completarModulosSinPortal(modulosUsuarioAplicacion);
		
		//for (Modulo m: modulosUsuarioAplicacion){
		//	logger.debug("modulo (id,nombre): (" + m.getIdModulo()+ "," + m + ")");
		//}
		
		return modulosUsuarioAplicacion;
	}

	/* M�todo helper para la construcci�n de la lista de m�dulos */
	private int getNroGrupoModulo(Modulo modulo, List<Modulo> modulos) {
		int nroGrupo = 1; 
		for(Modulo m : modulos) {
			if(m.equals(modulo)) {
				return nroGrupo;
			}
			if(m.isSeparador()) {
				nroGrupo++;
			}
		}
		return nroGrupo;
	}

	/* M�todo helper para la construcci�n de la lista de m�dulos */
	private void eliminarModulosAnulados(List<Modulo> modulos) throws PException {
		List<Modulo> modulosAnulados = new ArrayList<Modulo>();
		if(modulos != null) {
			for(Modulo modulo : modulos) {
				Integer orden = modulo.getOrden();
				if(orden == null) {
					PException e = new PException(BossError.ERR_APLICACION, "Problema construyendo el menú de la aplicación",
							"El módulo '" + modulo.getNombre() + "' no tiene definido el orden",
							new String[] {"Verifique la información del módulo en el Portal de Aplicaciones"});
					throw e;
				} else if(orden == Modulo.MODULO_ANULADO) {
					modulosAnulados.add(modulo);
				}
			}
			modulos.removeAll(modulosAnulados);
		}
	}

	/* Carga la configuraci�n del cliente desde el manager */
	private void cargarConfiguracionCliente() {
		configClienteManager = crearConfigClienteManager();
		try {
			if(configClienteManager == null) {
				System.err.println(EtiquetasBotones.ERROR_MANAGER_CONFIG_LOAD);
			} else {
				configClienteManager.cargarConfiguracionCliente();
			}
		} catch(PException e) {
			e.setMensajeContexto(EtiquetasBotones.ERROR_LOAD_CONFIG);
			BossError.gestionarError(e);
		}
	}

	/* Guarda la configuraci�n del cliente desde el manager */
	private void guardarConfiguracionCliente() {
		try {
			if(configClienteManager == null) {
				System.err.println(EtiquetasBotones.ERROR_MANAGER_CONFIG_SAVE);
			} else {
				configClienteManager.guardarConfiguracionCliente();
			}
		} catch(PException e) {
			e.setMensajeContexto(EtiquetasBotones.ERROR_SAVE_CONFIG);
			BossError.gestionarError(e);
		}
	}

	/* Clase listener de eventos de los m�dulos obtenidos del manager de login */
	@SuppressWarnings("unchecked")
	public class ModuloListener implements ActionListener {
		Modulo modulo;
		@SuppressWarnings("rawtypes")
		Class clase;
		JInternalFrame form;

		public ModuloListener(Modulo modulo) throws ClassNotFoundException, NullPointerException {
			this.modulo = modulo;
			this.clase = Class.forName(TraductorUbicaciones.getInstance().traducirUbicacion(modulo)) ;
		}
		
		public void actionPerformed(ActionEvent evt) {
			if(form == null || form.isClosed()) {
				PCursor.startWait(getContentPane());
				try {
					try {
						form = (JInternalFrame)clase.getConstructor(new Class[] { Modulo.class }).newInstance(new Object[] { modulo });
					} catch(NoSuchMethodException e) {
						form = (JInternalFrame)clase.getConstructor(new Class[] { Integer.class }).newInstance(new Object[] { modulo.getIdModulo() });
					}

					if(!form.isVisible()) {
						desktop.add(form);
						form.setVisible(true);
						GuiUtil.centrarEnPadre(form);
					} else {
						if(form.isIcon()) {
							try {
								form.setIcon(false);
							} catch(PropertyVetoException e) {
							}
						}
					}
					try {
						form.setSelected(true);
					} catch(PropertyVetoException e) {
					}
				
				} catch(Exception e1) {
					if (e1 instanceof InvocationTargetException && e1.getCause() instanceof PException) {
						BossError.gestionarError((PException)e1.getCause());
					} else {						
						BossError.gestionarError(BossError.ERR_APLICACION, EtiquetasBotones.ERROR_CONSTRUCCION_MODULO , "módulo " + modulo.getNombre(), e1, new String[] {"Comuníquese con Desarrollo de Sistemas."});
					}
				} finally {
					PCursor.endWait(getContentPane());
				}
			}
		}
	}

	/* Clase listener de eventos de la opci�n 'Cambiar Usuario' en el men� 'M�dulos' */
	public class CambiarUsuarioListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			guardarConfiguracionCliente();
			if(desktop.cerrarVentanas()) {
				verDialogoLogin();
			}
		}
	}

	/* Clase listener de eventos de la opci�n 'Salir' del men� 'M�dulos' */
	public class SalirListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			salir();
		}
	}

}
