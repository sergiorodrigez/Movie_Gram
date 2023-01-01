package main.java.dao;

import main.java.configuration.PropertiesISW;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {
	private static ConnectionDAO connectionDAO;
	private Connection con;
	
	private ConnectionDAO() {		
		String url = PropertiesISW.getInstance().getProperty("ddbb.connection");
        String user = PropertiesISW.getInstance().getProperty("ddbb.user");
        String password = PropertiesISW.getInstance().getProperty("ddbb.password");
	    try {
	    	con = DriverManager.getConnection(url, user, password);
	    }catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
	    
	}

	public static ConnectionDAO getInstance() {
		if (connectionDAO == null) {
			connectionDAO=new ConnectionDAO();
		}
		return connectionDAO;
	}
	
	public Connection getConnection() {
		return con;
	}

}