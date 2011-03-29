package ar.pegasus.framework.excepciones;

import ar.pegasus.framework.auditoria.BossError;

/**
 * <p>
 * Toma una excepcion (de runtime) e intenta cambiar el mensaje que trae por uno mas legible.
 * </p> 
 * <p>
 * El interprete es accedido mediante {@link BossError}. La implementacion default es devolver ex.getLocalizedMessage().</p>
 * <p>Cada aplicacion, dependiendo de las tecnologias subyacentes, sabe interpretar distintos 
 * grupos de excepciones.</p> 
 * <p>Por ejemplo:<br>
 * el interprete de SGP interpreta las excepciones de EJB3,
 * y muestra mensajes entendibles para un usuario SGP.</p>  
 * 
 * @see BossError#interpretarMensajeExcepcion(RuntimeException) 
 * @author pforesto
 *
 */
public interface InterpreteExcepciones {
	/**
	 * Interpreta la excepción. Ver documentacion de {@link InterpreteExcepciones}.
	 * @param ex
	 * @return String
	 */
	public String interpretarMensajeExcepcion (RuntimeException ex);
	
	/**
	 * Hace uniforme el tratamiento de excepciones. 
	 * Centraliza la traduccion de excepciones.
	 * Por ejemplo: 
	 *  Cuando se violó una constraint devuelve 
	 *  - ObjectInUserCLException
	 *  
	 *   Lista de excepciones que puede devolver.
	 *    - ObjectInUserCLException (Constraint)
	 *    - null (no identificado)
	 * @param e
	 * @return
	 */
	//public Exception traducirExcepcion (Exception e);
	
}
