package pl.edu.agh.to2.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DbUtil {
 
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            	System.out.println("<db_log>: ERROR while closing connection!\n"+e);
            }
        }
    }
 
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            	System.out.println("<db_log>: ERROR while closing statement!\n"+e);
            }
        }
    }
 
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            	System.out.println("<db_log>: ERROR while closing resultSet!\n"+e);
            }
        }
    }
}