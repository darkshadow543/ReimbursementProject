package com.revature.project1.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class ConnectionUtil {

	private static Connection server = null;
	private static final Logger log = (Logger) LogManager.getLogger(ConnectionUtil.class); 
	
	public ConnectionUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLog() {
		return log;
	}
	
	public static Connection getServer() throws Exception {
		if (ConnectionUtil.server != null) {
			return ConnectionUtil.server;
		}
		Connection dbConnection = null;
		Properties server = new Properties();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			InputStream config = ConnectionUtil.class.getResourceAsStream("/server.properties");
			server.load(config);
			
			String endpoint = server.getProperty("db.url");
			String user = server.getProperty("db.user");
			String password = server.getProperty("db.password");
			dbConnection = DriverManager.getConnection(endpoint, user, password);
			
			log.debug("Connected");;
			ConnectionUtil.server = dbConnection;
			return ConnectionUtil.server;
		} catch (IOException e) {
			log.error("Failed to Open File");
			throw new Exception("Unable to open file");
		} catch (SQLException e){
			System.out.println(e.getMessage());
			log.error("Failed to connect to Database");
			throw new Exception("Unable to connect to database");
		}
	}
}
