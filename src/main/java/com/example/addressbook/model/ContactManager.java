package com.example.addressbook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages the operations of contacts in the address book.
 * It uses an instance of IContactDAO to perform CRUD operations on contacts.
 */
public class ContactManager {
    private IContactDAO contactDAO;

    /**
     * Constructs a new ContactManager with the specified IContactDAO.
     *
     * @param contactDAO The data access object to be used for contact operations.
     */
    public ContactManager(IContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    /**
     * Searches for contacts that match the given query.
     *
     * @param query The query to match contacts.
     * @return A list of contacts that match the query.
     */
    public List<Contact> searchContacts(String query) {
        return contactDAO.getAllContacts()
                .stream()
                .filter(contact -> isContactMatched(contact, query))
                .toList();
    }

    /**
     * Checks if a contact matches the given query.
     *
     * @param contact The contact to check.
     * @param query The query to match.
     * @return true if the contact matches the query, false otherwise.
     */
    private boolean isContactMatched(Contact contact, String query) {
        if (query == null || query.isEmpty()) return true;
        query = query.toLowerCase();
        String searchString = contact.getFullName()
                + " " + contact.getEmail()
                + " " + contact.getPhone();
        return searchString.toLowerCase().contains(query);
    }

    /**
     * Adds a new contact.
     *
     * @param contact The contact to add.
     */
    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    /**
     * Deletes a contact.
     *
     * @param contact The contact to delete.
     */
    public void deleteContact(Contact contact) {
        contactDAO.deleteContact(contact);
    }

    /**
     * Updates a contact.
     *
     * @param contact The contact to update.
     */
    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    /**
     * Returns all contacts.
     *
     * @return A list of all contacts.
     */
    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}