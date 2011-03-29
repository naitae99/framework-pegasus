package ar.pegasus.framework.entidades.core;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Modulo")
public class Modulo implements Comparable<Modulo>, Serializable{
	private static final long serialVersionUID = 6811128225651757494L;

	private int idModulo;
    private String nombre;
    private String ubicacion;
    private Integer orden;
    //private transient Integer idGrupoModulos;//temporalmente transient
    public String icono;

	private List<Rol> roles;
    public static final int MODULO_NUEVO = 0;
    public static final int MODULO_ANULADO = -1;
    public static final int MODULO_SEPARADOR = -2;
    
    /** Método constructor */
    public Modulo() {
    }
    
    /**
     * Método constructor.
     * @param idModulo El nro. de id del módulo.
     * @param nombre El nombre del módulo.
     */
    public Modulo(int idModulo, String nombre) {
        this.idModulo = idModulo;
        this.nombre = nombre;
    }
    
    /**
     * Método constructor.
     * @param idModulo El nro. de id del módulo.
     * @param nombre El nombre del módulo.
     * @param ubicacion La clase asociada al módulo (debe incluir el package y no incluir el .class).
     */
    public Modulo(int idModulo, String nombre, String ubicacion) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    /**
     * Método constructor.
     * @param idModulo El nro. de id del módulo.
     * @param nombre El nombre del módulo.
     * @param ubicacion La clase asociada al módulo (debe incluir el package y no incluir el .class).
     * @param orden La ubicación del módulo en el menú.
     */
    public Modulo(int idModulo, String nombre, String ubicacion, int orden) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.orden = orden;
    }
    
    /**
     * Devuelve el <b>id</b> del módulo.
     * @return idModulo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="p_IdModulo")
	public int getIdModulo() {
		return idModulo;
	}
	
    /**
     * Setea el <b>id</b> del módulo.
     * @param idModulo
     */
    public void setIdModulo(int id) {
		this.idModulo = id;
	}
	
    /**
     * Setea el <b>nombre</b> del módulo.
     * @param nombre
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
    /**
     * Devuelve el <b>nombre</b> del módulo.
     * @return nombre
     */
	@Column(name="nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}

    /**
     * Retorna la <b>clase</b> asociada al módulo (debe incluir el package y no incluir el .class).
     * @return ubicacion
     */
	@Column(name="ubicacion", nullable=true)
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Setea la <b>clase</b> asociada al módulo (debe incluir el package y no incluir el .class).
     * @param ubicacion
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Obtiene el <b>orden</b> del módulo en el menú <b>Módulos</b>.
     * @return orden
     */
	@Column(name="orden", nullable=false)
    public Integer getOrden() {
        return orden;
    }

    /**
     * Setea el <b>orden</b> del módulo en el menú <b>Módulos</b>.
     * @param orden
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene el <b>ícono</b> del módulo en el menú <b>Módulos</b>.
     * @return icono
     */
	@Column(name="icono", nullable=false)
    public String getIcono() {
    	return icono;
    }

    /**
     * Setea el <b>ícono</b> del módulo en el menú <b>Módulos</b>.
     * @param icono
     */
    public void setIcono(String icono) {
    	this.icono = icono;
    }	
	
    public static Modulo crearModuloSeparador() {
    	Modulo sep = new Modulo(MODULO_SEPARADOR, String.valueOf(MODULO_SEPARADOR), String.valueOf(MODULO_SEPARADOR));
    	sep.setOrden(MODULO_SEPARADOR);
    	return sep;
    }
    
    @Transient
	public boolean isSeparador() {
		return getOrden() == Modulo.MODULO_SEPARADOR;
	}

    /** Retorna el nombre del módulo */
    public String toString () {
        return getNombre() ;
    }

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "modulos",
            targetEntity = Rol.class
        )
    public List<Rol> getRoles() {
        return roles;

    }
    public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	/** Sobreescritura del metodo equals */
    public boolean equals(Object obj) {
        if((obj != null) && (obj instanceof Modulo)) {
            return (this.getIdModulo() == ((Modulo)obj).getIdModulo());
        }
        return false;
    }

	public int compareTo(Modulo modulo) {
		return getOrden().compareTo(modulo.getOrden());
	}
}
