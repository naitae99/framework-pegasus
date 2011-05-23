package ar.pegasus.framework.componentes.error;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.boss.BossIdiomas;
import ar.pegasus.framework.componentes.PJTextArea;
import ar.pegasus.framework.util.DecorateUtil;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.ImageUtil;



/**
 * Componente que muestra un cuadro de di�logo con un mensaje de error y tres botones:
 * el primero para ver los detalles del error (el stack trace de la excepci�n),
 * el segundo para ver los tips o sugerencias en cuanto al error, y el tercero para
 * cerrar el cuadro de di�logo.
 * @author oarias
 */
public class PErrorDialog extends JDialog {
	private static final long serialVersionUID = -8837773570813002442L;

	public static Font fuente;
	private int X = 700;
	private int Y_1 = 160;
	private int Y_2 = 300;
	private int tipoDeError;
	private String mensajeLlamador;
	private String mensajeLlamado;
	private String llamadaParametrizada;
	private Throwable exception;
	private String[] tips;
	private JTextArea txtError = null;
	private JTextArea txtDetalle = null;
	private JTextArea txtTips = null;
	private JButton btnDetalle = null;
	private JButton btnTips = null;
	private JButton btnCopiar;
	private JPanel contentPane = null;
	private JPanel panError = null;
	private JPanel panBotones = null;
	private JPanel panDetalle = null;
	private JPanel panTips = null;
	//Iconos
	private Icon iconoErr = ImageUtil.loadIcon("ar/pegasus/framework/imagenes/error.gif");
	private JLabel lblIconoError = new JLabel(iconoErr, JLabel.CENTER);
	private Icon iconoErrCon = ImageUtil.loadIcon("ar/pegasus/framework/imagenes/error_conectividad.gif");
	private JLabel lblIconoErrCon = new JLabel(iconoErrCon, JLabel.CENTER);
	private Icon iconoErrApp = ImageUtil.loadIcon("ar/pegasus/framework/imagenes/error_aplicacion.gif");
	private JLabel lblIconoErrApp = new JLabel(iconoErrApp, JLabel.CENTER);

	/**
	 * Método constructor.
	 * @param tipoDeError El tipo de error.
	 * @param mensajeLlamador El mensaje del llamador.
	 * @param mensajeLlamado El mensaje del llamado.
	 * @param exception La excepción que causó el error.
	 * @param tips Los tips o sugerencias en cuanto al error.
	 */
	public PErrorDialog(int tipoDeError, String mensajeLlamador, String mensajeLlamado, String llamadaParametrizada, Throwable exception, String[] tips) {
		super();
		this.tipoDeError = tipoDeError;
		this.mensajeLlamador = mensajeLlamador;
		this.mensajeLlamado = mensajeLlamado;
		this.llamadaParametrizada = llamadaParametrizada;
		this.exception = exception;
		this.tips = tips;
		String title = BossIdiomas.getInstance(BossIdiomas.FW).getString("error");
		if(this.tipoDeError == BossError.ERR_APLICACION) {
			title = BossIdiomas.getInstance(BossIdiomas.FW).getString("descripcion_tipo_error_aplicacion");
		} else if(this.tipoDeError == BossError.ERR_CONEXION) {
			title = BossIdiomas.getInstance(BossIdiomas.FW).getString("descripcion_tipo_error_conexion");
		} else if(this.tipoDeError == BossError.ERR_INDETERMINADO) {
			title = BossIdiomas.getInstance(BossIdiomas.FW).getString("descripcion_tipo_error_indeterminado");
		} else if(this.tipoDeError == BossError.ERR_OPERACION) {
			title = BossIdiomas.getInstance(BossIdiomas.FW).getString("informacion");
		} else {
		}
		setTitle(title);
		/* Dialogo */
		contentPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		setContentPane(contentPane);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setSize(new Dimension(X, Y_1 + Y_2));
		GuiUtil.centrar(this);
		setSize(new Dimension(X, Y_1));
		contentPane.add(getIconoError(tipoDeError));
		contentPane.add(getTxtError());
		contentPane.add(getPanBotones());
		contentPane.add(getPanDetalle());
		contentPane.add(getPanTips());
	}

	/**
	 * Setea el ícono de <b>Error</b>.
	 * @param iconoErr
	 */
	public void setIconoError(String iconoErr) {
		this.iconoErr = ImageUtil.loadIcon(iconoErr);
	}

	/**
	 * Devuelve el ícono de <b>Error</b>.
	 * @return iconoErr
	 */
	public Icon getIconoError() {
		return iconoErr;
	}

	/**
	 * Setea el ícono de <b>Error de Aplicación</b>.
	 * @param iconoErrApp
	 */
	public void setIconoErrorAplicacion(String iconoErrApp) {
		this.iconoErrApp = ImageUtil.loadIcon(iconoErrApp);
	}

	/**
	 * Devuelve el ícono de <b>Error de Aplicación</b>.
	 * @return iconoErrApp
	 */
	public Icon getIconoErrorAplicacion() {
		return iconoErrApp;
	}

	/**
	 * Setea el ícono de <b>Error de Conectividad</b>.
	 * @param iconoErrCon
	 */
	public void setIconoErrorConectividad(String iconoErrCon) {
		this.iconoErrCon = ImageUtil.loadIcon(iconoErrCon);
	}

	/**
	 * Devuelve el ícono de <b>Error de Conectividad</b>.
	 * @return iconoErrCon
	 */
	public Icon getIconoErrorConectividad() {
		return iconoErrCon;
	}

	private Component getIconoError(int tipoDeError) {
		if(tipoDeError == BossError.ERR_APLICACION) {
			return this.lblIconoErrApp;
		} else if(tipoDeError == BossError.ERR_CONEXION) {
			return this.lblIconoErrCon;
		}
		return this.lblIconoError;
	}

	/**
	 * Devuelve el componente JTextArea que contiene el mensaje de error.
	 * @return El componente JTextArea que contiene el mensaje de error.
	 */
	public JTextArea getTxtError() {
		if(txtError == null) {
			if (mensajeLlamador != null && mensajeLlamado != null) {
				txtError = new JTextArea(mensajeLlamador + " (" + mensajeLlamado + ")");
			} else if (mensajeLlamador != null && mensajeLlamado == null) {
				txtError = new JTextArea(mensajeLlamador);
			} else if (mensajeLlamador == null && mensajeLlamado != null) {
				txtError = new JTextArea(mensajeLlamado);
			} else {
				txtError = new JTextArea(BossIdiomas.getInstance(BossIdiomas.FW).getString("mensaje_de_error_no_disponible"));
			}
			txtError.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			txtError.setPreferredSize(new Dimension(X - 30 - 30 - 10, Y_1 - 90));
			txtError.setEditable(false);
			txtError.setBackground(getContentPane().getBackground());
			txtError.setLineWrap(true);
			txtError.setWrapStyleWord(true);
		}
		return txtError;
	}

	/**
	 * Devuelve el componente JTextArea que contiene los tips o sugerencias.
	 * @return El JTextArea que contiene los tips o sugerencias.
	 */
	public JTextArea getTxtTips() {
		if(txtTips == null) {
			txtTips = new PJTextArea();
			txtTips.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			txtTips.setEditable(false);
		}
		if(tips == null) {
			txtTips.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("iniciales_no_disponible"));
		} else {
			txtTips.append(BossIdiomas.getInstance(BossIdiomas.FW).getString("causas_y_posibles_soluciones") +":\n\n");
			for(int i = 0; i < tips.length; i++) {
				txtTips.append("* " + tips[i] + "\n");
			}
		}
		return txtTips;
	}

	/**
	 * Devuelve el componente JTextArea que contiene los detalles del error.
	 * @return El JTextArea que contiene los detalles del error.
	 */
	public JTextArea getTxtDetalle() {
		if(txtDetalle == null) {
			txtDetalle = new PJTextArea();
			txtDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			txtDetalle.setEditable(false);
		}
		String text = "" ;
		if (llamadaParametrizada != null) {
			text += llamadaParametrizada + "\n" ;
		}
		if(exception != null) {
			StringWriter sw = new StringWriter();
			exception.printStackTrace(new PrintWriter(sw));
			text += sw.toString() ;
			txtDetalle.setText(sw.toString());
		}
		if (text.length() == 0) {
			txtDetalle.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("iniciales_no_disponible"));
		} else {
			txtDetalle.setText(text);
		}
		return txtDetalle;
	}

	/**
	 * Devuelve el panel que contiene los botones en el cuadro de di�logo.
	 * @return El panel que contiene los botones.
	 */
	public JPanel getPanBotones() {
		if(panBotones == null) {
			panBotones = new JPanel();
			btnTips = new JButton(BossIdiomas.getInstance(BossIdiomas.FW).getString("tips"));
			DecorateUtil.decorateButton(btnTips);
			btnTips.addActionListener(new BtnTipsActionListener());
			btnTips.setPreferredSize(new Dimension(90, 20));
			if(tips == null || tips.length == 0) {
				btnTips.setEnabled(false);
			}
			panBotones.add(btnTips);
			btnDetalle = new JButton(BossIdiomas.getInstance(BossIdiomas.FW).getString("detalles"));
			DecorateUtil.decorateButton(btnDetalle);
			btnDetalle.addActionListener(new BtnDetalleActionListener());
			btnDetalle.setPreferredSize(new Dimension(90, 20));
			panBotones.add(btnDetalle);
			JButton btnCerrar = new JButton(BossIdiomas.getInstance(BossIdiomas.FW).getString("cerrar"));
			DecorateUtil.decorateButton(btnCerrar);
			btnCerrar.addActionListener(new BtnCerrarActionListener());
			btnCerrar.setPreferredSize(new Dimension(90, 20));
			panBotones.add(btnCerrar);
		}
		return panBotones;
	}

	/**
	 * Devuelve el panel que contiene los detalles del error.
	 * @return El panel que contiene los detalles del error.
	 */
	public JPanel getPanDetalle() {
		if(panDetalle == null) {
			panDetalle = new JPanel(new BorderLayout());
			JScrollPane scrollPane = new JScrollPane(getTxtDetalle(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setPreferredSize(new Dimension(X - 30, Y_2 - 30));
			panDetalle.add(scrollPane, BorderLayout.CENTER);
			btnCopiar = new JButton(BossIdiomas.getInstance(BossIdiomas.FW).getString("copiar"));
			DecorateUtil.decorateButton(btnCopiar);
			btnCopiar.setToolTipText(BossIdiomas.getInstance(BossIdiomas.FW).getString("copiar_al_portapapeles"));
			panDetalle.add(btnCopiar, BorderLayout.SOUTH);
			btnCopiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					txtDetalle.selectAll();
					txtDetalle.copy();
				}
			});
			panDetalle.setVisible(false);
		}
		return panDetalle;
	}

	/**
	 * Devuelve el panel que contiene los tips o sugerencias.
	 * @return El panel que contiene los tips o sugerencias.
	 */
	public JPanel getPanTips() {
		if(panTips == null) {
			panTips = new JPanel(new BorderLayout());
			JScrollPane scrollPane = new JScrollPane(getTxtTips(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setPreferredSize(new Dimension(X - 30, Y_2 - 30));
			panTips.add(scrollPane, BorderLayout.CENTER);
			panTips.setVisible(false);
		}
		return panTips;
	}

	/**
	 * Devuelve el panel que contiene el mensaje de error.
	 * @return panError El panel que contiene el mensaje de error.
	 */
	public JPanel getPanError() {
		return panError;
	}

	public class BtnCerrarActionListener implements ActionListener {
		public BtnCerrarActionListener() {
			super();
		}

		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}

	public class BtnDetalleActionListener implements ActionListener {
		public BtnDetalleActionListener() {
			super();
		}

		public void actionPerformed(ActionEvent evt) {
			if(getPanDetalle().isVisible()) {
				//Se ocultan los detalles
				//dispose();
				setSize(X, Y_1);
				btnDetalle.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("detalles"));
				getPanDetalle().setVisible(false);
				setVisible(true);
			} else {
				//Se muestran los detalles
				//dispose();
				//Por si se estan mostrando los tips
				btnTips.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("tips"));
				getPanTips().setVisible(false);
				setSize(X, Y_1 + Y_2);
				btnDetalle.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("cerrar_detalles"));
				getPanDetalle().setVisible(true);
				setVisible(true);
			}
		}
	}

	public class BtnTipsActionListener implements ActionListener {
		public BtnTipsActionListener() {
			super();
		}

		public void actionPerformed(ActionEvent arg0) {
			if(getPanTips().isVisible()) {
				//Se ocultan los detalles
				//dispose();
				setSize(X, Y_1);
				btnTips.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("tips"));
				getPanTips().setVisible(false);
				setVisible(true);
			} else {
				//Se muestran los detalles
				//dispose();
				//Por si se estan mostrando los detalles
				btnDetalle.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("detalles"));
				getPanDetalle().setVisible(false);
				setSize(X, Y_1 + Y_2);
				btnTips.setText(BossIdiomas.getInstance(BossIdiomas.FW).getString("cerrar_tips"));
				getPanTips().setVisible(true);
				setVisible(true);
			}
		}
	}

}
