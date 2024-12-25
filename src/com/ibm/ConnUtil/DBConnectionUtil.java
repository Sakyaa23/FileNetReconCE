package com.ibm.ConnUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.apache.commons.dbutils.DbUtils;
import com.ibm.Util.ReadProperties;

public class DBConnectionUtil {

	Properties prop;
	ReadProperties properties = new ReadProperties();
	Connection conn = null;

	public Connection  dbConnectionData() throws Exception {
		prop = properties.readPropertiesFile();
		getCredentials td = new getCredentials();
		String JDBC_DRIVER = prop.getProperty("jdbc_driver");
		String DB_URL = prop.getProperty("db_url");
		String USER = prop.getProperty("user");
		String PASS = td.decrypt(prop.getProperty("pass"));
		Connection conn = null;
		DbUtils.loadDriver(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;

	}
	public Connection  dbConnectionDataFHPS() throws Exception {
		prop = properties.readPropertiesFile();
		getCredentials td = new getCredentials();
		String JDBC_DRIVER = prop.getProperty("jdbc_driver");
		String DB_URL_FHPS = prop.getProperty("db_url_FHPS");
		String USER_FHPS = prop.getProperty("user_FHPS");
		String PASS_FHPS = td.decrypt(prop.getProperty("pass_FHPS"));
		Connection conn1 = null;
		DbUtils.loadDriver(JDBC_DRIVER);
		conn1 = DriverManager.getConnection(DB_URL_FHPS, USER_FHPS, PASS_FHPS);
		return conn1;

	}
}

