package com.example.addressbook.controller;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.model.Contact;
import com.example.addressbook.model.ContactManager;
import com.example.addressbook.model.SqliteContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The CreateAccountController handles the account creation logic within the application.
 * It manages user input, validates the form, and performs the operation of adding
 * new contacts to the database using the ContactManager.
 */
public class CreateAccountController extends BaseController{
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private PasswordField passwordField;

    private final ContactManager contactManager;

    /**
     * Constructor for CreateAccountController.
     * Initialises the ContactManager with a SqliteContactDAO for database operations.
     */
    public CreateAccountController() {
        // Initialise the ContactManager with the SqliteContactDAO to handle database operations
        contactManager = new ContactManager(new SqliteContactDAO());
    }

    /**
     * Handles the action when the "Create Account" button is clicked.
     * Retrieves user input from text fields, performs basic validation,
     * and if valid, adds a new contact to the database.
     *
     * @param actionEvent the event triggered by clicking the "Create Account" button.
     */
    @FXML
    public void onCreateAccount(ActionEvent actionEvent) {
        // Get the input from the text fields
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String password = passwordField.getText();

        // Basic input validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill out all fields.");
        } else {

            Contact newContact = new Contact(firstName, lastName, email, phone, password);
            contactManager.addContact(newContact);

            // Load the login page using the loadPage method from BaseController
            loadPage(actionEvent, "/com/example/addressbook/login-view.fxml");

        }
    }

    /**
     * Handles the action when the "Cancel" button is clicked.
     * Returns the user to the login view without making changes.
     *
     * @param actionEvent the event triggered by clicking the "Cancel" button.
     */
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        loadPage(actionEvent, "/com/example/addressbook/login-view.fxml");
    }

    /**
     * Handles the action of adding a new contact with default values.
     * This method can be triggered from the UI for test purposes.
     */
    @FXML
    private void onCreateAccount() {
        // Default values for a new contact
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Contact";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        final String DEFAULT_PASSWORD = "";
        Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE, DEFAULT_PASSWORD);
        // Add the new contact to the database
        contactManager.addContact(newContact);

    }


}
