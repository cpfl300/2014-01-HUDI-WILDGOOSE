package next.wildgoose.model;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	private static DataSource datasource;
	private BasicDataSource ds;

	private DataSource() throws IOException, SQLException,
			PropertyVetoException {
		ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("viewer");
		ds.setPassword("");
		ds.setUrl("jdbc:mysql://10.73.45.134:3306/wildgoose_dev");

		// the settings below are optional -- dbcp can work with defaults
		ds.setMinIdle(3);
		ds.setMaxIdle(6);
		ds.setMaxOpenPreparedStatements(180);

	}

	public static DataSource getInstance() throws IOException, SQLException,
			PropertyVetoException {
		if (datasource == null) {
			datasource = new DataSource();
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.ds.getConnection();
	}
}
