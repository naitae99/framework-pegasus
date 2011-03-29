package ar.pegasus.framework.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import ar.pegasus.framework.templates.main.AbstractMainTemplate;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.ImageUtil;
/**
 * Componente que representa la ventana <b>Acerca De...</b> de una aplicaci�n.
 * Ej.:
 * CLAboutScreen aboutScreen = new CLAboutScreen("C:/imagen.png", "Mi Aplicaci�n", "Versi�n 1.0");
 * aboutScreen.setVisible(true);
 * @author oarias
 */
public class PAboutScreen extends JWindow {
	private static final long serialVersionUID = -1064388611481360411L;

	private Icon imagen;
    private JLabel labelImagen;
    private JPanel panelTexto;
    private JLabel labelTexto;
    private JLabel labelVersion;
    private JLabel labelJavaVersion;
    private String texto;
    private String version;
    private Font font;

    /**
     * M�todo constructor.
     * @param imagen La im�gen que mostrar� la ventana.
     */
    public PAboutScreen(String imagen) {
    	super(AbstractMainTemplate.getFrameInstance());
        this.imagen = ImageUtil.loadIcon(imagen);
        construct();
    }

    /**
     * M�todo constructor.
     * @param imagen La im�gen que mostrar� la ventana.
     * @param texto El texto que mostrar� la ventana.
     * @param version El nro. de versi�n de la aplicaci�n.
     */
    public PAboutScreen(String imagen, String texto, String version) {
    	super(AbstractMainTemplate.getFrameInstance());
    	if(imagen != null) {
    		this.imagen = ImageUtil.loadIcon(imagen);
    	}
		this.texto = texto;
		this.version = version;
		construct();
    }

	//Construye el componente
    private void construct() {
		JPanel cp = new JPanel();
		setContentPane(cp);
		cp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		cp.setLayout(new BorderLayout());
		try {
			labelImagen = new JLabel(imagen);
		} catch(Exception e) {
			e.printStackTrace();
		}
		cp.add(labelImagen, BorderLayout.CENTER);

        panelTexto = new JPanel(new BorderLayout(0, 0));
        cp.add(panelTexto, BorderLayout.SOUTH);
		//Text
		labelTexto = new JLabel(texto);
		labelTexto.setHorizontalAlignment(JLabel.CENTER);
		panelTexto.add(labelTexto, BorderLayout.NORTH);

		//Version
		labelVersion = new JLabel(version);
		labelVersion.setHorizontalAlignment(JLabel.CENTER);
		panelTexto.add(labelVersion, BorderLayout.CENTER);

		addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                PAboutScreen.this.dispose();
            }
		});
        labelJavaVersion = new JLabel("");
        labelJavaVersion.setHorizontalAlignment(JLabel.CENTER);
        panelTexto.add(labelJavaVersion, BorderLayout.SOUTH);
		pack();
		GuiUtil.centrar(this);
    }

	/**
	 * Retorna la <b>im�gen</b> de la ventana.
	 * @return image
	 */
	public Icon getImagen() {
		return imagen;
	}

	/**
	 * Retorna el <b>texto</b> de la ventana.
	 * @return text
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Retorna la <b>versi�n</b> de la aplicaci�n mostrada en la ventana.
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setea la <b>im�gen</b> de la ventana.
	 * @param imagen
	 */
	public void setImage(String imagen) {
		this.imagen = ImageUtil.loadIcon(imagen);
        labelImagen.setIcon(this.imagen);
	}

	/**
	 * Setea el <b>texto</b> de la ventana.
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
        labelTexto.setText(texto);
	}

	/**
	 * Setea la <b>versi�n</b> de la aplicaci�n en la ventana.
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
        labelVersion.setText(version);
	}

	/**
	 * Devuelve la tipograf�a seteada en la ventana.
	 * @return fuente
	 */
	public Font getFont() {
	    return font;
	}

	/**
	 * Setea la tipograf�a de la ventana.
	 * @param fuente
	 */
	public void setFont(Font font) {
	    this.font = font;
	    labelTexto.setFont(font);
	    labelVersion.setFont(font);
        labelJavaVersion.setFont(font);
	}

    /**
     * Agrega al texto la versi�n de Java. 
     */
    public void displayJavaVersion() {
        labelJavaVersion.setText("Java " + System.getProperty("java.version"));
        pack();
    }

}