package ar.pegasus.framework.componentes.error;

/**
 * Clase que encapsula una excepción de runtime. 
 * @author oarias
 */
@SuppressWarnings("serial")
public class PRuntimeException  extends RuntimeException {

    public PRuntimeException() {
        super();
    }

    public PRuntimeException(String message) {
        super(message);
    }

    public PRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PRuntimeException(Throwable cause) {
        super(cause);
    }


}
