package ar.pegasus.framework.dao.api.local;

import java.sql.Date;
import java.sql.Timestamp;

import javax.ejb.Local;

import ar.pegasus.framework.componentes.PException;

@Local
public interface DateDAOLocal {

	public Timestamp getServerTimestamp() throws PException ;

	public Date getServerDate() throws PException;
}
