package ar.pegasus.framework.templates.main.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.componentes.PJTable;
import ar.pegasus.framework.componentes.MDIDesktopPane;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.templates.main.skin.SkinChanger;
import ar.pegasus.framework.templates.main.skin.SkinChooserDialog;
import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.GuiUtil;

public class MenuApariencia extends PJMenu {

	private static final long serialVersionUID = -4165369169744414091L;
	private MDIDesktopPane desktop;
	private JMenuItem menuEstiloVisual;
	private JMenu menuTamanioFuente;
	private JMenu menuExportacionAExcel;
	private JRadioButtonMenuItem menuTamanioFuentePredeterminado;
	private JRadioButtonMenuItem menuTamanioFuenteGrande;
	private JCheckBoxMenuItem menuIntercalarColoresFilas;
	//Tama�o de la fuente en las tablas
	private static final int TAMANIO_FUENTE_DEFAULT = 11;
	private static final int TAMANIO_FUENTE_GRANDE = 13;

	public MenuApariencia(MDIDesktopPane desktop) {
		super(EtiquetasBotones.APARIENCIA, 'p');
		this.desktop = desktop;
		//Setea el Tama�o de Fuente
		if(System.getProperty("tamanioFuente") != null && System.getProperty("tamanioFuente").equals("Grande")) {
			getMenuTamanioFuenteGrande().setSelected(true);
			PJTable.setFontSize(TAMANIO_FUENTE_GRANDE);
		} else {
			getMenuTamanioFuentePredeterminado().setSelected(true);
			PJTable.setFontSize(TAMANIO_FUENTE_DEFAULT);
		}
		if(System.getProperty("intercalarColoresFilas") != null && System.getProperty("intercalarColoresFilas").equals(String.valueOf(true))) {
			getMenuIntercalarColoresFilas().setSelected(true);
		}
		//Estilo visual
		add(getMenuEstiloVisual());
		//Tama�o de la fuente
		add(getMenuTamanioFuente());
		//Exportaci�n a Excel
		add(getMenuExportacionAExcel());
	}

	public JMenuItem getMenuEstiloVisual() {
		if(menuEstiloVisual == null) {
			menuEstiloVisual = new JMenuItem("Cambiar Estilo Visual");
			menuEstiloVisual.addActionListener(new EstiloVisualListener());
		}
		return menuEstiloVisual;
	}

	public JMenu getMenuTamanioFuente() {
		if(menuTamanioFuente == null) {
			menuTamanioFuente = new JMenu("Tamaño de Fuente");
			ButtonGroup group = new ButtonGroup();
			menuTamanioFuente.add(getMenuTamanioFuentePredeterminado());
			group.add(getMenuTamanioFuentePredeterminado());
			menuTamanioFuente.add(getMenuTamanioFuenteGrande());
			group.add(getMenuTamanioFuenteGrande());
		}
		return menuTamanioFuente;
	}

	public JMenu  getMenuExportacionAExcel() {
		if(menuExportacionAExcel == null) {
			menuExportacionAExcel = new JMenu(EtiquetasBotones.EXPORTAR_EXCEL);
			menuExportacionAExcel.add(getMenuIntercalarColoresFilas());
		}
		return menuExportacionAExcel;
	}

	private JRadioButtonMenuItem getMenuTamanioFuentePredeterminado() {
		if(menuTamanioFuentePredeterminado == null) {
			menuTamanioFuentePredeterminado = new JRadioButtonMenuItem("Predeterminado", true);
			menuTamanioFuentePredeterminado.addActionListener(new TamanioFuenteListener());
		}
		return menuTamanioFuentePredeterminado;
	}

	private JRadioButtonMenuItem getMenuTamanioFuenteGrande() {
		if(menuTamanioFuenteGrande == null) {
			menuTamanioFuenteGrande = new JRadioButtonMenuItem("Grande");
			menuTamanioFuenteGrande.addActionListener(new TamanioFuenteListener());
		}
		return menuTamanioFuenteGrande;
	}

	private JCheckBoxMenuItem getMenuIntercalarColoresFilas() {
		if(menuIntercalarColoresFilas == null) {
			menuIntercalarColoresFilas = new JCheckBoxMenuItem("Intercalar Filas de Color");
			menuIntercalarColoresFilas.addActionListener(new IntercalarColoresFilasListener());
		}
		return menuIntercalarColoresFilas;
	}

	/** Clase listener de eventos del submen� 'Estilo Visual' */
	class EstiloVisualListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			SkinChooserDialog dialog = new SkinChooserDialog(getFrame()) {
				private static final long serialVersionUID = 6472970138923591210L;

				public void changeSkin() {
					SkinChanger changer = new SkinChanger(getSelectedSkin(), getFrame());
					try {
						changer.changeSkin();
					} catch(PException e) {
						BossError.gestionarError(e);
					}
					dispose();
				}
			};
			dialog.setSkins(BossEstilos.getSkins());
			dialog.setSelectedSkin(BossEstilos.getDefaultSkin());
			GuiUtil.centrar(dialog);
			dialog.setVisible(true);
		}
	}

	/** Clase listener de eventos del submen� 'Tama�o de fuente' */
	class TamanioFuenteListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource().equals(getMenuTamanioFuentePredeterminado())) {
				PJTable.setFontSize(TAMANIO_FUENTE_DEFAULT);
				System.setProperty("tamanioFuente", "Predeterminado");
			} else if(evt.getSource().equals(getMenuTamanioFuenteGrande())) {
				PJTable.setFontSize(TAMANIO_FUENTE_GRANDE);
				System.setProperty("tamanioFuente", "Grande");
			}
		}
	}

	/** Clase listener de eventos del submen� 'Exportaci�n a Excel' */
	class IntercalarColoresFilasListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			System.setProperty("intercalarColoresFilas", String.valueOf(((JCheckBoxMenuItem)evt.getSource()).isSelected()));
		}
	}

}