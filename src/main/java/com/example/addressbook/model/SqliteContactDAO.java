package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A SQLite implementation of the IContactDAO interface that handles
 * the CRUD operations for the Contact class with the SQLite database
 * using JDBC.
 */
public class SqliteContactDAO implements IContactDAO {
    private Connection connection;

    /**
     * Constructs a new SqliteContactDAO with a connection to the SQLite database,
     * and creates the contacts table if it does not exist.
     */
    public SqliteContactDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
        addPasswordColumnIfNotExists();
    }

    // Add the password column if it does not already exist
    private void addPasswordColumnIfNotExists() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, "contacts", "password");
            if (!rs.next()) {
                Statement statement = connection.createStatement();
                String alterTableQuery = "ALTER TABLE contacts ADD COLUMN password VARCHAR";
                statement.execute(alterTableQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS contacts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addContact(Contact contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO contacts (firstName, lastName, phone, email, password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.setString(5, contact.getPassword());
            statement.executeUpdate();
            // Set the id of the new contact
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                contact.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateContact(Contact contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE contacts SET firstName = ?, lastName = ?, phone = ?, email = ?, password = ? WHERE id = ?");
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.setString(5, contact.getPassword());
            statement.setInt(6, contact.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE id = ?");
            statement.setInt(1, contact.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contact getContact(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Contact contact = new Contact(firstName, lastName, phone, email, password);
                contact.setId(id);
                return contact;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM contacts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Contact contact = new Contact(firstName, lastName, phone, email, password);
                contact.setId(id);
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }
}