package ar.pegasus.framework.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import ar.pegasus.framework.templates.main.AbstractMainTemplate;

public class PDateFieldWithButton extends JPanel {
	private static final long serialVersionUID = 132524014490220888L;
	private JFormattedTextField txtFechaConsulta;
	private BotonCalendarioFecha btnCalendarioFecha;
	private SimpleDateFormat formato;

	public PDateFieldWithButton(SimpleDateFormat formato){
		setFormato(formato);
		construct();
	}
	
	private void construct() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(getTxtFecha());
		add(getBtnFecha());
	}

	private BotonCalendarioFecha getBtnFecha() {
		if(btnCalendarioFecha == null) {
			btnCalendarioFecha = new BotonCalendarioFecha();
		}
		return btnCalendarioFecha;		
	}
	
	private JFormattedTextField getTxtFecha() {
		if(txtFechaConsulta == null) {
			txtFechaConsulta = new JFormattedTextField(getFormato());
			txtFechaConsulta.setEditable(false);
			txtFechaConsulta.setEnabled(false);
			txtFechaConsulta.setDisabledTextColor(Color.BLACK);
			txtFechaConsulta.setHorizontalAlignment(JFormattedTextField.CENTER);
			txtFechaConsulta.setMinimumSize(new Dimension(120, 20));
			txtFechaConsulta.setPreferredSize(new Dimension(120, 20));
		}
		return txtFechaConsulta;
	}
	
	private SimpleDateFormat getFormato() {
		return formato;
	}
	
	private void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
	}

	/**
	 * Clase que maneja la fecha de consulta de la mascota
	 * 
	 * @author oarias
	 *
	 */
	protected class BotonCalendarioFecha extends PBtnCalendarioBF {
		private static final long serialVersionUID = 7155639125386756024L;

		public BotonCalendarioFecha() {
			super(AbstractMainTemplate.getFrameInstance());
		}

		public BotonCalendarioFecha(Date fechaMinima, Date fechaMaxima) {
			super(AbstractMainTemplate.getFrameInstance(), fechaMinima, fechaMaxima);
		}

		@Override
		public void botonCalendarioPresionado() {
			getTxtFecha().requestFocus();
			fechaModificada( getSelectedDate(), (Date)getTxtFecha().getValue());
		}
	}

	protected void fechaModificada(Date fechaNueva, Date fechaAnterior){
	}
	
	public void setEditable(boolean b) {
		getTxtFecha().setEditable(b);
	}

	public void setDisabledTextColor(Color color) {
		getTxtFecha().setDisabledTextColor(color);
	}

	public void setHorizontalAlignment(int alignment) {
		getTxtFecha().setHorizontalAlignment(alignment);
	}

	public Date getValue() {
		return (Date)getTxtFecha().getValue();
	}

	public void setValue(Date fecha) {
		if(fecha == null){
			getTxtFecha().setText("");
			return;
		}
		getTxtFecha().setValue(fecha); // DateUtil.dateToString(fecha,DateUtil.WEEK_DAY_SHORT_DATE)
	}
	
	@Override
	public void setEnabled(boolean estado){
		getTxtFecha().setEnabled(estado);
		getBtnFecha().setEnabled(estado);
	}
}
