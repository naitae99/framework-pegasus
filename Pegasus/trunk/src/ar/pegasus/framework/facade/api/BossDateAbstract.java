package ar.pegasus.framework.facade.api;

import java.sql.Date;
import java.sql.Timestamp;

import ar.pegasus.framework.componentes.PException;

public interface BossDateAbstract {

	public Date getServerDate() throws PException ;

	public Timestamp getServerTimestamp() throws PException ;

}
