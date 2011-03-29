package ar.pegasus.framework.componentes;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class PException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAplicacion;
    private String[] tips;
    private int tipoError;
    private String mensajeContexto;

    /*
     * Se puede utilizar para guardar informaci�n sobre la llamada que gener� el error
     * Ej) Query o invocaci�n a SP - invocaci�n a m�todo de WS - etc.
     */
    private String llamadaParametrizada;

    public PException() {
        super();
    }

    public PException(int tipoError, String mensajeError) {
        super(mensajeError);
        setTipoError(tipoError);
    }

    public PException(int tipoError, String mensajeError, String[] tips) {
        super(mensajeError);
        setTipoError(tipoError);
        setTips(tips);
    }

    public PException(int tipoError, String mensajeError, Throwable cause) {
        super(mensajeError, cause);
        setTipoError(tipoError);
    }

    public PException(int tipoError, String mensajeError, Throwable cause, String[] tips) {
        super(mensajeError, cause);
        setTipoError(tipoError);
        setTips(tips);
    }

    public PException(int tipoError, String mensajeContexto, String mensajeError, Throwable cause, String[] tips) {
        super(mensajeError, cause);
        setTipoError(tipoError);
        setMensajeContexto(mensajeContexto);
        setTips(tips);
    }

    public PException(int tipoError, String mensajeContexto, String mensajeError, String[] tips) {
    	super(mensajeError);
    	setTipoError(tipoError);
    	setMensajeContexto(mensajeContexto);
    	setTips(tips);
    }

    public PException(int tipoError, String mensajeContexto, String mensajeError, String llamadaParametrizada, Throwable cause, String[] tips) {
        super(mensajeError, cause);
        setTipoError(tipoError);
        setMensajeContexto(mensajeContexto);
        setLlamadaParametrizada(llamadaParametrizada);
        setTips(tips);
    }

    public PException(String mensajeError, Throwable cause) {
        super(mensajeError, cause);
    }
    public PException(int tipoError, String mensajeContexto, String mensajeError, String llamadaParametrizada, int cause, String[] tips) {
    	
    }
   //[int, class java.lang.String, class java.lang.String, class java.lang.String, int, class [Ljava.lang.String;]
    public PException(String mensajeError) {
        super(mensajeError);
    }

    public String[] getTips() {
        return tips;
    }

    public void setTips(String[] tips) {
        this.tips = tips;
    }

    public String getMensajeError() {
        return getMessage();
    }

    /**
     * 
     * @return
     * <ul>	
     *  <li>BossError.ERR_APLICACION </li>
     *  <li>BossError.ERR_CONEXION</li>
	 *  <li>BossError.ERR_OPERACION</li>
	 *  <li>BossError.ERR_INDETERMINADO</li>
	 * </ul>
     */
    public int getTipoError() {
        return tipoError;
    }

    public void setTipoError(int tipoError) {
        this.tipoError = tipoError;
    }

    public void setMensajeContexto(String mensajeContexto) {
        this.mensajeContexto = mensajeContexto;
    }

    public String getMensajeContexto() {
        return mensajeContexto;
    }

	public String getLlamadaParametrizada() {
		return llamadaParametrizada;
	}

	public void setLlamadaParametrizada(String llamadaParametrizada) {
		this.llamadaParametrizada = llamadaParametrizada;
	}

	public int getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public PException(int idAplicacion, int tipoError, String mensajeContexto, String llamadaParametrizada, String[] tips) {
		super();
		this.idAplicacion = idAplicacion;
		this.tips = tips;
		this.tipoError = tipoError;
		this.mensajeContexto = mensajeContexto;
		this.llamadaParametrizada = llamadaParametrizada;
	}

	@Override
	public String toString() {
		if (getMensajeContexto() == null) {
			return getMensajeError() ;
		} else {
			return getMensajeContexto() + " (" + getMensajeError() + ")" ;
		}
	}

	public boolean tieneTips() {
		return getTips() != null && getTips().length > 0 ; 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PException other = (PException) obj;
		if (getMensajeError() == null) {
			if (other.getMensajeError() != null)
				return false;
		} else if (!getMensajeError().equals(other.getMensajeError())) {
			return false;
		}
		if (getMensajeContexto() == null) {
			if (other.getMensajeContexto() != null)
				return false;
		} else if (!getMensajeContexto().equals(other.getMensajeContexto())) {
			return false;
		}
		return true ;
	}

}
