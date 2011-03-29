package ar.pegasus.framework.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface InstantiateFromResultSet {

	public void setData(ResultSet rs) throws SQLException ;

}
