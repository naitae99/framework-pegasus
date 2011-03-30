package ar.pegasus.framework.templates.main.menu;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaElementos;

@SuppressWarnings("serial")
public class MenuModulosPrincipal extends MenuModulosSimple {

	private JMenuItem menuSalir;
	private JMenuItem menuCambiarUsuario;

	public MenuModulosPrincipal() {
		super(EtiquetasBotones.MODULOS, 'M');
		//Cambiar usuario
		addSeparator();
		add(getMenuItemCambiarUsuario());
		//Salir
		add(getMenuItemSalir());
	}

	public JMenuItem getMenuItemCambiarUsuario() {
		if(menuCambiarUsuario == null) {
			menuCambiarUsuario = new JMenuItem(EtiquetasBotones.CAMBIAR_USUARIO);
		}
		return menuCambiarUsuario;
	}

	public JMenuItem getMenuItemSalir() {
		if(menuSalir == null) {
			menuSalir = new JMenuItem(EtiquetasBotones.SALIR);
		}
		return menuSalir;
	}
	
	public void limpiarModulos() {
		removeAll();
		//Cambiar usuario
		addSeparator();
		add(getMenuItemCambiarUsuario());
		//Salir
		add(getMenuItemSalir());
	}
	
	public void mostrarIconos() {
		getMenuItemCambiarUsuario().setIcon(ImageUtil.loadIcon(RutaElementos.ICONO_CAMBIAR_USUARIO));
		getMenuItemSalir().setIcon(ImageUtil.loadIcon(RutaElementos.ICONO_SALIR));
	}
	
	public void agregarModulo(String nombre, Icon icono, ActionListener listener) {
		JMenuItem item = new JMenuItem(nombre, icono);
		item.addActionListener(listener);
		int pos = getMenuComponentCount() -3; //3 --> Separador + Cambiar Usuario + Salir
		if(listener == null) {
			item.setEnabled(false);
			add(item, pos);
		} else {
			add(item, pos);
		}
	}
	
	public void agregarSubmenu(MenuModulosSimple menuModulosGrupo) {
		int pos = getMenuComponentCount()-3; //3 --> Separador + Cambiar Usuario + Salir
		add(menuModulosGrupo, pos);
	}		
	

}