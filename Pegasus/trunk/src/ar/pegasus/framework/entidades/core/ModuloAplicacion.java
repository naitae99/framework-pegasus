package ar.pegasus.framework.entidades.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ModuloAplicacion")
public class ModuloAplicacion {
	private int id;
	private Aplicacion aplicacion;
	private Modulo modulo;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}
    
	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}
	@ManyToOne
	@JoinColumn(name="F_IdAplicacion", nullable=false)
	public Aplicacion getAplicacion() {
		return aplicacion;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	
	@ManyToOne
	@JoinColumn(name="F_IdModulo", nullable=false)	
	public Modulo getModulo() {
		return modulo;
	}
}
