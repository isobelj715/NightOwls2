package com.example.addressbook.model;

import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IContactDAO {
    /**
     * Adds a new contact to the database.
     * @param contact The contact to add.
     */
    public void addContact(Contact contact);
    /**
     * Updates an existing contact in the database.
     * @param contact The contact to update.
     */
    public void updateContact(Contact contact);
    /**
     * Deletes a contact from the database.
     * @param contact The contact to delete.
     */
    public void deleteContact(Contact contact);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public Contact getContact(int id);
    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<Contact> getAllContacts();
}