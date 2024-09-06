package com.example.addressbook.model;
/**
 * A simple model class representing a contact with a first name, last name, email, and phone number.
 */
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    /**
     * Constructs a new Contact with the specified first name, last name, email, and phone number.
     *
     * @param firstName The first name of the contact.
     * @param lastName The last name of the contact.
     * @param email The email of the contact.
     * @param phone The phone number of the contact.
     * @param password The password number of the contact.
     */
    public Contact(String firstName, String lastName, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Returns the ID of the contact.
     *
     * @return The ID of the contact.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the contact.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the first name of the contact.
     *
     * @return The first name of the contact.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the contact.
     *
     * @return The last name of the contact.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the email of the contact.
     *
     * @return The email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the contact.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the contact.
     *
     * @return The phone number of the contact.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param phone The phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the full name of the contact.
     *
     * @return The full name of the contact.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }


    /**
     * Returns the password of the contact.
     *
     * @return The password of the contact.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

