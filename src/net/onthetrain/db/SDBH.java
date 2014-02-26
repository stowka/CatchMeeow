package net.onthetrain.db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author Antoine De Gieter
 *
 */
public final class SDBH {
    public static Connection connection;
    private SDBH() {
    	String url = "jdbc:mysql://localhost:3306/";
        String database = "crappybi";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "893QQY";
        try {
            Class.forName(driver).newInstance();
            SDBH.connection = (Connection)DriverManager.getConnection(url + database, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static synchronized Connection getConnection() {
        if (SDBH.connection == null) {
        	new SDBH();
        }
        return SDBH.connection;
    }
}