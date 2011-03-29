package ar.pegasus.framework.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ar.pegasus.framework.util.DateUtil;
import ar.pegasus.framework.util.ImageUtil;
import ar.pegasus.framework.util.RutaImagenes;
/**
 * Componente que muestra un botón que al ser presionado muestra un PPopupCalendar.
 * Además muestra dos botones para navegar por las fechas del calendario.
 * @author oarias
 * @see PBotonCalendario
 * @see PPopupCalendar
 */
public class PBtnCalendarioBF extends JPanel {
	private static final long serialVersionUID = -7616890697089762814L;

	public static String iconoBtnAdelante = null;
    public static String iconoBtnAdelanteDeshab = null;
    public static String iconoBtnAtras = null;
    public static String iconoBtnAtrasDeshab = null;
    private PBotonCalendario botonCalendario;
    private JButton btnAtras;
    private JButton btnAdelante;
    private Date limiteFechaDesde;
    private Date limiteFechaHasta;
	private Frame owner;


    /** Método constructor */
    public PBtnCalendarioBF() {
        construct() ;
    }

    /** Método constructor */
    public PBtnCalendarioBF(Frame owner) {
        this.owner = owner ;
        construct() ;
    }

    /**
     * Método constructor.
     * @param limiteDesde La fecha Desde l�mite.
     * @param limiteHasta La fecha Hasta l�mite.
     */
    public PBtnCalendarioBF(Date limiteDesde, Date limiteHasta) {
        setLimiteFechaDesde(limiteDesde);
        setLimiteFechaHasta(limiteHasta);
        construct() ;
    }

    /**
     * Método constructor.
     * @param owner El padre del di�logo.
     * @param limiteDesde La fecha Desde l�mite.
     * @param limiteHasta La fecha Hasta l�mite.
     */
    public PBtnCalendarioBF(Frame owner, Date limiteDesde, Date limiteHasta) {
    	this.owner = owner ;
        setLimiteFechaDesde(limiteDesde);
        setLimiteFechaHasta(limiteHasta);
        construct() ;
    }

    private void construct () {
    	setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)) ;
    	add(getBtnAtras());
    	add(getBotonCalendario());
    	add(getBtnAdelante());
    	
    }
    /**
     * Devuelve el icono del botón <b>Adelante</b>.
     * @return iconoBtnAdelante
     */
    public String getIconoBtnAdelante() {
        return iconoBtnAdelante;
    }

    /**
     * Setea el icono del botón <b>Adelante</b>.
     * @param iconoBtnAdelante
     */
    public void setIconoBtnAdelante(String iconoBtnAdelante) {
        PBtnCalendarioBF.iconoBtnAdelante = iconoBtnAdelante;
        if(btnAdelante != null) {
            Icon icon = ImageUtil.loadIcon(iconoBtnAdelante);
            btnAdelante.setIcon(icon);
            btnAdelante.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
    }

    /**
     * Devuelve el icono del botón <b>Adelante</b> en estado deshabilitado.
     * @return iconoBtnAdelanteDeshab
     */
    public String getIconoBtnAdelanteDeshab() {
        return iconoBtnAdelanteDeshab;
    }

    /**
     * Setea el icono del botón <b>Adelante</b> en estado deshabilitado.
     * @param iconoBtnAdelanteDeshab
     */
    public void setIconoBtnAdelanteDeshab(String iconoBtnAdelanteDeshab) {
        PBtnCalendarioBF.iconoBtnAdelanteDeshab = iconoBtnAdelanteDeshab;
        if(btnAdelante != null)
            btnAdelante.setDisabledIcon(ImageUtil.loadIcon(iconoBtnAdelanteDeshab));
    }

    /**
     * Devuelve el icono del botón <b>Atrás</b>.
     * @return iconoBtnAtras
     */
    public String getIconoBtnAtras() {
        return iconoBtnAtras;
    }

    /**
     * Setea el icono del botón <b>Atrás</b>.
     * @param iconoBtnAtras
     */
    public void setIconoBtnAtras(String iconoBtnAtras) {
        PBtnCalendarioBF.iconoBtnAtras = iconoBtnAtras;
        if(btnAtras != null) {
            Icon icon = ImageUtil.loadIcon(iconoBtnAtras);
            btnAtras.setIcon(icon);
            btnAtras.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
    }

    /**
     * Devuelve el icono del botón <b>Atrás</b> en estado deshabilitado.
     * @return iconoBtnAtrasDeshab
     */
    public String getIconoBtnAtrasDeshab() {
        return iconoBtnAtrasDeshab;
    }

    /**
     * Setea el icono del botón <b>Atrás</b> en estado deshabilitado.
     * @param iconoBtnAtrasDeshab
     */
    public void setIconoBtnAtrasDeshab(String iconoBtnAtrasDeshab) {
        PBtnCalendarioBF.iconoBtnAtrasDeshab = iconoBtnAtrasDeshab;
        if(btnAtras != null)
            btnAtras.setDisabledIcon(ImageUtil.loadIcon(iconoBtnAtrasDeshab));
    }

    //Devuelve el botón 'Atrás'
    private JButton getBtnAtras() {
        if(btnAtras == null) {
            btnAtras = new JButton();
            btnAtras.setFocusable(false);
            btnAtras.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btnAtras.addActionListener(new BtnAtrasActionListener());
            String iconoAtras = (iconoBtnAtras == null ? RutaImagenes.DEFAULT_ICONO_BTN_ATRAS : iconoBtnAtras);
            String iconoAtrasDeshab = (iconoBtnAtrasDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_ATRAS_DESHAB : iconoBtnAtrasDeshab);
            Icon icon = ImageUtil.loadIcon(iconoAtras);
            btnAtras.setIcon(icon);
            btnAtras.setDisabledIcon(ImageUtil.loadIcon(iconoAtrasDeshab));
            btnAtras.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        return btnAtras;
    }

    //Devuelve el botón 'Adelante'
    private JButton getBtnAdelante() {
        if(btnAdelante == null) {
            btnAdelante = new JButton();
            btnAdelante.setFocusable(false);
            btnAdelante.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btnAdelante.addActionListener(new BtnAdelanteActionListener());
            String iconoAdelante = (iconoBtnAdelante == null ? RutaImagenes.DEFAULT_ICONO_BTN_ADELANTE : iconoBtnAdelante);
            String iconoAdelanteDeshab = (iconoBtnAdelanteDeshab == null ? RutaImagenes.DEFAULT_ICONO_BTN_ADELANTE_DESHAB : iconoBtnAdelanteDeshab);
            Icon icon = ImageUtil.loadIcon(iconoAdelante);
            btnAdelante.setIcon(icon);
            btnAdelante.setDisabledIcon(ImageUtil.loadIcon(iconoAdelanteDeshab));
            btnAdelante.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        return btnAdelante;
    }

    //Manejo del evento de click en el botón 'Adelante'
    private class BtnAdelanteActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            Date seleccionada = getBotonCalendario().getCalendario().getSelectedDate();
            if(!DateUtil.iguales(seleccionada, getLimiteFechaHasta())) {
                setSelectedDate(DateUtil.getMananaSinRedondear(seleccionada));
                PBtnCalendarioBF.this.botonCalendarioPresionado();
            }
        }
    }

    //Manejo del evento de click en el bot�n 'Atr�s'
    private class BtnAtrasActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            Date seleccionada = getBotonCalendario().getCalendario().getSelectedDate();
            if(!DateUtil.iguales(seleccionada, getLimiteFechaDesde())) {
                setSelectedDate(DateUtil.getAyerSinRedondear(seleccionada));
                PBtnCalendarioBF.this.botonCalendarioPresionado();
            }
        }
    }

    //Devuelve el bot�n calendario
    private PBotonCalendario getBotonCalendario() {
        if(botonCalendario == null) {
        	if (getLimiteFechaDesde() != null && getLimiteFechaHasta() != null) {
        		if (getOwner() == null) {
		            botonCalendario = new PBotonCalendario(getLimiteFechaDesde(), getLimiteFechaHasta()) {
						private static final long serialVersionUID = 7187154418243753560L;

						public void botonCalendarioPresionado() {
		                    PBtnCalendarioBF.this.botonCalendarioPresionado();
		                }
		            };
        		} else {
		            botonCalendario = new PBotonCalendario(getOwner(), getLimiteFechaDesde(), getLimiteFechaHasta()) {
						private static final long serialVersionUID = 1444588061054659274L;

						public void botonCalendarioPresionado() {
		                    PBtnCalendarioBF.this.botonCalendarioPresionado();
		                }
		            };        			
        		}
        	} else {
        		if (getOwner() == null) {
		            botonCalendario = new PBotonCalendario() {
						private static final long serialVersionUID = 8015900760837250099L;

						public void botonCalendarioPresionado() {
		                    PBtnCalendarioBF.this.botonCalendarioPresionado();
		                }
		            };
        		} else {
		            botonCalendario = new PBotonCalendario(getOwner()) {
						private static final long serialVersionUID = 1375075178337195263L;

						public void botonCalendarioPresionado() {
		                    PBtnCalendarioBF.this.botonCalendarioPresionado();
		                }
		            };
        		}
        	}
            botonCalendario.setPreferredSize(new Dimension(botonCalendario.DEFAULT_BUTTON_WIDTH, botonCalendario.DEFAULT_BUTTON_HEIGHT));
            setSelectedDate(DateUtil.getHoy());
        }
        return botonCalendario;
    }

    private Frame getOwner() {
		return owner;
	}

	/**
     * Devuelve el l�mite de la fecha <b>Desde</b>.
     * @return limiteDesde
     */
    public Date getLimiteFechaDesde() {
        return limiteFechaDesde;
    }

    /**
     * Setea el l�mite de la fecha <b>Desde</b>.
     * @param limiteDesde
     */
    public void setLimiteFechaDesde(Date limiteDesde) {
        this.limiteFechaDesde = limiteDesde;
    }

    /**
     * Devuelve el l�mite de la fecha <b>Hasta</b>.
     * @return limiteDesde
     */
    public Date getLimiteFechaHasta() {
        return limiteFechaHasta;
    }

    /**
     * Setea el l�mite de la fecha <b>Hasta</b>.
     * @param limiteDesde
     */
    public void setLimiteFechaHasta(Date limiteHasta) {
        this.limiteFechaHasta = limiteHasta;
    }

    /**
     * Devuelve la fecha seleccionada en el calendario.
     * @return La fecha seleccionada en el calendario.
     */
    public Date getSelectedDate() {
        return getBotonCalendario().getCalendario().getSelectedDate();
    }

    /**
     * Setea la fecha seleccionada en el calendario.
     * @param date La fecha a seleccionar en el calendario.
     */
    public void setSelectedDate(Date date) {
        getBotonCalendario().getCalendario().setSelectedDate(date);
    }

	/**
	 * M�todo invocado cuando se produce el evento de click del bot�n.
	 * M�todo vac�o para ser sobreescrito.
	 */
    public void botonCalendarioPresionado() {
    }

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	    botonCalendario.setEnabled(enabled);
	    btnAtras.setEnabled(enabled);
	    btnAdelante.setEnabled(enabled);
	}

}