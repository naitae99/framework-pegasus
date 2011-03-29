package ar.pegasus.framework.templates.main.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.error.PRuntimeException;
import ar.pegasus.framework.entidades.GrupoModulos;
import ar.pegasus.framework.entidades.GrupoModulos.NewItemMenu;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.templates.main.PMainTemplate;
import ar.pegasus.framework.util.EtiquetasBotones;

/**
 * <p>Maneja los menues de modulos para un AbstractMainTemplate</p>
 * Delega la lectura y acceso a la configuraci�n de menues a <b>LectorConfiguracionMenues</b> 
 * 
 * Esta clase, basandose en la configuraci�n de menues, se encarga de agregar los menues y sus modulos
 * ; siempre llamando a la api provista por AbstractMainTemplate
 * 
 * Los menues de modulos se crean exclusivamente desde esta clase.
 * Hay otros menues que no (Ayuda/Ventana/Impresion...)
 *   
 * @author pforesto
 *
 */
public class ManejadorMenues {

	static Logger logger = Logger.getLogger(ManejadorMenues.class);
	
	//private List<MenuModulosSimple> menues= new ArrayList<MenuModulosSimple>();
	 
	@SuppressWarnings("unchecked")
	private PMainTemplate mainTemplate;
	private Map<GrupoModulos,MenuModulosSimple> mapGrupoMenu = new HashMap<GrupoModulos,MenuModulosSimple>();
	private static ManejadorMenues _instance;
	private LectorConfiguracionMenues configuracionMenues;
	
	public  List<GrupoModulos> getGruposModulos() {
		if (configuracionMenues==null)
			throw new PRuntimeException("this.configuracionMenues es NULL- Debe leer el xml de menues antes de pedirlo");
		
		return configuracionMenues.getGruposModulos();
	}
	
	private LectorConfiguracionMenues getConfiguracionMenues (){
		return this.configuracionMenues;
	}
	
	
	@SuppressWarnings("unchecked")
	public synchronized static ManejadorMenues getManejadorMenues (PMainTemplate mainTemplate) {
		if (_instance != null)
			return _instance;
		
		
		ManejadorMenues manejadorMenues = new ManejadorMenues (mainTemplate);
		manejadorMenues.configuracionMenues = new LectorConfiguracionMenues();
		
		manejadorMenues.configuracionMenues.crearConfiguracion();
		
		
		manejadorMenues.inicializarMenues();
		_instance = manejadorMenues;
		return manejadorMenues;
	}
	

	 
	
	@SuppressWarnings("unchecked")
	private void inicializarMenues() {
		GrupoModulos grupoDefault = this.configuracionMenues.getGrupoDefault();
		MenuModulosPrincipal menuModulosDefault = new MenuModulosPrincipal();
		menuModulosDefault.setText(grupoDefault.getNombre());
		menuModulosDefault.getMenuItemCambiarUsuario().addActionListener(mainTemplate.new CambiarUsuarioListener());
		menuModulosDefault.getMenuItemSalir().addActionListener( mainTemplate.new SalirListener());
		this.mainTemplate.agregarMenu(menuModulosDefault, 0);
		Set<GrupoModulos> yaprocesados  = new HashSet<GrupoModulos>();
		yaprocesados.add(grupoDefault);
		//menues.add(menuModulosDefault);
		mapGrupoMenu.put(grupoDefault, menuModulosDefault);
		
		
		int orden =1;
		for (GrupoModulos grupo: this.getGruposModulos()){
			if (yaprocesados.contains(grupo))
				continue;
			MenuModulosSimple menuModulos = new MenuModulosSimple();				
			menuModulos.setText(grupo.getNombre());
			this.mainTemplate.agregarMenu(menuModulos, orden);
			//menues.add(menuModulos);
			mapGrupoMenu.put(grupo,menuModulos);
			orden++;
		}
		
	}


	@SuppressWarnings("unchecked")
	private ManejadorMenues (PMainTemplate mainTemplate) {
		this.mainTemplate = mainTemplate;
	}
	

	
	public MenuModulosPrincipal getMenuDefault() {
		return (MenuModulosPrincipal)registerMenu(this.getConfiguracionMenues().getGrupoDefault());
	}
	
	
	private MenuModulosSimple getMenuPorModulo (Modulo modulo){
		GrupoModulos grupo = getConfiguracionMenues().getGrupoPorModulo(modulo);
		return registerMenu(grupo);
	}		
	
	
	
	private MenuModulosSimple registerMenu (GrupoModulos grupo){
		MenuModulosSimple menuGrupo = mapGrupoMenu.get(grupo);
		if (menuGrupo == null){
			menuGrupo = new MenuModulosSimple();
			menuGrupo.setText( grupo.getNombre());
			mapGrupoMenu.put(grupo, menuGrupo);
		}
		return menuGrupo;
	}
	
	

	/**
	 * limpia todos los menues de modulos. Incluye submenues.
	 */
	public void limpiarModulos(){
		for (MenuModulosSimple menu: this.mapGrupoMenu.values()){
			menu.limpiarModulos();
		}
	}
	
	/**
	 * Recibe un modulo.
	 * Determina en qu� menu debe insertarlo (s/conf), y lo inserta.
	 * Crea submenues a medida que los necesita
	 * 
	 * @param modulo
	 */
	@SuppressWarnings("unchecked")
	public void agregarModulo(Modulo modulo) {
		
		if(modulo.isSeparador()) {
			int pos = this.getMenuDefault().getMenuComponentCount() - 3;
			if (pos <= 0 || !(this.getMenuDefault().getMenuComponents()[pos-1] instanceof JSeparator)){
				this.getMenuDefault().add(new JSeparator(), pos);
			}
			return;
		} 
		
		//chequeo que existe el submenu donde va este modulo
		checkExisteSubmenu(modulo);
		
		MenuModulosSimple menuModulos = getMenuPorModulo(modulo);
		
		
		
		try {
			if(menuModulos.hasDecorator()) {
				//Carga el �cono al m�dulo
				Icon icono = menuModulos.getMenuDecorator().getIcono(modulo.getUbicacion());
				//Agrega el m�dulo al men�
				menuModulos.agregarModulo(modulo.getNombre(), icono, mainTemplate.new ModuloListener(modulo));
			} else {
				//Agrega el m�dulo al men�
				menuModulos.agregarModulo(modulo.getNombre(), mainTemplate.new ModuloListener(modulo));
			}
		} catch(ClassNotFoundException e) { //Si el nombre de la clase es inv�lido
			menuModulos.agregarModulo(modulo.getNombre(), null);
			PException exception = new PException(BossError.ERR_APLICACION, EtiquetasBotones.PROBLEMA_CONSTRUCCION_MODULO,
					"No se pudo cargar la clase (" + modulo.getUbicacion() + ") asociada al módulo " + modulo.getNombre(), e,
					new String[] { EtiquetasBotones.VERIFICAR_INFO_MODULO });
			BossError.gestionarError(exception);
		} catch(NullPointerException e) { //Si la ubicaci�n es null
			menuModulos.agregarModulo(modulo.getNombre(), null);
			PException exception = new PException(BossError.ERR_APLICACION, EtiquetasBotones.PROBLEMA_CONSTRUCCION_MODULO,
					"La ubicación del módulo '" + modulo.getNombre() + "' es nula", e,
					new String[] { EtiquetasBotones.VERIFICAR_INFO_MODULO });
			BossError.gestionarError(exception);
		}
	
	}

	private void checkExisteSubmenu(Modulo modulo) {
		GrupoModulos grupo = getConfiguracionMenues().getGrupoPorModulo(modulo);
		MenuModulosSimple menuModulosGrupo = registerMenu(grupo);
		
		if (grupo.getPadre() != null){
			MenuModulosSimple menuPadre = mapGrupoMenu.get(grupo.getPadre());
			if (!Arrays.asList(menuPadre.getMenuComponents()).contains(menuModulosGrupo)){
				menuPadre.agregarSubmenu(menuModulosGrupo);
			}
		}
	}

	/**
	 * mostrar Iconos en TODOS los menues de modulos
	 */
	public void mostrarIconos() {
		for ( MenuModulosSimple menu: this.mapGrupoMenu.values()){
			if(menu.hasDecorator()) {
				menu.mostrarIconos();
			}	
		}
	}

	/**
	 * agrega modulos que no se leyeron desde el portal 
	 * 
	 * @param modulosUsuarioAplicacion
	 */
	public void completarModulosSinPortal(List<Modulo> modulosUsuarioAplicacion) {
		for (GrupoModulos grupo: this.getGruposModulos()){
			grupo.completarModulosSinPortal(modulosUsuarioAplicacion);	
		}
	}

	/**
	 * Informa cuantos <b>menues de modulos</b> se crearon o se crearán Arriba.
	 * No toma en cuenta submenues, ni otros menues (ej: no cuenta Ayuda/Impresion...)
	 * 
	 * @return
	 */
	public byte getCantidadMenuesArriba() {
		byte cnt = 0;
		for (GrupoModulos grupo: this.getConfiguracionMenues().getGruposModulos()){
			cnt += (grupo.getPadre() == null?1:0);
		}
		return cnt;
	}

	/**
	 * Crea e inserta los submenues especificados en el XML
	 * Se insertaran en el orden declarado.
	 * Se insertaran al inicio, (como hace windows con los directorios)
	 * 
	 */
	public void iniciarSubmenues() {
		for (GrupoModulos grupo: this.getConfiguracionMenues().getGruposModulos()){
			if (grupo.getPadre() == null){
				/* sin ordenaci�n
				Collections.sort(grupo.getSubgrupos(), new Comparator<GrupoModulos>(){
					public int compare(GrupoModulos g1, GrupoModulos g2) {
						return g1.getNombre().compareTo(g2.getNombre());
					}
				});
				*/
				
				for (GrupoModulos subgrupo : grupo.getSubgrupos()){
					MenuModulosSimple submenu = registerMenu(subgrupo);
					mapGrupoMenu.get(grupo).agregarSubmenu(submenu);
				}
				
			}
		}
	}

	/**
	 * Util que busca un modulo en una lista seg�n el Id. 
	 * @param modulos
	 * @param idMod
	 * @return
	 */
	private Modulo findModulo (List<Modulo> modulos, Integer idMod){
		for (Modulo m: modulos){
			if (m.getIdModulo() == idMod )
				return m;
		}
		return null;
	}
	
	/**
	 * recursivo.
	 * Recorrido en orden de un arbol.
	 * Ver {@link #reordenamiento(List)}
	 * 
	 * @param todosLosModulos
	 * @param modulosOrdenandose
	 * @param grupos
	 */
	private void reordenamiento (List<Modulo> todosLosModulos,List<Modulo> modulosOrdenandose, List<GrupoModulos> grupos){
		for (GrupoModulos grupo: grupos){
			if (!grupo.getSubgrupos().isEmpty()){
				reordenamiento (todosLosModulos, modulosOrdenandose,grupo.getSubgrupos());
			}
			for (Object modulo : grupo.getIdsModuloYNuevos()){
				if (modulo instanceof Integer){
					Modulo m = findModulo(todosLosModulos, (Integer)modulo);
					if (m!=null){
						modulosOrdenandose.add(m);
					}
				}else if (modulo instanceof NewItemMenu){
					modulosOrdenandose.add((Modulo)modulo);
				}
			}
		}	
	}
	
	/**
	 * Reordenamiento de los modulos, seg�n XML.
	 * <ol>
	 * <li>Toma el xml, y reordena los modulos del listado.</li>
	 * <li>Recorre el arbol, el orden es: primero los hijos, luego yo (recursivo).</li>
	 * <li>Luego inserta los que no est�n en el �rbol.</li>
	 * </ol>
	 * @param modulos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Modulo> reordenamiento(List<Modulo> modulos) {
		
		List<Modulo> modulosOrdenandose = new ArrayList<Modulo>(); 
		reordenamiento(modulos, modulosOrdenandose, this.getConfiguracionMenues().getGruposModulos());
		
		Collection disjuncion = restaConjuntosOrdenada(modulos,modulosOrdenandose );
		modulosOrdenandose.addAll(disjuncion);
		
		return modulosOrdenandose;
		
	}
	
	/**
	 *{p tq/ p pert IZQ ^ p no pert DER}
	 * @param <T>
	 * @param izq
	 * @param der
	 * @return
	 */
	private<T> Collection<T> restaConjuntosOrdenada (Collection<T> izq, Collection<T> der){
		ArrayList<T> aux = new ArrayList<T>();
		for (T obj: izq){
			if (!der.contains(obj)){
				aux.add(obj);
			}
		}
		return aux;
	}
	
	
	
}