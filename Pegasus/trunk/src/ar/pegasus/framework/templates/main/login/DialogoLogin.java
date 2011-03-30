package ar.pegasus.framework.templates.main.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaElementos;


public class DialogoLogin extends JDialog {
	private static final long serialVersionUID = -8496563414088956629L;

	private String usuario;
	private String password;
	private JLabel lblLogo;
	private JTextField txtUsuario;
	private JTextField txtPassword;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private boolean cancelado;
	private static final int ANCHO_DEFAULT = 300;
	private static final int ALTO_DEFAULT = 190;

	/**
	 * Metodo constructor.
	 * @param owner La ventana padre del cuadro de dialogo.
	 */
	public DialogoLogin(Frame owner) {
		super(owner, true);
		construirDialogo();
	}

	/**
	 * M�todo constructor.
	 * @param owner La ventana padre del cuadro de di�logo.
	 * @param usuario Un nombre de usuario para mostrar por defecto.
	 */
	public DialogoLogin(Frame owner, String usuario) {
		this(owner);
		txtUsuario.setText(usuario);
		txtUsuario.selectAll();
	}

	/**
	 * Devuelve el <b>nombre de usuario</b>.
	 * @return usuario El nombre de usuario ingresado.
	 */
	public String getUsuario() {
	    return usuario;
	}

	/**
	 * Setea el <b>nombre de usuario</b>.
	 * @param usuario El nombre de usuario ingresado.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
		txtUsuario.setText(usuario);
	}

	/**
	 * Devuelve el <b>password</b>.
	 * @return password La contraseña del usuario ingresada.
	 */
	public String getPassword() {
	    return password;
	}

	/**
	 * Setea el <b>password</b>.
	 * @param password La contraseña del usuario.
	 */
	public void setPassword(String password) {
		this.password = password;
		txtPassword.setText(password);
	}

	/**
	 * Setea una im�gen para mostrar en el cuadro de di�logo.
	 * @param logo La ruta completa de la im�gen a mostrar.
	 */
	public void setLogo(String logo) {
		setLogo(ImageUtil.loadIcon(logo));
	}

	/**
	 * Setea una im�gen para mostrar en el cuadro de di�logo.
	 * @param logo La im�gen a mostrar.
	 */
	public void setLogo(Icon logo) {
		lblLogo.setIcon(logo);
	}

	/**
	 * Devuelve <b>true</b> si se cancel� la autentificaci�n.
	 * @return cancelado
	 */
	public boolean isCancelado() {
		return cancelado;
	}

	/* Construye graficamente el cuadro de dialogo */
	private void construirDialogo() {
		setSize(new Dimension(ANCHO_DEFAULT, ALTO_DEFAULT));
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle(EtiquetasBotones.AUTENTICACION);
		getContentPane().setLayout(new BorderLayout());
		lblLogo = new JLabel(ImageUtil.loadIcon(RutaElementos.DEFAULT_LOGO));
		getContentPane().add(lblLogo, BorderLayout.WEST);
		JPanel panLogin = new JPanel(null);
		getContentPane().add(panLogin, BorderLayout.CENTER);
		//Usuario
		JLabel lblUsuario = BossEstilos.createLabel(EtiquetasBotones.USUARIO);
		panLogin.add(lblUsuario);
		lblUsuario.setBounds(0, 20, 70, 20);
		txtUsuario = new JTextField();
		panLogin.add(txtUsuario);
		txtUsuario.setBounds(80, 20, 150, 20);
		//Password
		JLabel lblPassword = BossEstilos.createLabel(EtiquetasBotones.CONTRASENIA);
		panLogin.add(lblPassword);
		lblPassword.setBounds(0, 70, 80, 20);
		txtPassword = new JPasswordField();
		panLogin.add(txtPassword);
		txtPassword.setBounds(80, 70, 150, 20);
		txtPassword.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(evt.getKeyChar() == KeyEvent.VK_ENTER)
					btnAceptar.doClick();
			}
		});
		//Bot�n 'Aceptar'
		btnAceptar = BossEstilos.createButton(EtiquetasBotones.ACEPTAR);
		panLogin.add(btnAceptar);
		btnAceptar.setBounds(15, 120, 90, 20);
		btnAceptar.addActionListener(new BtnAceptarListener());
		//Bot�n 'Cancelar'
		btnCancelar = BossEstilos.createButton(EtiquetasBotones.CANCELAR);
		panLogin.add(btnCancelar);
		btnCancelar.setBounds(125, 120, 90, 20);
		btnCancelar.addActionListener(new BtnCancelarListener());
		//Centra el componente en la pantalla
		GuiUtil.centrar(this);
	}

	/** Clase listener del boton 'Aceptar' */
	class BtnAceptarListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			usuario = txtUsuario.getText().trim();
			password = txtPassword.getText().trim();
			cancelado = false;
			dispose();
		}
	}

	/** Clase listener del boton 'Cancelar' */
	class BtnCancelarListener implements ActionListener {
	    public void actionPerformed(ActionEvent evt) {
	    	usuario = null;
	    	password = null;
	    	cancelado = true;
	        dispose();
	    }
	}

}