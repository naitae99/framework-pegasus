package ar.pegasus.framework.componentes;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ar.pegasus.framework.boss.BossEstilos;

/**
 * Componente que extiende de javax.swing.JPanel con métodos útiles para agregarle
 * los componentes más utilizados (JButton, CLJTable, etc.).
 * @author oarias
 */
public class GuiPanel extends JPanel {
	private static final long serialVersionUID = 8086516516834316871L;
	private int x;
    private int y;
    private int ancho;
    private int alto;

    /** Método constructor */
    public GuiPanel() {
        super(null);
        this.x = 0;
        this.y = 0;
        this.ancho = 0;
        this.alto = 0;
    }

    /**
     * M�todo constructor.
     * @param x La coordenada X de la ubicaci�n del componente.
     * @param y La coordenada Y de la ubicaci�n del componente.
     * @param ancho El ancho en pixeles del componente.
     * @param alto El alto en pixeles del componente.
     */
    public GuiPanel(int x, int y, int ancho, int alto) {
        super(null);
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        setBounds(x, y, ancho, alto);
    }

    /**
     * M�todo constructor.
     * @param ancho El ancho en pixeles del componente.
     * @param alto El alto en pixeles del componente.
     */
    public GuiPanel(int ancho, int alto) {
        super(null);
        this.x = 0;
        this.y = 0;
        this.ancho = ancho;
        this.alto = alto;
        setBounds(x, y, ancho, alto);
    }

    /**
     * M�todo para agregar botones al componente.
     * @param button El bot�n a agregar al componente.
     * @param x La coordenada X de la ubicaci�n del bot�n en el componente.
     * @param y La coordenada Y de la ubicaci�n del bot�n en el componente.
     */
    public void add(JButton button, int x, int y) {
        button.setBounds(x, y, 90, 20);
        BossEstilos.decorateButton(button);
        add(button);
    }

    /**
     * M�todo para agregar botones al componente.
     * @param button El bot�n a agregar al componente.
     * @param x La coordenada X de la ubicaci�n del bot�n en el componente.
     * @param y La coordenada Y de la ubicaci�n del bot�n en el componente.
     * @param ancho El ancho del bot�n en pixeles.
     * @param alto El alto del bot�n en pixeles.
     */
    public void add(JButton button, int x, int y, int ancho, int alto) {
        button.setBounds(x, y, ancho, alto);
        BossEstilos.decorateButton(button);
        add(button);
    }

    /**
     * M�todo para agregar un CLBotonCalendario al componente.
     * @param botonCalendario El CLBotonCalendario a agregar.
     * @param x La coordenada X de la ubicaci�n del bot�n en el componente.
     * @param y La coordenada Y de la ubicaci�n del bot�n en el componente.
     * @see ar.pegasus.framework.componentes.PBotonCalendario
     */
    public void add(PBotonCalendario botonCalendario, int x, int y) {
        botonCalendario.setBounds(x, y, botonCalendario.DEFAULT_BUTTON_WIDTH, botonCalendario.DEFAULT_BUTTON_HEIGHT);
        add(botonCalendario);
    }

    /**
     * M�todo para agregar un JLabel al componente.
     * @param label EL objeto JLabel a agregar.
     * @param x La coordenada X de la ubicaci�n de la etiqueta en el componente.
     * @param y La coordenada Y de la ubicaci�n de la etiqueta en el componente.
     */
    public void add(JLabel label, int x, int y) {
        label.setBounds(x, y, ancho, 20);
        label.setFont(BossEstilos.getDefaultFont());
        add(label);
    }

    /**
     * M�todo para agregar un JLabel al componente.
     * @param label EL objeto JLabel a agregar.
     * @param x La coordenada X de la ubicaci�n de la etiqueta en el componente.
     * @param y La coordenada Y de la ubicaci�n de la etiqueta en el componente.
     * @param ancho El ancho en pixeles de la etiqueta.
     * @param alto El alto en pixeles de la etiqueta.
     */
    public void add(JLabel label, int x, int y, int ancho, int alto) {
        label.setBounds(x, y, ancho, alto);
        label.setFont(BossEstilos.getDefaultFont());
        add(label);
    }

    /**
     * M�todo para agregar un JTextField al componente.
     * @param label EL objeto JTextField a agregar.
     * @param x La coordenada X de la ubicaci�n del JTextField en el componente.
     * @param y La coordenada Y de la ubicaci�n del JTextField en el componente.
     * @param ancho El ancho en pixeles del JTextField.
     */
    public void add(JTextField textField, int x, int y, int ancho) {
        textField.setBounds(x, y, ancho, 20);
        textField.setFont(BossEstilos.getSecondaryFont());
        add(textField);
    }

    /**
     * M�todo para agregar un JComboBox al componente.
     * @param label EL objeto JComboBox a agregar.
     * @param x La coordenada X de la ubicaci�n del JComboBox en el componente.
     * @param y La coordenada Y de la ubicaci�n del JComboBox en el componente.
     * @param ancho El ancho en pixeles del JComboBox.
     */
    public void add(JComboBox comboBox, int x, int y, int ancho) {
        comboBox.setBounds(x, y, ancho, 20);
        comboBox.setFont(BossEstilos.getSecondaryFont());
        add(comboBox);
    }

    /**
     * M�todo para agregar un JCheckBox al componente.
     * @param label EL objeto JCheckBox a agregar.
     * @param x La coordenada X de la ubicaci�n del JCheckBox en el componente.
     * @param y La coordenada Y de la ubicaci�n del JCheckBox en el componente.
     * @param ancho El ancho en pixeles del JCheckBox.
     */
    public void add(JCheckBox checkBox, int x, int y, int ancho) {
        checkBox.setBounds(x, y, ancho, 20);
        checkBox.setFont(BossEstilos.getDefaultFont());
        add(checkBox);
    }

    /**
     * M�todo para agregar un JRadioButton al componente.
     * @param label EL objeto JRadioButton a agregar.
     * @param x La coordenada X de la ubicaci�n del JRadioButton en el componente.
     * @param y La coordenada Y de la ubicaci�n del JRadioButton en el componente.
     * @param ancho El ancho en pixeles del JRadioButton.
     */
    public void add(JRadioButton checkBox, int x, int y, int ancho) {
        checkBox.setBounds(x, y, ancho, 20);
        checkBox.setFont(BossEstilos.getDefaultFont());
        add(checkBox);
    }

    /**
     * M�todo para agregar un GuiPanel al componente.
     * @param label EL objeto GuiPanel a agregar.
     * @param x La coordenada X de la ubicaci�n del GuiPanel en el componente.
     * @param y La coordenada Y de la ubicaci�n del GuiPanel en el componente.
     */
    public void add(GuiPanel panel, int x, int y) {
        panel.setFont(BossEstilos.getSecondaryFont());
        panel.setBounds(x, y, panel.ancho, panel.alto);
        add((JPanel)panel);
    }

    /**
     * M�todo para agregar un JComponent al componente.
     * @param label EL objeto JComponent a agregar.
     * @param x La coordenada X de la ubicaci�n del JComponent en el componente.
     * @param y La coordenada Y de la ubicaci�n del JComponent en el componente.
     * @param ancho El ancho en pixeles del JComponent.
     * @param alto El ancho en pixeles del JComponent.
     */
    public void add(JComponent component, int x, int y, int ancho, int alto) {
        component.setBounds(x, y, ancho, alto);
        component.setFont(BossEstilos.getSecondaryFont());
        add(component);
    }

    /**
     * M�todo para agregar un GuiPanelScrollable al componente.
     * @param label EL objeto GuiPanelScrollable a agregar.
     * @param x La coordenada X de la ubicaci�n del GuiPanelScrollable en el componente.
     * @param y La coordenada Y de la ubicaci�n del GuiPanelScrollable en el componente.
     * @param ancho El ancho en pixeles del GuiPanelScrollable.
     * @param alto El ancho en pixeles del GuiPanelScrollable.
     * @see ar.pegasus.framework.componentes.GuiPanelScrollable
     */
    public void addConScroll(GuiPanelScrollable panel, int x, int y, int ancho, int alto) {
        panel.setFont(BossEstilos.getSecondaryFont());
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(x, y, ancho, alto);
        add(scrollPane);
    }

    /**
     * M�todo para agregar un JTextArea al componente con scroll horizontal y
     * vertical.
     * @param label EL objeto JTextArea a agregar.
     * @param x La coordenada X de la ubicaci�n del JTextArea en el componente.
     * @param y La coordenada Y de la ubicaci�n del JTextArea en el componente.
     * @param ancho El ancho en pixeles del JTextArea.
     * @param alto El ancho en pixeles del JTextArea.
     */
    public void addConScroll(JTextArea textArea, int x, int y, int ancho, int alto) {
        textArea.setFont(BossEstilos.getSecondaryFont());
        textArea.addKeyListener(new TextAreaTabListener());
        JScrollPane scrollPane;
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(x, y, ancho, alto);
        add(scrollPane);
    }

    /**
     * Método para agregar un PCheckBoxList al componente con scroll horizontal y
     * vertical.
     * @param label EL objeto PCheckBoxList a agregar.
     * @param x La coordenada X de la ubicaci�n del PCheckBoxList en el componente.
     * @param y La coordenada Y de la ubicaci�n del PCheckBoxList en el componente.
     * @param ancho El ancho en pixeles del PCheckBoxList.
     * @param alto El ancho en pixeles del PCheckBoxList.
     * @see ar.pegasus.framework.componentes.PCheckBoxList
     */
	public void addConScroll(@SuppressWarnings("rawtypes") PCheckBoxList chkList, int x, int y, int ancho, int alto) {
        JScrollPane scrollPane = new JScrollPane(chkList);
        scrollPane.setBounds(x, y, ancho, alto);
        add(scrollPane);
    }

    /**
     * Metodo para agregar un PJTable al componente con scroll horizontal y
     * vertical.
     * @param label EL objeto PJTable a agregar.
     * @param x La coordenada X de la ubicacion del PJTable en el componente.
     * @param y La coordenada Y de la ubicacion del PJTable en el componente.
     * @param ancho El ancho en pixeles del PJTable.
     * @param alto El ancho en pixeles del PJTable.
     * @see ar.pegasus.framework.componentes.PJTable
     */
    public void addConScroll(PJTable table, int x, int y, int ancho, int alto) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(x, y, ancho, alto);
        add(scrollPane);
    }

    /**
     * M�todo para agregar un JComponent al componente con scroll horizontal y
     * vertical.
     * @param label EL objeto JComponent a agregar.
     * @param x La coordenada X de la ubicaci�n del JComponent en el componente.
     * @param y La coordenada Y de la ubicaci�n del JComponent en el componente.
     * @param ancho El ancho en pixeles del JComponent.
     * @param alto El ancho en pixeles del JComponent.
     */
    public void addConScroll(JComponent component, int x, int y, int ancho, int alto) {
        component.setFont(BossEstilos.getSecondaryFont());
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBounds(x, y, ancho, alto);
        add(scrollPane);
    }

}