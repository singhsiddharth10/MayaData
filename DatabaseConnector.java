package test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
//	JDBC Driver
	static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	//Database Url
	static final String dbUrl = "jdbc:mysql://localhost:3306/maya_data";
	//Database Credentials
	static final String user = "root";
	static final String pass = "123";
	
	private static java.sql.Connection conn;
	
	public static Connection getConnection() throws Exception{
		try {
			//Registering the Driver
			Class.forName(jdbcDriver);
			//Open a connection
			conn = DriverManager.getConnection(dbUrl,user,pass);
			
			//Returning the connection
			return conn;
		}catch(Exception e) {
			//Handling Exception
			e.printStackTrace();
		}
		return null;
	}

}
