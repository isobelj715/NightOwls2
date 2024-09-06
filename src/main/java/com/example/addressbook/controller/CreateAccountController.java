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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAccountController {
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

    public CreateAccountController() {
        // Initialize the ContactManager with the SqliteContactDAO to handle database operations
        contactManager = new ContactManager(new SqliteContactDAO());
    }

    // Handles the action when the "Create Account" button is clicked
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
            // Proceed with account creation logic (e.g., store the data, etc.)


            showAlert("Success", "Account created successfully!");
            // close the window or navigate to the next screen
            Stage stage = (Stage) firstNameTextField.getScene().getWindow();
            stage.close();
        }
    }

    // Handles the action when the "Cancel" button is clicked
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        // Close the window without saving anything
        Stage stage = (Stage) firstNameTextField.getScene().getWindow();
        stage.close();
    }

    // Utility method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the action of adding a new contact.
     * Adds a new contact with default values to the database.
     */

    // USE ****************************************************
    // ADD DEFAULT PASSWORD TO CONSTRUCTOR!! and then uncomment
    @FXML
    private void onCreateAccount() {
        // Default values for a new contact
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Contact";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        final String DEFAULT_PASSWORD = "";
        Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE); //DEFAULT_PASSWORD);
        // Add the new contact to the database
        contactManager.addContact(newContact);

    }


}
