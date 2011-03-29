package ar.pegasus.framework.entidades;

import java.io.Serializable;
import java.util.List;

import ar.pegasus.framework.entidades.core.Aplicacion;
import ar.pegasus.framework.entidades.core.Modulo;
import ar.pegasus.framework.entidades.enumeradores.EnumTipoAccion;

public class Accion implements Serializable {
	private static final long serialVersionUID = 4338943407156441771L;

	private Integer idAccion;
	private String nombre ;
	private String descripcion ;
	private String toolTip ;
	private String imagenActiva ;
	private String imagenInactiva ;
	private Boolean independienteSeleccion ;
    private int tipo = EnumTipoAccion.NO_PERMITIDA;
	/**
	 * La clase asociada a la acción.
	 * Puede o no utilizarse.
	 * Ej) Un valor posible para SGP-Materiales sería gui.builders.acciones.accion.AccionIngresarOriginal
	 */
	private String ubicacion ;
	/**
	 * El grupo asociado a la acción.
	 * Puede o no utilizarse.
	 * Ej) Un valor posible para SGP-Materiales sería un grupo con nombre Operaciones
	 */
	private GrupoAccion grupoAccion ;
	/**
	 * El número de orden de la acción dentro del grupo al cual pertenece.
	 * Puede o no utilizarse.
	 * Ej) Si la accion tiene orden 2 y grupo G, al armar la seccion de la GUI correspondiente
	 * a las acciones la misma debería aparecer 2da en la subseccion correspondiente al grupo G
	 */
	private Integer orden ;
	/**
	 * La acción puede aplicarse sólo a módulos pertenecientes a las aplicaciones de esta lista.
	 */
	private List<Aplicacion> aplicacionList;
	/**
	 * Módulos en los que se debe incluir esta acción.
	 */
	private List<Modulo> moduloList;

	private boolean valida;

	public Integer getIdAccion() {
		return idAccion;
	}
	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getImagenActiva() {
		return imagenActiva;
	}
	public void setImagenActiva(String imagenActiva) {
		this.imagenActiva = imagenActiva;
	}
	public String getImagenInactiva() {
		return imagenInactiva;
	}

	public void setImagenInactiva(String imagenInactiva) {
		this.imagenInactiva = imagenInactiva;
	}

	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public GrupoAccion getGrupoAccion() {
		return grupoAccion;
	}
	public void setGrupoAccion(GrupoAccion grupoAccion) {
		this.grupoAccion = grupoAccion;
	}

	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<Aplicacion> getAplicacionList() {
		return aplicacionList;
	}
	public void setAplicacionList(List<Aplicacion> aplicacionList) {
		this.aplicacionList = aplicacionList;
	}

	public List<Modulo> getModuloList() {
		return moduloList;
	}
	public void setModuloList(List<Modulo> moduloList) {
		this.moduloList = moduloList;
	}

	/**
	 * @return Returns the valida.
	 */
	public boolean esValida() {
		return valida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAccion == null) ? 0 : idAccion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Accion other = (Accion) obj;
		if (idAccion == null) {
			if (other.idAccion != null)
				return false;
		}
		return idAccion.equals(other.idAccion) ;
	}
	@Override
	public String toString() {
		return getNombre();
	}
	public Boolean getIndependienteSeleccion() {
		return independienteSeleccion;
	}
	public void setIndependienteSeleccion(Boolean independienteSeleccion) {
		this.independienteSeleccion = independienteSeleccion;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getTipo() {
		return tipo;
	}

}
