package ar.pegasus.framework.templates;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import ar.pegasus.framework.auditoria.BossError;
import ar.pegasus.framework.boss.AppConstantesBase;
import ar.pegasus.framework.boss.BossEstilos;
import ar.pegasus.framework.componentes.PCursor;
import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.componentes.PJOptionPane;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.util.EtiquetasBotones;
import ar.pegasus.framework.util.GuiUtil;
import ar.pegasus.framework.util.TreeUtil;

/**
 * Template para la creación de formularios con la siguiente estructura básica:
 * Un combobox en la parte superior izquierda, un selector (árbol, lista, etc.) a la
 * izquierda y un conjunto de tabs o solapas a la derecha de la pantalla.
 * @author oarias
 */
public abstract class GuiABMTemplate extends GuiForm {
	private static final long serialVersionUID = -4553282678098899167L;

	private JLabel lblComboMaestro;
    private JComboBox cmbMaestro;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JPanel panModificarGrabar;
    private JButton btnGrabar;
    private JButton btnModificar;
    private Component selector;
    private JScrollPane panSelector;
    private boolean hijoCreado;
    private boolean modoEdicion;
    private boolean permiteAgregarEliminar;
    private boolean modoConsulta;
    private boolean agregar;


    /** Método constructor */
    public GuiABMTemplate() {
        super(AppConstantesBase.ANCHO_STD_MODULOS, AppConstantesBase.ALTO_STD_MODULOS);
        //Construye gráficamente la pantalla
        construct();
        //Define el estado inicial de los componentes
        setEstadoInicialTemplate();
    }

    public GuiABMTemplate(Modulo modulo) throws PException {
    	super(AppConstantesBase.ANCHO_STD_MODULOS, AppConstantesBase.ALTO_STD_MODULOS, modulo);
    	
    	//Construye gr�ficamente la pantalla
        construct();
        //Define el estado inicial de los componentes
        setEstadoInicialTemplate();
    }

    //Construye gráficamente la pantalla
    private void construct() {
        //Propiedades de la pantalla
        getContentPane().setLayout(null);
        setIconifiable(true);
        //Panel Selector
        panSelector = new JScrollPane();
        getContentPane().add(panSelector);
        panSelector.setBounds(20, 20, 290, 620);
        //Label 'Sistema'
        lblComboMaestro = new JLabel();
        getContentPane().add(lblComboMaestro);
        lblComboMaestro.setBounds(20, 20, 80, 20);
        lblComboMaestro.setFont(BossEstilos.getDefaultFont());
        lblComboMaestro.setText("Combo Maestro");
        lblComboMaestro.setVisible(false);
        //Combobox 'Maestro'
        cmbMaestro = new JComboBox();
        getContentPane().add(cmbMaestro);
        cmbMaestro.setBounds(110, 20, 200, 20);
        cmbMaestro.setVisible(false);
        cmbMaestro.addItemListener(new ComboMaestroListener());
        //Botones Agregar/Eliminar
        btnAgregar = new JButton(EtiquetasBotones.AGREGAR_ARROW);
        btnAgregar.setMnemonic(java.awt.event.KeyEvent.VK_A);
        BossEstilos.decorateButton(btnAgregar);
        getContentPane().add(btnAgregar);
        btnAgregar.setBounds(50, 650, 90, 20);
        btnAgregar.addActionListener(new BotonAgregarListener());
        btnEliminar = new JButton(EtiquetasBotones.ELIMINAR_ARROW);
        btnEliminar.setMnemonic(java.awt.event.KeyEvent.VK_E);
        BossEstilos.decorateButton(btnEliminar);
        getContentPane().add(btnEliminar);
        btnEliminar.setBounds(190, 650, 90, 20);
        btnEliminar.addActionListener(new BotonEliminarListener());
        //Panel 'Modificar'/'Grabar'
        panModificarGrabar = new JPanel(null);
        panModificarGrabar.setBorder(new javax.swing.border.EtchedBorder());
        btnModificar = new JButton(EtiquetasBotones.MODIFICAR_ARROW);
        btnModificar.setMnemonic(java.awt.event.KeyEvent.VK_O);
        BossEstilos.decorateButton(btnModificar);
        panModificarGrabar.add(btnModificar);
        btnModificar.setBounds(330, 10, 90, 20);
        btnModificar.addActionListener(new BotonModificarListener());
        btnGrabar = new JButton(EtiquetasBotones.GRABAR_ARROW);
        btnGrabar.setMnemonic(java.awt.event.KeyEvent.VK_G);
        BossEstilos.decorateButton(btnGrabar);
        panModificarGrabar.add(btnGrabar);
        btnGrabar.setBounds(200, 10, 90, 20);
        btnGrabar.addActionListener(new BotonGrabarListener());
        getContentPane().add(panModificarGrabar);
        panModificarGrabar.setBounds(330, 640, 650, 40);
        getBtnSalir().setLocation(890, 20);
        pack();
    }

    /**
     * Devuelve el <b>selector</b>.
     * @return selector
     */
    protected Component getSelector() {
        return selector;
    }

    /**
     * Setea el <b>selector</b> del formulario.
     * @param selector
     */
    protected void setSelector(Component selector) {
        this.selector = selector;
    }

    /** Actualiza el contenido del selector */
    public abstract void refrescarSelector();

    /** Devuelve <b>true </b> si el selector no tiene ning�n �tem seleccionado */
    protected abstract boolean isSelectorSelectionEmpty();

    /** Habilita/Deshabilita los botones <b>Agregar</b> y <b>Eliminar</b> */
    protected void habilitarAgregarEliminar() {
        if(hijoCreado) {
            if(isSelectorSelectionEmpty()) {
                btnEliminar.setEnabled(false);
                btnModificar.setEnabled(false);
            } else {
                btnEliminar.setEnabled(true);
                btnModificar.setEnabled(true);
            }
        }
    }

    /** Método abstracto de seteo del estado inicial de los componentes */
    public abstract void setEstadoInicial();

    /** Método abstracto de click en el botón 'Agregar' */
    public abstract void botonAgregarPresionado(int nivelNodoSeleccionado);

    /** Método abstracto de click en el botón 'Eliminar' */
    public abstract void botonEliminarPresionado(int nivelNodoSeleccionado);

    /** Método abstracto de click en el botón 'Modificar' */
    public abstract boolean botonModificarPresionado(int nivelNodoSeleccionado);

    /** Método abstracto de click en el botón 'Cancelar' */
    public abstract void botonCancelarPresionado(int nivelNodoSeleccionado);

    /** Método abstracto de click en el botón 'Grabar' */
    public abstract boolean botonGrabarPresionado(int nivelNodoSeleccionado);

    /** Metodo abstracto invocado por el template después de cambiar de modo de */
    public abstract void setModoEdicion(boolean estado);

    /** Método abstracto que limpia los datos de la pantalla */
    public abstract void limpiarDatos();

    /*
     * Métodos vacíos: La clase que herede de este template los puede
     * sobreescribir si es necesario agregar alguna validación previa a la
     * ejecución del código del template. Esto es por si se desea chequear
     * alguna condición que pueda alterar y cancelar el flujo normal del
     * template. Por default, el flujo sigue sin trabas implementado con
     * return true.
     */
    /**
     * Método útil para el manejo del evento de click en el botón <b>Salir</b>.
     * Muestra en pantalla un cuadro de di�logo de confirmación. Devuelve la respuesta
     * del usuario.
     * @return
     */
    public boolean botonSalirPresionado() {
        if(modoEdicion) {
            if(PJOptionPane.showQuestionMessage(GuiABMTemplate.this, "Usted está editando datos. ¿Desea salir sin grabar?", EtiquetasBotones.CERRAR_MODULO) == PJOptionPane.NO_OPTION) {
                return false;
            }
        }
        return true;
    }

    /**
     * Setea el estado inicial de los botones <b>Agregar</b>, <b>Eliminar</b>,
     * <b>Grabar</b> y <b>Modificar</b>.
     */
    protected void setEstadoInicialTemplate() {
    	habilitarBotones(false);
        if(hijoCreado) {
            setEstadoInicial();
        }
    }

    /**
     * Setea el estado del <b>selector</b> y de los botones <b>Agregar</b>, <b>Eliminar</b>,
     * <b>Modificar</b> y <b>Grabar</b> en modo edici�n.
     * @param True para modo edici�n.
     */
    protected void setModoEdicionTemplate(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
        cmbMaestro.setEnabled(!modoEdicion);
        selector.setEnabled(!modoEdicion);
        btnAgregar.setEnabled(!modoEdicion);
        btnGrabar.setEnabled(modoEdicion);
        if(modoEdicion) {
            btnModificar.setEnabled(true);
            btnModificar.setText("Cancelar >>");
            btnModificar.setMnemonic(java.awt.event.KeyEvent.VK_C);
            btnEliminar.setEnabled(false);
        } else {
        	if(isSelectorSelectionEmpty()) {
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
        	} else {
        		btnModificar.setEnabled(true);
        		btnEliminar.setEnabled(true);
        	}
            btnModificar.setText("Modificar >>");
            btnModificar.setMnemonic(java.awt.event.KeyEvent.VK_O);
        }
        setModoEdicion(modoEdicion);
    }

    /**
     * Devuelve <b>true</b> si el módulo permitirá Agregar/Eliminar items al
     * selector.
     * @return permiteAgregarEliminar
     */
    public boolean isPermiteAgregarEliminar() {
        return permiteAgregarEliminar;
    }

    /**
     * Setea si el módulo permitirá Agregar/Eliminar items del selector.
     * @param permiteAgregarEliminar
     */
    public void setPermiteAgregarEliminar(boolean permiteAgregarEliminar) {
        this.permiteAgregarEliminar = permiteAgregarEliminar;
        //Esconde los botones Agregar/Eliminar
        btnAgregar.setVisible(permiteAgregarEliminar);
        btnEliminar.setVisible(permiteAgregarEliminar);
        if(permiteAgregarEliminar) {
        	panSelector.setBounds(panSelector.getX(), panSelector.getY(), panSelector.getWidth(), panSelector.getHeight() - 40);
        } else {
        	panSelector.setBounds(panSelector.getX(), panSelector.getY(), panSelector.getWidth(), panSelector.getHeight() + 40);
        }
    }

    /**
     * Devuelve <b>true</b> si el m�dulo se encuentra en modo consulta.
     * @return modoConsulta
     */
    public boolean isModoConsulta() {
    	return modoConsulta;
    }

    /**
     * Setea si el m�dulo se comportar� en modo <b>consulta</b>. En �ste modo se deshabilitan
     * los botones Agregar/Eliminar/Modificar/Grabar.
     * @param modoConsulta
     */
    public void setModoConsulta(boolean modoConsulta) {
    	this.modoConsulta = modoConsulta;
    	if(modoConsulta) {
    		habilitarBotones(false);
    	} else {
    		btnAgregar.setEnabled(true);
			btnEliminar.setEnabled(!isSelectorSelectionEmpty());
			btnModificar.setEnabled(!isSelectorSelectionEmpty());
			btnGrabar.setEnabled(isModoEdicion());
    	}
    }

    /** M�todo abstracto de selecci�n de items del selector */
    public abstract void itemSelectorSeleccionado(int nivelItemSelector);

    /**
     * Devuelve <b>true</b> si se est�n editando datos.
     * @return modoEdicion
     */
    public boolean isModoEdicion() {
        return modoEdicion;
    }

    /**
     * Devuelve <b>true</b> si el m�dulo que hereda de este template fue creado.
     * @return hijoCreado
     */
    public final boolean isHijoCreado() {
        return hijoCreado;
    }

    /**
     * Setea si el m�dulo que hereda de este template fue creado.
     * @param hijoCreado
     */
    public final void setHijoCreado(boolean hijoCreado) {
        this.hijoCreado = hijoCreado;
    }

    /**
     * @return El item seleccionado en el combo maestro.
     */
    public Object getItemComboMaestroSeleccionado() {
        return cmbMaestro.getSelectedItem();
    }

    /**
     * Establece el contenido del combobox 'Maestro' y setea el texto del label.
     * Si datos es null esconde el combobox.
     * @param datos
     * @param textoLabel
     */
    public void setContenidoComboMaestro(List<?> datos, String textoLabel) {
        if(datos == null) {
            lblComboMaestro.setVisible(false);
            cmbMaestro.setVisible(false);
            GuiUtil.vaciarCombo(cmbMaestro);
        	panSelector.setBounds(panSelector.getX(), panSelector.getY() - 40, panSelector.getWidth(), panSelector.getHeight() + 40);
        } else {
            lblComboMaestro.setText(textoLabel);
            lblComboMaestro.setVisible(true);
            cmbMaestro.setVisible(true);
            GuiUtil.llenarCombo(cmbMaestro, datos, false);
            panSelector.setBounds(panSelector.getX(), panSelector.getY() + 40, panSelector.getWidth(), panSelector.getHeight() - 40);
        }
    }

    /** M�todo abstracto de selecci�n de items del combobox 'Sistema' */
    public abstract void itemComboMaestroSeleccionado();

	public boolean isAgregar() {
		return agregar;
	}

	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

	public JLabel getLblComboMaestro() {
		return lblComboMaestro;
	}

	public void setLblComboMaestro(JLabel lblComboMaestro) {
		this.lblComboMaestro = lblComboMaestro;
	}

	public JComboBox getCmbMaestro() {
		return cmbMaestro;
	}

	public void setCmbMaestro(JComboBox cmbMaestro) {
		this.cmbMaestro = cmbMaestro;
	}

	public JScrollPane getPanSelector() {
		return panSelector;
	}

	public void setPanSelector(JScrollPane panSelector) {
		this.panSelector = panSelector;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public void setBtnAgregar(JButton btnAgregar) {
		this.btnAgregar = btnAgregar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(JButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public JButton getBtnGrabar() {
		return btnGrabar;
	}

	public void setBtnGrabar(JButton btnGrabar) {
		this.btnGrabar = btnGrabar;
	}

	public JPanel getPanModificarGrabar() {
		return panModificarGrabar;
	}

	public void setPanModificarGrabar(JPanel panModificarGrabar) {
		this.panModificarGrabar = panModificarGrabar;
	}

    /*
     * Devuelve el nivel del �tem seleccionado en el selector. Si el selector es
     * un <b>JTree </b> devuelve el nivel del nodo seleccionado. Si el selector
     * es una JList devuelve el �ndice del �tem seleccionado.
     */
    protected int getNivelItemSelector() {
        if(selector instanceof JTree) {
            return TreeUtil.getNivelNodoSeleccionadoArbol((JTree)selector);
        } else if(selector instanceof JList) {
            return ((JList)selector).getSelectedIndex();
        }
        return -1;
    }

    /* Habilita/Deshabilita los botones de ABM. */
    private void habilitarBotones(boolean habilitar) {
        btnAgregar.setEnabled(habilitar);
        btnEliminar.setEnabled(habilitar);
        btnModificar.setEnabled(habilitar);
        btnGrabar.setEnabled(habilitar);
    }

    /**
     * Botón eliminar presionado 
     */
    protected void botonEliminarPresionadoTemplate() {
    	PCursor.startWait(getContentPane());
    	try {
			botonEliminarPresionado(getNivelItemSelector());
		} catch(RuntimeException e) {
			//Gestiono el error de Runtime (Error desconocido)
			BossError.gestionarError(e);
		}
        refrescarSelector();
        PCursor.endWait(getContentPane());
    	if(isSelectorSelectionEmpty()) {
    		btnEliminar.setEnabled(false);
    		btnGrabar.setEnabled(false);
    		btnModificar.setEnabled(false);
    	}
    }

	/** 
	 * Clase para el manejo de eventos de selección de items del combobox 'Maestro' 
	 * @author oarias
	 *
	 */
	class ComboMaestroListener implements ItemListener {
		public void itemStateChanged(ItemEvent evt) {
			if(GuiUtil.isControlEventoEnabled(evt) && evt.getStateChange() == ItemEvent.SELECTED) {
				//refrescarSelector();
				itemComboMaestroSeleccionado();
				habilitarAgregarEliminar();
			}
		}
	}

    /** 
     * Clase para el manejo del evento click del botón <b>Agregar</b> 
     * @author oarias
     *
     */
    class BotonAgregarListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            agregar = true;
            if(selector instanceof JTree && isSelectorSelectionEmpty())
                PJOptionPane.showWarningMessage(GuiABMTemplate.this, "Seleccione un �tem del selector", "Error al agregar");
            else {
                setModoEdicionTemplate(true);
                botonAgregarPresionado(getNivelItemSelector());
            }
        }
    }

    /** 
     * Clase para el manejo del evento click del botón <b>Eliminar</b> 
     * @author oarias
     *
     */
    class BotonEliminarListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if(isSelectorSelectionEmpty())
                PJOptionPane.showWarningMessage(GuiABMTemplate.this, "Seleccione un �tem del selector.", "Error al eliminar");
            else {
                botonEliminarPresionadoTemplate();
            }
        }
    }

    /**
     * Clase para el manejo del evento click del botón <b>Modificar/Cancelar</b>
     * @author oarias
     *
     */
    class BotonModificarListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            agregar = false;
            if(modoEdicion) { //Bot�n 'Cancelar'
                setModoEdicionTemplate(false);
                botonCancelarPresionado(getNivelItemSelector());
                refrescarSelector();
            } else { //Bot�n 'Modificar'
                if(isSelectorSelectionEmpty()) {
                    PJOptionPane.showErrorMessage(GuiABMTemplate.this, "Seleccione un Item de la lista.", "Error al modificar");
                } else {
                    if(botonModificarPresionado(getNivelItemSelector())) {
                        setModoEdicionTemplate(true);
                    }
                }
            }
        }
    }

    /** 
     * Clase para el manejo del evento click del botón <b>Grabar</b> 
     * @author oarias
     *
     */
    class BotonGrabarListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int nivelItemSeleccionado = getNivelItemSelector();
            PCursor.startWait(getContentPane());
            if(agregar) {
                nivelItemSeleccionado++;
            }
            if(botonGrabarPresionado(nivelItemSeleccionado)) {
                agregar = false;
                setModoEdicionTemplate(false);
                refrescarSelector();
            }
            PCursor.endWait(getContentPane());
        }
    }
   
}