package ar.pegasus.framework.entidades.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int idUsuario;
	private String nombre;
	private String password;
	private String userName;
//	private Persona persona;
	private Rol rol;
	
	/** Método constructor */
    public Usuario() {
    }

    /**
     * Método constructor.
     * @param idUsuario El nro. de id del usuario.
     * @param nombre El nombre de usuario.
     */
    public Usuario(int idUsuario, String nombre) {
    	this(idUsuario, nombre, null);
    }

    /**
     * Método constructor.
     * @param idUsuario El nro. de id del usuario.
     * @param nombre El nombre de usuario.
     * @param commonName El common name del usuario.
     */
    public Usuario(int idUsuario, String nombre, String commonName) {
    	this(idUsuario, nombre, commonName, null);
    }

    /**
     * M�todo constructor.
     * @param idUsuario El nro. de id del usuario.
     * @param nombre El nombre completo de usuario.
     * @param userName El nombre de usuario.
     * @param rol El rol del usuario.
     */
    public Usuario(int idUsuario, String nombre, String userName, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.userName = userName;
        this.setRol(rol);
    }

    
    @Id
    @Column(name="idUsuario")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int id) {
		this.idUsuario = id;
	}
	
	@Column(name="a_nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@Column(name="A_UserName")
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
//	@OneToOne(mappedBy="usuario")
//	public Persona getPersona() {
//		return persona;
//	}
//	public void setPersona(Persona persona) {
//		this.persona = persona;
//	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

//	@ManyToOne
//	@JoinColumn(name="F_IdRolUsuario", nullable=false)
	@Transient
	public Rol getRol() {
		return rol;
	}
	
}
