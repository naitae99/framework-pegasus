package ar.pegasus.framework.entidades.enumeradores;

import java.util.ArrayList;
import java.util.List;

import ar.pegasus.framework.util.IndexItem;

/**
 * Enumerador de tipos de acción.
 * @author oarias
 */

public class EnumTipoAccion {

    public static final int CONSULTA = 1;
    public static final int OPERACION = 2;
    public static final int NO_PERMITIDA = 3;

    /**
     * Devuelve la descripción de un tipo de acción.
     * @param id El id del tipo de acci�n.
     * @return La descripcion del tipo de acci�n.
     */
    public static String getDescripcion(int id) {
        String descripcion = null;
        switch(id) {
            case CONSULTA:
            descripcion = "Consulta";
            break;

            case OPERACION:
            descripcion = "Operaci�n";
            break;

            case NO_PERMITIDA:
            descripcion = "No Permitida";
            break;
        }
        return descripcion;
    }

    /**
     * Devuelve el id de un tipo de acci�n.
     * @param descripcion La descripci�n del tipo de acci�n.
     * @return El id del tipo de acci�n.
     */
    public static int getId(String descripcion) {
        int id = -1;
        if(descripcion.compareTo(getDescripcion(CONSULTA)) == 0)
			id = CONSULTA;
        else if(descripcion.compareTo(getDescripcion(OPERACION)) == 0)
			id = OPERACION;
		else if(descripcion.compareTo(getDescripcion(NO_PERMITIDA)) == 0)
			id = NO_PERMITIDA;
		return id;
    }

	/**
	 * Devuelve una lista con el id y descripci�n de todos los tipos de acci�n.
	 * @return La lista de tipos de acci�n.
	 */
	public static List<IndexItem> getListaTiposAccion() {
		List<IndexItem> tiposAccion = new ArrayList<IndexItem>();
		tiposAccion.add(new IndexItem(CONSULTA, getDescripcion(CONSULTA)));
		tiposAccion.add(new IndexItem(OPERACION, getDescripcion(OPERACION)));
		tiposAccion.add(new IndexItem(NO_PERMITIDA, getDescripcion(NO_PERMITIDA)));
		return tiposAccion;
	}

    /**
     * Compara dos tipos de acci�n.
     * @param tipo1 El id del primer tipo de acci�n.
     * @param tipo2 El id del segundo tipo de acci�n.
     * @return <b>true</b> si el primer tipo de acci�n es menor al segundo.
     */
	public static boolean esMenor(int tipo1, int tipo2) {
        if(tipo1 == NO_PERMITIDA || tipo2 == NO_PERMITIDA) {
            return false;
        } else if(tipo1 == OPERACION && tipo2 == CONSULTA) {
            return false;
        } else {
            return true;
        }
    }

}