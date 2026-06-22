package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DATABASE = "encyclopedia";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Single Instance to Store Connection
    private static Connection connection = null;

    // Connections
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DBConnector.class) {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("[DB INFO] Success to Connect into SQL");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[DB ERR] Failed to Connect: " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB INFO] Success to Close SQL Connection");
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to Close: " + e.getMessage());
        }
    }  
}
