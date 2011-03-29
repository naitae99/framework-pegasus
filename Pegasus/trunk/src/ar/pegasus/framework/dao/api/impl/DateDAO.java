package ar.pegasus.framework.dao.api.impl;

import java.sql.Date;
import java.sql.Timestamp;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import ar.pegasus.framework.componentes.PException;
import ar.pegasus.framework.dao.api.local.DateDAOLocal;

@Stateless
@Name("dateDAO")
public class DateDAO extends GenericDAO implements DateDAOLocal {

	public Timestamp getServerTimestamp() throws PException {
		Object obj = getEntityManager().createNativeQuery("SELECT CURRENT_TIMESTAMP").getSingleResult();
		return (Timestamp)obj;
	}

	public Date getServerDate() throws PException {
		Timestamp obj = (Timestamp)getEntityManager().createNativeQuery("SELECT CAST(CONVERT(Varchar(10),CURRENT_TIMESTAMP,112) as DateTime)").getSingleResult();
		return new Date(obj.getTime());
	}

}