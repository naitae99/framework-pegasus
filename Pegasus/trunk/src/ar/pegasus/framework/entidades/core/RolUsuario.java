package ar.pegasus.framework.entidades.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RolUsuario")
public class RolUsuario {
	private int id;
	private Rol rol;
	private Usuario usuario;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@ManyToOne
	@JoinColumn(name="F_IdRol", nullable=false)
	public Rol getRol() {
		return rol;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name="F_IdUsuario", nullable=false)
	public Usuario getUsuario() {
		return usuario;
	}
		

}
