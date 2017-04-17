package ua.dominos.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static final Logger LOG = Logger.getLogger(DbConnection.class);

    private static Connection connection;
    private static String username;
    private static String password;
    private static String connectionURL;


    public static Connection getConnection() {
        try {
            if (connection == null) {
                getProperties();
            }
            connection = DriverManager.getConnection(connectionURL, username, password);
        } catch (SQLException e) {
            LOG.error(e);
        }
        return connection;
    }

    private static void getProperties() {
        try {
            Properties propertiesFile = new Properties();
            propertiesFile.load(DbConnection.class.getClassLoader().getResourceAsStream("database.properties"));
            String driver = propertiesFile.getProperty("jdbc.drivers");
            connectionURL = propertiesFile.getProperty("jdbc.url");
            username = propertiesFile.getProperty("jdbc.username");
            password = propertiesFile.getProperty("jdbc.password");
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            LOG.error("Attempt to read from properties file was failed", e);
        }
    }
}
