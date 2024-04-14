package com.example.addressbook.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton class that manages the SQLite database connection.
 */
public class SqliteConnection {
    private static Connection instance = null;

    /**
     * Private constructor to prevent instantiation.
     * It establishes a connection to the SQLite database.
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:contacts.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Returns the singleton instance of the SQLite database connection.
     * If the instance is null, it creates a new SqliteConnection.
     *
     * @return The singleton instance of the SQLite database connection.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}