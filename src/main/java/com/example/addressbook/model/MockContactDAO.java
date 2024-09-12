package com.example.addressbook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the IContactDAO interface that uses an in-memory list of contacts.
 */
public class MockContactDAO implements IContactDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public final ArrayList<Contact> contacts = new ArrayList<>();
    private int autoIncrementedId = 0;

    @Override
    public void addContact(Contact contact) {
        contact.setId(autoIncrementedId);
        autoIncrementedId++;
        contacts.add(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contact.getId()) {
                contacts.set(i, contact);
                break;
            }
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        contacts.remove(contact);
    }

    @Override
    public Contact getContact(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }


    /**
     * Retrieves a contact by email.
     *
     * @param email The email of the contact to retrieve.
     * @return The contact with the matching email, or null if no contact is found.
     */
    @Override
    public Contact getContactByEmail(String email) {
        for (Contact contact : contacts) {
            if (contact.getEmail().equalsIgnoreCase(email)) {
                return contact;
            }
        }
        return null;
    }
}