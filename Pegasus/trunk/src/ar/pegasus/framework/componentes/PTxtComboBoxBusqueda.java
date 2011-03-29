package ar.pegasus.framework.componentes;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.ImageUtil;

/**
 * Componente que permite la busqueda de elementos del tipo T en base al texto ingresado en el TextField (Enter).
 * Si la b�squeda devuelve elementos, el TextField es reemplazado por un ComboBox con los elementos devueltos, de
 * los cuales puede seleccionarse uno. Si se desea puede volverse al modo b�squeda (BackSpace).
 * 
 * @param <T> El tipo de elemento a buscar/seleccionar.
 */
@SuppressWarnings("serial")
public abstract class PTxtComboBoxBusqueda<T> extends JPanel {

	protected JComboBox cmbResultados;

	protected JTextField searchField;

	protected JButton searchBtn;

	protected JButton backButton;

	protected boolean modoResult;

	private Action backAction = new BackAction();

	private Action searchAction = new SearchAction();

	/**
	 * Crea el componente.
	 * 
	 * @param fieldWidth el ancho del campo de texto y del combo (ambos tienen el mismo ancho).
	 */
	public PTxtComboBoxBusqueda() {
		super(new CardLayout());
		add(createSearchPanel(), "SEARCH");
		add(createResultPanel(), "RESULT");
	}

	protected abstract List<T> buscar(String text) throws PException;

	/**
	 * M�todo para sobreescritura; esta implementaci�n devuelve siempre true.
	 * 
	 * @param text el texto que se utilizar� para buscar.
	 * @return True si se debe realizar la busqueda.
	 */
	protected boolean realizarBusqueda(String text) {
		return true;
	}

	/**
	 * Registra el listener como interesado en la acci�n. Se notificar� al listener cuando se seleccione un item
	 * del combo.
	 * 
	 * @param listener el listener interesado en la acci�n.
	 */
	public void addActionListener(ActionListener listener) {
		cmbResultados.addActionListener(listener);
	}

	/**
	 * Saca al listener que estaba registrado como interesado en la acci�n.
	 * 
	 * @param listener el listener interesado en la acci�n.
	 */
	public void removeActionListener(ActionListener listener) {
		cmbResultados.removeActionListener(listener);
	}

	/**
	 * @return el item seleccionado.
	 */
	@SuppressWarnings("unchecked")
	public T getItemSeleccionado() {
		if(!isModoResult())
			return null;
		return (T)cmbResultados.getSelectedItem();
	}

	/**
	 * Establece el elemento que se encuentra seleccionado
	 * 
	 * @param item Item que se encuentra seleccionado
	 */
	public void setItemSeleccionado(T item) {
		cmbResultados.removeAllItems();
		if(item != null) {
			cmbResultados.addItem(item);
			cmbResultados.setSelectedIndex(0);
		}
		switchToResultMode();
	}

	/**
	 * @return True si hay un item seleccionado.
	 */
	public boolean isItemSelected() {
		if(!isModoResult())
			return false;
		return cmbResultados.getSelectedIndex() != -1;
	}

	private JPanel createSearchPanel() {
		JPanel searchPanel = new JPanel(new GridBagLayout());
		searchPanel.add(createSearchField(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
				0, 0), 0, 0));
		searchBtn = createButton(searchAction);
		searchPanel.add(searchBtn, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
		return searchPanel;
	}

	private JPanel createResultPanel() {
		JPanel resultPanel = new JPanel(new GridBagLayout());
		resultPanel.add(createCmbResultados(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,
				0, 0, 0), 0, 0));
		backButton = createButton(backAction);
		resultPanel.add(backButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
		return resultPanel;
	}

	private JButton createButton(Action action) {
		JButton btn = new JButton(action);
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		btn.setFocusPainted(false);
		btn.setPreferredSize(new Dimension(btn.getIcon().getIconWidth(), btn.getIcon().getIconHeight()));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonPressed();
			}
		});
		return btn;
	}
	
	public void backButtonPressed() {
	}
	

	private JTextField createSearchField() {
		searchField = new PJTextField();
		searchField.addActionListener(new SearchAction());
		return searchField;
	}

	private JComboBox createCmbResultados() {
		cmbResultados = new JComboBox();
		cmbResultados.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					backAction.actionPerformed(null);
				}
			}
		});
		return cmbResultados;
	}

	private void switchToSearchMode() {
		((CardLayout)getLayout()).show(this, "SEARCH");
		if(searchField.isEnabled())
			searchField.requestFocus();
		this.validate();
		habilitarBusqueda();
		setModoResult(false);
	}

	protected void switchToResultMode() {
		((CardLayout)getLayout()).show(this, "RESULT");
		if(cmbResultados.isEnabled())
			cmbResultados.requestFocus();
		this.validate();
		setModoResult(true);
	}

	/**
	 * @return el icono del boton 'buscar'.
	 */
	protected Icon createSearchIcon() {
		return ImageUtil.loadIcon("ar/pegasus/framework/imagenes/b_buscar.png");
	}

	class SearchAction extends AbstractAction {

		public SearchAction() {
			super("", createSearchIcon());
		}

		public void actionPerformed(ActionEvent event) {
			final String text = searchField.getText();
			if(realizarBusqueda(text)) {
				habilitarBusqueda(false);
				final Thread searchThread = new Thread(new Runnable() {

					public void run() {
						List<T> resultados;
						try {
							resultados = buscar(text);
							if(resultados != null && !resultados.isEmpty()) {
								mostrarResultados(resultados);
								hayResultado();
							} else {
								noHayResultado();
							}
						} catch(PException e) {
							e.setMensajeContexto("No se pudo efectuar la b�squeda.");
							BossError.gestionarError(e);
						}
						habilitarBusqueda(true);
					}
				});
				searchThread.start();
			}
			searchField.setText("");
		}

		private void mostrarResultados(List<T> resultados) {
			switchToResultMode();
			GuiUtil.llenarCombo(cmbResultados, resultados, true);
		}

		private void habilitarBusqueda(boolean habilitar) {
			searchField.setEnabled(habilitar);
			searchBtn.setEnabled(habilitar);
			if(habilitar) {
				searchField.requestFocus();
			}
		}
	}

	class BackAction extends AbstractAction {

		public BackAction() {
			super("", ImageUtil.loadIcon("ar/pegasus/framework/imagenes/b_izquierda.png"));
		}

		public void actionPerformed(ActionEvent event) {
			switchToSearchMode();
			
		}
	}

	public void noHayResultado() {
	}
	
	public void hayResultado() {
	}
	//habilitar y deshabilitar botones en busqueda
	public void habilitarBusqueda(){
		
	}
	public boolean isModoResult() {
		return modoResult;
	}

	protected void setModoResult(boolean modoResult) {
		this.modoResult = modoResult;
	}
	
	public void reset(){
		this.switchToSearchMode();
	}

	public JComboBox getCmbResultados() {
		return cmbResultados;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public JButton getBackButton() {
		return backButton;
	}

}