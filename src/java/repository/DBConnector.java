package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.descriptor.AnimalDescriptor;
import models.descriptor.PlantDescriptor;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306";
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

    // Data Retriever
    public List<AnimalDescriptor> getAllAnimalDescriptors() {
        List<AnimalDescriptor> list = new ArrayList<>();
        String sql = "SELECT species_name, base_speed, max_energy, mating_cap, description FROM species_animal";
        // Jalankan JDBC PreparedStatement & ResultSet seperti biasa...
        // list.add(new AnimalDescriptor(...));
        return list;
    }

    // Ambil semua data dari tabel tumbuhan
    public List<PlantDescriptor> getAllPlantDescriptors() {
        List<PlantDescriptor> list = new ArrayList<>();
        String sql = "SELECT plant_name, energy_gained, growth_rate, description FROM species_plant";
        // Jalankan JDBC PreparedStatement & ResultSet seperti biasa...
        return list;
    }    
}
