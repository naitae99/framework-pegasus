package ar.pegasus.framework.templates;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ar.pegasus.framework.util.EtiquetasBotones;

public abstract class PanelBotones extends JPanel {
	private static final long serialVersionUID = -1992908338983730231L;

	private JButton btnAceptar, btnCancelar, btnSalir;  
	
	
	public PanelBotones(){
		construct();
	}
	
	protected void construct(){
		setLayout(new GridBagLayout());
		// Boton Aceptar
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 15);
		gridBagConstraints.weightx = .5;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		add(getBtnAceptar(), gridBagConstraints);
		// Boton Cancelar
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.weightx = .5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 2;
		add(getBtnCancelar(), gridBagConstraints);
		// Boton Salir
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 3;
		add(getBtnSalir(), gridBagConstraints);
	}

	/**
	 * Getters/Setters
	 */
	protected JButton getBtnAceptar() {
		if(btnAceptar == null){
			btnAceptar = new JButton();
			btnAceptar.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					botonAceptarPresionado();
					
				}
			});
			btnAceptar.setText(EtiquetasBotones.ACEPTAR);
			btnAceptar.setPreferredSize(new Dimension(100, 20));			
		}
		return btnAceptar;
	}

	protected JButton getBtnCancelar() {
		if(btnCancelar == null){
			btnCancelar = new JButton();
			btnCancelar.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					botonCancelarPresionado();
					
				}
			});
			btnCancelar.setText(EtiquetasBotones.CANCELAR);
			btnCancelar.setPreferredSize(new Dimension(100, 20));
		}
		return btnCancelar;
	}

	

	protected JButton getBtnSalir() {
		if(btnSalir == null){
			btnSalir = new JButton();
			btnSalir.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					botonSalirPresionado();
					
				}
			});
			btnSalir.setText(EtiquetasBotones.SALIR_ARROW);
			btnSalir.setPreferredSize(new Dimension(100, 20));
		}
		return btnSalir;
	}

	protected void setEnabledControlesDatos(boolean enabled) {
		getBtnAceptar().setEnabled(enabled);
		getBtnCancelar().setEnabled(enabled);
	}
	
	protected abstract void botonAceptarPresionado();
	
	protected abstract void botonCancelarPresionado();
	
	protected abstract void botonSalirPresionado();
}
