package ar.pegasus.framework.componentes;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.pegasus.framework.boss.BossEstilos;

/**
 * Componente compuesto por dos listas y botones de agregar y sacar
 * para intercambiar items de una lista a otra.
 * @author oarias
 */
public class PDobleLista extends JPanel {
	private static final long serialVersionUID = 6452060880005641787L;
	
	private Font font;
    private JList listaOrigen;
    private TitledBorder bordeOrigen;
    private JLabel labelOrigen;
    private String tituloOrigen;
    private JList listaDestino;
    private TitledBorder bordeDestino;
    private JLabel labelDestino;
    private String tituloDestino;
    private JButton botonAgregar;
    private JButton botonQuitar;
    private boolean ordenAlfabetico = true;
    private boolean caseSensitive = false;
    private boolean constructed;
    private static final String DEFAULT_TITULO_ORIGEN = "Origen";
    private static final String DEFAULT_TITULO_DESTINO = "Destino";

	/** Método constructor */
    public PDobleLista() {
        tituloOrigen = DEFAULT_TITULO_ORIGEN;
        tituloDestino = DEFAULT_TITULO_DESTINO;
        construct();
        botonQuitar.setEnabled(false);
        botonAgregar.setEnabled(false);
    }

    /**
     * Método constructor.
     * @param tituloOrigen El título de la lista <b>Origen</b> (izquierda).
     * @param tituloDestino El título de la lista <b>Destino</b> (derecha).
     */
    public PDobleLista(String tituloOrigen, String tituloDestino) {
        this.tituloOrigen = tituloOrigen;
        this.tituloDestino = tituloDestino;
        construct();
        botonQuitar.setEnabled(false);
        botonAgregar.setEnabled(false);
    }

    private void construct() {
        setLayout(new GridLayout(1, 3));
        //Lista Origen
        add(createPanelListaOrigen());
        //Botones
        add(createPanBotonesCentral());
        //Lista Destino
        add(createPanelListaDestino());
        constructed = true;
    }

    private JScrollPane createPanelListaOrigen() {
        listaOrigen = new JList();
        JScrollPane scrollOrigen = new JScrollPane(listaOrigen);
        listaOrigen.setModel(new DefaultListModel());
        listaOrigen.addListSelectionListener(new ListaOrigenDestinoSelectionListener());
        scrollOrigen = new JScrollPane(listaOrigen);
        bordeOrigen = new TitledBorder(null, tituloOrigen, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, font);
        scrollOrigen.setBorder(bordeOrigen);
        return scrollOrigen;
    }

    private JPanel createPanBotonesCentral() {
        JPanel botones = new JPanel(new GridBagLayout());
        botonAgregar = BossEstilos.createButton(">>");
        botonAgregar.setPreferredSize(new Dimension(40, 25));
        botonAgregar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Object[] values = listaOrigen.getSelectedValues();
                for(int i = 0; i < values.length; i++) {
                    Object sel = values[i];
                    agregarItemDestino(sel);
                    borrarItemOrigen(sel);
                }
            }

        });
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        botones.add(botonAgregar, c);
        botonQuitar = BossEstilos.createButton("<<");
        botonQuitar.setPreferredSize(new Dimension(40, 25));
        botonQuitar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Object[] values = listaDestino.getSelectedValues();
                for(int i = 0; i < values.length; i++) {
                    Object sel = values[i];
                    agregarItemOriginal(sel);
                    borrarItemDestino(sel);
                }
            }

        });
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 0);
        botones.add(botonQuitar, c);
        return botones;
    }

    private JScrollPane createPanelListaDestino() {
        listaDestino = new JList();
        JScrollPane scrollDestino = new JScrollPane(listaDestino);
        listaDestino.setModel(new DefaultListModel());
        listaDestino.addListSelectionListener(new ListaOrigenDestinoSelectionListener());
        scrollDestino = new JScrollPane(listaDestino);
        bordeDestino = new javax.swing.border.TitledBorder(null, tituloDestino, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, font);
        scrollDestino.setBorder(bordeDestino);
        return scrollDestino;
    }

    /**
     * Devuelve la <b>Lista Origen</b>.
     * @return listaOrigen
     */
    public JList getListaOrigen() {
        return listaOrigen;
    }

    /**
     * Devuelve la <b>Lista Destino</b>.
     * @return listaDestino
     */
    public JList getListaDestino() {
        return listaDestino;
    }

    /**
     * Setea los items de la <b>Lista Origen</b>.
     * @param values Los valores de los items de la lista Origen.
     */
    public void setListaOriginal(List<?> values) {
        ((DefaultListModel)listaOrigen.getModel()).clear();
        for(Iterator<?> i = values.iterator(); i.hasNext();)
            agregarItemOriginal(i.next());
    }

    /**
     * Setea los items de la <b>Lista Destino</b>.
     * @param values Los valores de los items de la lista Destino.
     */
    public void setListaDestino(List<?> values) {
        DefaultListModel modeloDestino = (DefaultListModel)listaDestino.getModel();
        modeloDestino.clear();
        for(Iterator<?> i = values.iterator(); i.hasNext();) {
            Object o = i.next();
            borrarItemOrigen(o);
        }
        for(Iterator<?> i = values.iterator(); i.hasNext();)
            agregarItemDestino(i.next());
    }

    /** Resetea y restaura los valores iniciales de la Lista Origen */
    public void restaurarListaOriginal() {
        DefaultListModel modeloOriginal = (DefaultListModel)listaOrigen.getModel();
        DefaultListModel modeloDestino = (DefaultListModel)listaDestino.getModel();
        for(int i = 0; i < modeloDestino.size(); i++)
            modeloOriginal.addElement(modeloDestino.elementAt(i));
        modeloDestino.clear();
    }

    /**
     * Agrega un item a la Lista Origen.
     * @param item
     */
    public void agregarItemOriginal(Object item) {
        agregarItemLista(item, listaOrigen);
    }

    /**
     * Agrega un item a la Lista Destino.
     * @param item
     */
    public void agregarItemDestino(Object item) {
        agregarItemLista(item, listaDestino);
    }

    private void agregarItemLista(Object item, JList lista) {
        DefaultListModel modeloLista = (DefaultListModel)lista.getModel();
        if(!modeloLista.contains(item)) {
            if(ordenAlfabetico) {
                int insertionPoint = getPuntoInsercion(modeloLista, item);
                modeloLista.insertElementAt(item, insertionPoint);
            } else
                modeloLista.addElement(item);
        }
    }

    private int getPuntoInsercion(DefaultListModel modelo, Object obj) {
        for(int i = 0; i < modelo.getSize(); i++) {
            Object o = modelo.get(i);
            if (caseSensitive) {
            	if (obj.toString().compareTo(o.toString()) <= 0 )
            		return i ;
            } else {
            	if (obj.toString().compareToIgnoreCase(o.toString()) <= 0 )
            		return i ;            	
            }
        }
        return modelo.getSize();
    }

    /**
     * Borra un item de la <b>Lista Origen</b>.
     * @param item El item a borrar de la lista.
     */
    public void borrarItemOrigen(Object item) {
        borrarItemLista(item, listaOrigen);
    }

    /**
     * Borra un item de la <b>Lista Destino</b>.
     * @param item El item a borrar de la lista.
     */
    public void borrarItemDestino(Object item) {
        borrarItemLista(item, listaDestino);
    }

    /**
     * Devuelve los valores de la <b>Lista Origen</b>.
     * @return values Los valores de la lista.
     */
    public List<?> getValoresListaOrigen() {
        return getValoresLista(listaOrigen);
    }

    /**
     * Devuelve los valores de la <b>Lista Destino</b>.
     * @return values Los valores de la lista.
     */
    public List<?> getValoresListaDestino() {
        return getValoresLista(listaDestino);
    }

    private void borrarItemLista(Object item, JList lista) {
        DefaultListModel modeloLista = (DefaultListModel)lista.getModel();
        modeloLista.removeElement(item);
    }

    private List<?> getValoresLista(JList lista) {
        List<Object> valores = new ArrayList<Object>();
        int size = lista.getModel().getSize();
        if(size == 0) {
            return null;
        }
        for(int i = 0; i < size; i++) {
            valores.add(i, lista.getModel().getElementAt(i));
        }
        return valores;
    }

    /** Limpia ambas listas */
    public void vaciarListas() {
        DefaultListModel modelDestino = (DefaultListModel)listaDestino.getModel();
        DefaultListModel modelOrigen = (DefaultListModel)listaOrigen.getModel();
        modelDestino.clear();
        modelOrigen.clear();
    }

    /**
     * Devuelve el <b>t�tulo</b> de la <b>Lista Destino</b>.
     * @return tituloDestino
     */
    public String getTituloDestino() {
        return tituloDestino;
    }

    /**
     * Setea el <b>t�tulo</b> de la <b>Lista Destino</b>.
     * @param tituloDestino
     */
    public void setTituloDestino(String tituloDestino) {
        this.tituloDestino = tituloDestino;
        labelDestino.setText(tituloDestino);
    }

    /**
     * Devuelve el <b>t�tulo</b> de la <b>Lista Origen</b>.
     * @return tituloOrigen
     */
    public String getTituloOrigen() {
        return tituloOrigen;
    }

    /**
     * Setea el <b>t�tulo</b> de la <b>Lista Origen</b>.
     * @param tituloOrigen
     */
    public void setTituloOrigen(String tituloOrigen) {
        this.tituloOrigen = tituloOrigen;
        labelOrigen.setText(tituloOrigen);
    }

    /**
     * Devuelve la tipograf�a seteada al componente.
     * @return fuente La tipograf�a seteada.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Setea la tipograf�a al componente.
     * @param fuente La tipograf�a a setear.
     */
    public void setFont(Font font) {
        this.font = font;
        if(constructed) {
            bordeOrigen.setTitleFont(font);
            bordeDestino.setTitleFont(font);
            botonAgregar.setFont(font);
            botonQuitar.setFont(font);
        }
    }

    public void setOrdenAlfabetico(boolean b) {
        ordenAlfabetico = b;
    }

    public void setCaseSensitive(boolean b) {
    	caseSensitive = b;
    }

    /** Clase listener para el manejo de la habilitaci�n de los botones de agregar y sacar */
    private class ListaOrigenDestinoSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent evt) {
            //Para que el evento no se dispare dos veces
            if(!evt.getValueIsAdjusting()) {
                if(evt.getSource() == listaOrigen) {
                    botonAgregar.setEnabled(listaOrigen.getSelectedIndex() != -1);
                } else {
                    botonQuitar.setEnabled(listaDestino.getSelectedIndex() != -1);
                }
            }
        }

    }

    public JButton getBotonAgregar() {
		return botonAgregar;
	}

	public JButton getBotonQuitar() {
		return botonQuitar;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
		listaOrigen.setEnabled(enabled);
		listaDestino.setEnabled(enabled);
		if (enabled) {
			botonAgregar.setEnabled(listaOrigen.getSelectedIndex() != -1);
			botonQuitar.setEnabled(listaDestino.getSelectedIndex() != -1);
		} else {			
			botonAgregar.setEnabled(enabled);
			botonQuitar.setEnabled(enabled);
		}
	}

}