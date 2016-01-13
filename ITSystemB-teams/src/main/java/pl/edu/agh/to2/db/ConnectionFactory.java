package pl.edu.agh.to2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String DBNAME = "teams_test"; 
    public static final String USER = "root";
    public static final String PASSWORD = "bazy";
    public static final String DRIVER_CLASS = "org.mariadb.jdbc.Driver"; 
    
     

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL+DBNAME, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("<db_log>: ERROR: Unable to Connect to Database!");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
}