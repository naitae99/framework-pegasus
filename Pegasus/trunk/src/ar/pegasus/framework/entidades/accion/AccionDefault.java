package ar.pegasus.framework.entidades.accion;

import ar.pegasus.framework.entidades.Accion;

/**
 * 
 * @author Lcremonte Migracion desde FWSGP
 *
 */
public class AccionDefault extends Accion {
	private static final long serialVersionUID = -5081703505588626962L;

	/**
	 * Método constructor.
	 */
	public AccionDefault() {
		super();
	}
	
	@Override
	public boolean esValida() {
		return true;
	}

//	@Override
//	public void ejecutar(List items) {
//		// TODO Auto-generated method stub
//		
//	}


}