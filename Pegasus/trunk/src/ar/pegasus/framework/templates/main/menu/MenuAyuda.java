package ar.pegasus.framework.templates.main.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.JMenuItem;

import ar.pegasus.framework.componentes.PAboutScreen;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.util.AnchorTrick;
import ar.pegasus.framework.util.EtiquetasBotones;

public class MenuAyuda extends PJMenu {
	private static final long serialVersionUID = -8940253575102458429L;

	private JMenuItem menuAcercaDe;
	private JMenuItem menuContenidos;
	private PAboutScreen aboutScreen;
	private static HelpSet helpSet;
	private static HelpBroker helpBroker;
	private String archivoAyuda;
	private String imagenAbout;
	private String version;

	public MenuAyuda() {
		super(EtiquetasBotones.AYUDA, 'A');
		add(getMenuAcercaDe());
	}

	private void addContenidos() {
		add(getMenuContenidos());
	}

	/** Clase listener del menú 'Ayuda' */
	class AyudaListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			//Contenidos
			if(evt.getSource().equals(getMenuContenidos())) {
				helpBroker.setDisplayed(true);
				//Acerca De...
			} else if(evt.getSource().equals(getMenuAcercaDe())) {
				verAboutScreen(getImagenAbout(), EtiquetasBotones.COMPANY, getVersion());
			}
		}
	}

	public JMenuItem getMenuAcercaDe() {
		if(menuAcercaDe == null) {
			menuAcercaDe = new JMenuItem(EtiquetasBotones.ABOUT);
			menuAcercaDe.addActionListener(new AyudaListener());
			menuAcercaDe.setEnabled(false);
		}
		return menuAcercaDe;
	}

	public JMenuItem getMenuContenidos() {
		if(menuContenidos == null) {
			menuContenidos = new JMenuItem(EtiquetasBotones.CONTENIDOS);
			menuContenidos.addActionListener(new AyudaListener());
		}
		return menuContenidos;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getArchivoAyuda() {
		return archivoAyuda;
	}

	public void setArchivoAyuda(String archivoAyuda) throws PException {
		this.archivoAyuda = archivoAyuda;
		crearHelp();
	}

	public String getImagenAbout() {
		return imagenAbout;
	}

	public void setImagenAbout(String imagenAbout) {
		this.imagenAbout = imagenAbout;
		getMenuAcercaDe().setEnabled(true);
	}

	/* Crea la ayuda en pantalla */
	private void crearHelp() throws PException {
		ClassLoader cl = AnchorTrick.class.getClassLoader();
		try {
			URL hsURL = new URL(getArchivoAyuda()) ;
			helpSet = new HelpSet(cl, hsURL);
			helpBroker = helpSet.createHelpBroker();
			addContenidos() ;
		} catch(HelpSetException e) {
			throw new PException("No se puede inicializar la ayuda", e);
		} catch (MalformedURLException e) {
			throw new PException("No se puede inicializar la ayuda", e);
		}
	}

	/*
	 * Muestra la ventana de <b>Acerca De...</b> de la aplicaci�n.
	 * @param imagen La im�gen a mostrar en la ventana.
	 * @param titulo El t�tulo de la ventana.
	 * @param version El nro. de versi�n de la aplicaci�n.
	 * @param versionJava Si es true muestra la versi�n del JDK instalado.
	 */
	private void verAboutScreen(String imagen, String titulo, String version) {
		if(aboutScreen == null) {
			aboutScreen = new PAboutScreen(imagen, titulo, "Versión " + version);
			aboutScreen.displayJavaVersion();
		}
		aboutScreen.setVisible(true);
	}

}