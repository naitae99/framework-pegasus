package ar.pegasus.framework.componentes;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.util.GridBagUtil;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.ImageUtil;

public class PProgressDialog extends JDialog {
	private static final long serialVersionUID = 2748539867159361789L;

	private int taskLength;
	private String icon;
	private boolean showProgress;
	private IndicadorProgreso indicador;
	public MonitorIndicadorProgreso monitor;
	private JProgressBar progressBar;
	private JLabel msgLabel;
	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT = 100;

	public static PProgressDialog createDialog(Frame owner, int taskLength) {
		return createDialog(owner, taskLength, null);
	}

	public static PProgressDialog createDialog(Frame owner, int taskLength, String icon) {
		return createDialog(owner, taskLength, icon, true);
	}

	public static PProgressDialog createDialog(Frame owner, int taskLength, String icon, boolean showProgress) {
		JDialog.setDefaultLookAndFeelDecorated(false);
		return new PProgressDialog(owner, taskLength, icon, showProgress);
	}

	public static PProgressDialog createDialog(JDialog owner, int taskLength, String icon, boolean showProgress) {
		JDialog.setDefaultLookAndFeelDecorated(false);
		return new PProgressDialog(owner, taskLength, icon, showProgress);
	}

	private PProgressDialog(Frame owner, int taskLength, String icon, boolean showProgress) {
		super(owner, true);
		this.taskLength = taskLength;
		this.icon = icon;
		this.showProgress = showProgress;
		constructDialog();
		if(showProgress) {
			indicador = new IndicadorProgreso(progressBar);
			monitor = new MonitorIndicadorProgreso(indicador, 1);
			monitor.start();
		}
	}

	private PProgressDialog(JDialog owner, int taskLength, String icon, boolean showProgress) {
		super(owner, true);
		this.taskLength = taskLength;
		this.icon = icon;
		this.showProgress = showProgress;
		constructDialog();
		if(showProgress) {
			indicador = new IndicadorProgreso(progressBar);
			monitor = new MonitorIndicadorProgreso(indicador, 1);
			monitor.start();
		}
	}
	
	private void constructDialog() {
		setUndecorated(true);
		setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		getContentPane().add(panel);
		//Header
		if(icon != null) {
			JLabel headerLabel = new JLabel(ImageUtil.loadIcon(PProgressDialog.class, icon), JLabel.CENTER);
			panel.add(headerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, CENTER, NONE, new Insets(5, 5, 5, 5), 0, 0));
		}
		//Msg Label
		msgLabel = BossEstilos.createLabel(null);
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(msgLabel, GridBagUtil.createSoftHorizontalContraints(0, 1, 1, 1, 1.0));
		//Progress Bar
		if(showProgress) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			panel.add(progressBar, GridBagUtil.createSoftHorizontalContraints(0, 2, 1, 1, 1.0));
		}
		if(showProgress) {
			addKeyListener(new EscKeyListener());
		}
		GuiUtil.centrar(this);
	}

	public void setMessage(String msg) {
		msgLabel.setText(msg);
	}

	public void setReady() {
		if(showProgress) {
			indicador.setReady(0, taskLength);
		}
	}

	public void setFinished(boolean finished) {
		if(showProgress) {
			indicador.setFinished(finished);
			if(finished) {
				dispose();
			}
		}
	}

	public void setCurrent(int current) {
		if(showProgress) {
			indicador.setValorActual(current);
		}
	}

	public IndicadorProgreso getIndicadorProgreso() {
		return indicador;
	}

	public MonitorIndicadorProgreso getMonitor() {
		return monitor;
	}

	@Override
	public void dispose() {
		super.dispose();
		JDialog.setDefaultLookAndFeelDecorated(true);
	}

	class EscKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent evt) {
			if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				indicador.setFinished(true);
			}
		}
	}

}