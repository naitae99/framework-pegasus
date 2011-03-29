package ar.pegasus.framework.util;

import java.io.Serializable;

/**
 * Clase �til para representar items, ya sea de una �rbol (JTree) o de una lista (JList)
 * con n�mero de <b>id</b> y <b>nombre</b>. Puede utilizarse la descripci�n para mostrar
 * por ejemplo un tooltip del �tem.
 * @author AGEA S.A.
 * @version 1.1
 */
public class IndexItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String descripcion;
	private boolean distinguido;
	private int idPadre;

	public IndexItem() {
	}

	/**
	 * M�todo constructor.
	 * @param id El nro. de id del �tem.
	 * @param nombre El nombre del �tem.
	 */
	public IndexItem(int id, String nombre) {
		this(id, nombre, false);
	}

	/**
	 * M�todo constructor.
	 * @param id El nro. de id del �tem.
	 * @param nombre El nombre del �tem.
	 * @param distinguido Indica si el �tem se mostrar� distinguido.
	 */
	public IndexItem(int id, String nombre, boolean distinguido) {
		this.id = id;
		this.nombre = nombre;
		this.distinguido = distinguido;
	}

	/**
	 * @return Devuelve el id del �tem.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setea el <b>id</b> del �tem.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Devuelve el nombre del �tem.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el <b>nombre</b> del �tem.
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return La descripci�n del �tem.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Setea la <b>descripci�n</b> al �tem.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return true si el �tem est� distinguido.
	 */
	public boolean isDistinguido() {
		return distinguido;
	}

	/**
	 * Setea al �tem distinguido.
	 * @param distinguido
	 */
	public void setDistinguido(boolean distinguido) {
		this.distinguido = distinguido;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	/** M�todo <b>toString</b> */
	public String toString() {
		return nombre;
	}

	/** M�todo <b>equals</b> */
	public boolean equals(Object obj) {
		if(!(obj instanceof IndexItem))
			return false;
		else if(obj == null)
			return false;
		else {
			return (((IndexItem)obj).getId() == id) &&
					(((IndexItem)obj).getNombre().equals(nombre)) &&
					(((IndexItem)obj).isDistinguido() == distinguido);
		}
	}

	public int hashCode() {
		return id << 3 ^ id >> 1;
	}

}