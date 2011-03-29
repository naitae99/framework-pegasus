package ar.pegasus.framework.entidades.core;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Rol")
public class Rol implements Comparable<Rol>, Serializable{
	private static final long serialVersionUID = -7500596515757757496L;

	private int idRol;
	private String nombre;
	private String descripcion;
	private List<Modulo> modulos;
	
	public Rol(Integer idRol, String nombre, String descripcion){
		setIdRol(idRol);
		setNombre(nombre);
		setDescripcion(descripcion);
	}
	
    public Rol() {
		
	}

	@Id
	@Column(name="idRol")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdRol() {
		return idRol;
	}
	
    public void setIdRol(int id) {
		this.idRol = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}
	
    /**
     * Devuelve la descripci�n del rol.
     * @return descripcion La descripci�n del rol.
     */
	@Column(name="descripcion", nullable=false)
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setea la descripci�n del rol.
     * @param descripcion La descripci�n del rol.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	@ManyToMany(targetEntity = Modulo.class)
	@JoinTable(name = "RolModulo", joinColumns = { @JoinColumn(name = "F_IdRol") }, inverseJoinColumns = { @JoinColumn(name = "F_IdModulo") })
	public List<Modulo> getModulos() {
		return modulos;
	}

	/** M�todo toString */
    public String toString() {
        return nombre;
    }

    /** M�todo equals */
    public boolean equals(Object o) {
        if(o instanceof Rol) {
            Rol rol = (Rol)o;
            if(idRol == rol.getIdRol() && nombre.equals(rol.getNombre()) && descripcion.equals(rol.getDescripcion())) {
                return true;
            }
        }
        return false;
    }

    /** M�todo compareTo */
	public int compareTo(Rol r) {
		return this.getNombre().compareToIgnoreCase(r.getNombre());
	}

}
