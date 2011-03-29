package ar.pegasus.framework.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Destinatario")
public class Destinatario implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idDestinatario;
    private int tipoDestinatario;
    private String direccionMail;

    /** Método constructor */
    public Destinatario() {
    }

    /**
     * 
     * @param tipoDest DestinatarioRequest.PARA,DestinatarioRequest.CC... 
     * @param email string, ej asdf@mail.com
     */
    public Destinatario(int tipoDest, String email) {
		this.direccionMail = email;
		this.tipoDestinatario = tipoDest;
	}

	/**
     * Devuelve el <b>id</b> del destinatario.
     * @return idDestinatario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="P_IdDestinatario")
    public int getIdDestinatario() {
        return idDestinatario;
    }

    /**
     * Setea el <b>id</b> del destinatario.
     * @param idDestinatario
     */
    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    /**
     * Devuelve el <b>tipo</b> de destinatario. Puede ser TO = 0 / CC = 1 / CCO = 2.
     * @return tipoDestinatario
     */
	@Column(name="A_TipoDestinatario")
    public int getTipoDestinatario() {
        return tipoDestinatario;
    }

    /**
     * Setea el <b>tipo</b> de destinatario. Puede ser PARA = 0 / CC = 1 / CCO = 2.
     * @param tipoDestinatario
     */
    public void setTipoDestinatario(int tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    /**
     * Devuelve la <b>dirección</b> de Mail.
     * @return direccionMail
     */
	@Column(name="A_DireccionMail")
    public String getDireccionMail() {
        return direccionMail;
    }

    /**
     * Setea la <b>dirección</b> de Mail.
     * @param direccionMail
     */
    public void setDireccionMail(String direccionMail) {
        this.direccionMail = direccionMail;
    }

    public String toString () {
    	return getDireccionMail() ; 
    }
}