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

            Contact newContact = new Contact(firstName, lastName, email, phone, password);
            contactManager.addContact(newContact);


            //Stage stage = (Stage) firstNameTextField.getScene().getWindow();
            //stage.close();
            // Load the login page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/login-view.fxml"));
                AnchorPane loginPane = loader.load();
                Scene loginScene = new Scene(loginPane);

                // Get the current stage and set the new scene (Login page)
                Stage stage = (Stage) firstNameTextField.getScene().getWindow();
                stage.setScene(loginScene);

                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Handles the action when the "Cancel" button is clicked
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/login-view.fxml"));
            AnchorPane loginPane = loader.load();
            Scene loginScene = new Scene(loginPane);

            // Get the current stage and set the new scene (Login page)
            Stage stage = (Stage) firstNameTextField.getScene().getWindow();
            stage.setScene(loginScene);

            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE, DEFAULT_PASSWORD);
        // Add the new contact to the database
        contactManager.addContact(newContact);

    }


}
