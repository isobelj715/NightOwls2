package com.example.addressbook.model;


import com.example.addressbook.model.Contact;

public class SessionManager {

    // The single instance of the SessionManager
    private static SessionManager instance;

    // The logged-in user's data
    private Contact loggedInUser;

    // Private constructor to prevent direct instantiation
    private SessionManager() {}

    // Get the instance of the SessionManager (singleton pattern)
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set the logged-in user when they successfully log in
    public void setLoggedInUser(Contact user) {
        this.loggedInUser = user;
    }

    // Get the logged-in user's data
    public Contact getLoggedInUser() {
        return loggedInUser;
    }

    // Clear the session data (e.g., on logout)
    public void clearSession() {
        loggedInUser = null;
    }
}