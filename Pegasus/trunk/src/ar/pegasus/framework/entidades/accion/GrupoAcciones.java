package ar.pegasus.framework.entidades.accion;
/**
 * 
 * @author lcremonte Migracion desde FWSGP
 *
 */
public class GrupoAcciones {

	private int orden;
	private String nombre;

	public GrupoAcciones(int orden, String nombre) {
		setOrden(orden);
		setNombre(nombre);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getOrden() {
		return orden;
	}

	public String getNombre() {
		return nombre;
	}

}