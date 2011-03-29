package ar.pegasus.framework.entidades.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Aplicacion")
@Inheritance(strategy=InheritanceType.JOINED)
public class Aplicacion implements Serializable {
	private static final long serialVersionUID = -4609214955706839185L;

	private int idAplicacion;
	private String descripcion;
	private List<Modulo> modulos;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="P_IdAplicacion")
	public int getIdAplicacion() {
		return idAplicacion;
	}
	
    public void setIdAplicacion(int id) {
		this.idAplicacion = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="descripcion", nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	
	@ManyToMany(targetEntity = Modulo.class)
	@JoinTable(name = "ModuloAplicacion", joinColumns = { @JoinColumn(name = "F_IdAplicacion") }, inverseJoinColumns = { @JoinColumn(name = "F_IdModulo") })
	public List<Modulo> getModulos() {
		return modulos;
	}
	@Transient
	public List<Modulo> getModulosOrdenados() {
		List<Modulo> modulosOrdenados = new ArrayList<Modulo>(modulos);
		Collections.sort(modulosOrdenados);
		return modulosOrdenados;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public String toString() {
		return getDescripcion();
	}
}
