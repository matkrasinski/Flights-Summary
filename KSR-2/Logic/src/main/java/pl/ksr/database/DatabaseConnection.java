package pl.ksr.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private Connection connection;
    private final Properties properties;
    private static final class InstanceHolder {
        private static final DatabaseConnection instance = new DatabaseConnection();
    }

    private DatabaseConnection() {
        properties = loadProperties();
    }

    public static DatabaseConnection getInstance() {
        return InstanceHolder.instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                throw new IOException("Unable to find config.properties file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(props);
        return props;
    }
}