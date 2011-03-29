package ar.pegasus.framework.templates;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.templates.main.AbstractMainTemplate;
import ar.pegasus.framework.util.EtiquetasBotones;

public abstract class DialogoEditarDatos<T> extends LayeredDialog {
	
	private static final long serialVersionUID = 8323015326248363325L;
	private boolean modoEdicion;
	private T elemento;
	protected boolean cambiosSinGuardar = false;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnSalir;
	private PanelBotones panelBotones;

	public DialogoEditarDatos(T elemento, boolean modoEdicion) throws PException {
		super(AbstractMainTemplate.getFrameInstance(), true);	
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isCambiosSinGuardar()) {
					int resp = PJOptionPane.showQuestionMessage(DialogoEditarDatos.this,"¿Desea salir y abandonar los cambios?", getTitle());
					if (resp == PJOptionPane.YES_OPTION) {
						setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					}
				} else {
					setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				}
			}
			
		});
		setModoEdicion(modoEdicion);
		setElemento(elemento);
		preconstruct();
		construct();
		cargarDatos();
	}

	/**
	 * Validaciones y seteos previos al construir una ventana
	 */
	protected void preconstruct() {
		
	}

	/**
	 * Se encarga de construir el panel con las acciones a realizar en la ventana.
	 * 
	 * @return Panel de botones Creados
	 */
	protected PanelBotones getPanelBotones() {
		if(panelBotones == null){
			panelBotones = new PanelBotones(){
				private static final long serialVersionUID = 8570032301143130148L;

				@Override
				protected void botonAceptarPresionado() {
					// Validar los datos ingresados
					if (!validarDatos())
						return;
					setCambiosSinGuardar(false);
					// capturar los datos de la nota
					capturarDatos();
					// Deshabilitar los controles
					setEnabledControlesDatos(false);
				}

				@Override
				protected void botonCancelarPresionado() {
					if (isCambiosSinGuardar()) {
						int resp = PJOptionPane.showQuestionMessage(DialogoEditarDatos.this,"¿Desea abandonar los cambios?", getTitle());
						if (resp == PJOptionPane.NO_OPTION) {
							return;
						}
					}
					setCambiosSinGuardar(false);
					setContenido(getElemento());
					// Deshabilitar los controles
					setEnabledControlesDatos(false);					
				}

				@Override
				protected void botonSalirPresionado() {
					if (isCambiosSinGuardar()) {
						int resp = PJOptionPane.showQuestionMessage(DialogoEditarDatos.this,"¿Desea salir y abandonar los cambios?", getTitle());
						if (resp != PJOptionPane.YES_OPTION) {
							return;
						}
					}
					DialogoEditarDatos.this.dispose();				
				}
				
			};
		}
		return panelBotones;
	}

	/**
	 * Metodos sobrecargables
	 */
	
	protected void construct(){
		setTitle(getTitulo());
		int filaActual = -1;
		getContentPane().setLayout(new GridBagLayout());
		setBounds(getDefaultSize());
		
		/**
		 * Agregamos el panel de datos de la nota.
		 */
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 5;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.insets = new Insets(10, 10, 5, 10);
		gridBagConstraints.gridy = ++filaActual;
		gridBagConstraints.gridx = 0;
		getContentPane().add(getPanelDatos(), gridBagConstraints);
		
		/**
		 * Barra de Botones.
		 */
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 10, 10);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = ++filaActual;
		getContentPane().add(getPanelBotones(), gridBagConstraints);
	}
	
	protected Rectangle getDefaultSize() {
		return new Rectangle(75, 75, 500, 500);
	}

	protected abstract String getTitulo();

	protected abstract JPanel getPanelDatos();

	protected abstract void cargarDatos() throws PException;
	
	protected abstract boolean validarDatos();

	protected abstract void botonAceptarPresionado(); 

	protected abstract void botonCancelarPresionado(); 

	protected abstract void botonSalirPresionado();

	protected abstract void capturarDatos();

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

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public boolean isModoEdicion(){
		return modoEdicion;
	}
	
	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}
	
	protected void setEnabledControlesDatos(boolean enabled) {
		getBtnAceptar().setEnabled(enabled);
		getBtnCancelar().setEnabled(enabled);
	}
	
	public void setContenido(T contenido){
		setElemento(contenido);
		try {
			cargarDatos();
		} catch (PException e) {
			BossError.gestionarError(e);
		}
	}

	public void actualizar(boolean estado) {
		if(isModoEdicion()) {
			cambiosSinGuardar = estado;
			getBtnAceptar().setEnabled(cambiosSinGuardar);
			getBtnCancelar().setEnabled(cambiosSinGuardar);
		}
	}
	
	public void cerrarDialogo(){
		botonSalirPresionado();
	}
}
