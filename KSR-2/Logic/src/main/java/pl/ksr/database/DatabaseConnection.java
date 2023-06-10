package pl.ksr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static String DATABASE_URL = "jdbc:postgresql://localhost:5432/skyflow";
    public static String DATABASE_USERNAME = "root";
    public static String DATABASE_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection;
        try {
            String url = DATABASE_URL;
            String user = DATABASE_USERNAME;
            String password = DATABASE_PASSWORD;
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}