package ar.pegasus.framework.templates.main.skin;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import ar.pegasus.framework.boss.BossEstilos;

/**
 * Componente que representa un cuadro di�logo formado por:<br>
 * - Una im�gen de previsualizaci�n del skin.<br>
 * - Un combobox para seleccionar un skin entre los posibles.<br>
 * - Un bot�n de aceptar y un bot�n de cancelar.<br>
 * Util para realizar cambios de skin din�micos.
 */
public class SkinChooserDialog extends JDialog {

	private static final long serialVersionUID = 2803623877937034336L;
	private AbstractSkin[] skins;
	private JLabel previewLabel;
	private JComboBox skinComboBox;
	private JButton aplicarButton;
	private JButton cancelarButton;

	/**
	 * M�todo constructor.
	 * @param owner El frame padre del cuadro de di�logo.
	 */
	public SkinChooserDialog(Frame owner) {
		super(owner, "Estilos Visuales", true);
		constructDialog();
	}

	/* Construye graficamente el cuadro de di�logo */
	private void constructDialog() {
		setSize(450, 320);
		getContentPane().setLayout(new GridBagLayout());

		//Preview
		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(new TitledBorder(null, "Previsualizaci�n", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.gridx = 0;
		getContentPane().add(previewPanel, constraints);
		previewLabel = new JLabel();
		previewLabel.setBorder((Border)UIManager.get("TextField.border"));
		previewLabel.setHorizontalAlignment(JLabel.CENTER);
		previewLabel.setPreferredSize(new Dimension(400, 150));
		previewLabel.setMinimumSize(new Dimension(400, 150));
		previewPanel.add(previewLabel);

		//Estilo Visual
		JLabel estiloVisualLabel = BossEstilos.createLabel("Estilo Visual:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 10, 5, 5);
		constraints.gridy = 1;
		constraints.gridx = 0;
		getContentPane().add(estiloVisualLabel, constraints);
		skinComboBox = new JComboBox();
		skinComboBox.addItemListener(new SkinSelectedListener());
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 10, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;
		constraints.gridx = 1;
		getContentPane().add(skinComboBox, constraints);

		//Botones
		JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 0, 10, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridy = 2;
		constraints.gridx = 0;
		getContentPane().add(botonesPanel, constraints);
		//Aplicar
		aplicarButton = BossEstilos.createButton("Aplicar");
		aplicarButton.setPreferredSize(new Dimension(100, 20));
		aplicarButton.addActionListener(new AplicarActionListener());
		botonesPanel.add(aplicarButton);
		cancelarButton = BossEstilos.createButton("Cancelar");
		cancelarButton.setPreferredSize(new Dimension(100, 20));
		cancelarButton.addActionListener(new CancelarActionListener());
		botonesPanel.add(cancelarButton);
	}

	/**
	 * @return Devuelve los skins disponibles para aplicar.
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public AbstractSkin[] getSkins() {
		return skins;
	}

	/**
	 * Setea los skins disponibles para aplicar.
	 * @param skins Un array con los skins.
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public void setSkins(AbstractSkin[] skins) {
		for(AbstractSkin skin : skins) {
			skinComboBox.addItem(skin);
		}
	}

	/**
	 * @return El skin seleccionado.
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public AbstractSkin getSelectedSkin() {
		return (AbstractSkin)skinComboBox.getSelectedItem();
	}

	/**
	 * Selecciona un skin en el combobox.
	 * @param skin
	 * @see ar.pegasus.framework.templates.main.skin.AbstractSkin
	 */
	public void setSelectedSkin(AbstractSkin skin) {
		skinComboBox.setSelectedItem(skin);
	}

	/**
	 * Método disponible para ser sobreescrito, y definir la lógica de cambio de skin.
	 */
	public void changeSkin() {
	}

	/* Listener de selecci�n de items del combobox */
	class SkinSelectedListener implements ItemListener {
		public void itemStateChanged(ItemEvent evt) {
			AbstractSkin skin = (AbstractSkin)skinComboBox.getSelectedItem();
			previewLabel.setIcon(skin.getPreview());
		}
	}

	/* Listener del botón 'Aplicar' */
	class AplicarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			changeSkin();
		}
	}

	/* Listener del botón 'Cancelar' */
	class CancelarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			dispose();
		}
	}

}