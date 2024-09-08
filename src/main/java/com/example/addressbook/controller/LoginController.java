package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.model.ContactManager;
import com.example.addressbook.model.SqliteContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;  // Import for VBox
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;
    @FXML
    public void initialize() {
        System.out.println("Create Account Button initialized: " + createAccountButton);
    }

    private ContactManager contactManager;

    public LoginController() {
        // Initialize the ContactManager with SqliteContactDAO to handle database operations
        contactManager = new ContactManager(new SqliteContactDAO());
    }

    // Handles the action when the "Log In" button is clicked
    @FXML
    private void onLogin(ActionEvent event) {
        String email = emailTextField.getText().trim();
        String password = passwordField.getText().trim();

        // Basic input validation
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill out both fields.");
            return;
        }

        // Check the database for matching credentials
        List<Contact> contacts = contactManager.getAllContacts();
        boolean isValid = false;

        for (Contact contact : contacts) {
            // Debugging output to make sure correct data is being retrieved
            System.out.println("Checking email: " + contact.getEmail() + " | password: " + contact.getPassword());

            if (contact.getEmail().trim().equals(email) && contact.getPassword().trim().equals(password)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            showAlert("Success", "Login successful!");
            // Proceed to the next screen if needed (after successful login)
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }

    // Handles the action when the "Create Account" button is clicked
    @FXML
    private void onCreateAccount(ActionEvent event) {
        try {
            // Load the "Create Account" FXML file and switch the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/createaccount-view.fxml"));

            VBox createAccountPane = loader.load();  // Use VBox since your root element is VBox in the FXML
            Scene createAccountScene = new Scene(createAccountPane);

            // Get the current stage and set the new scene (Create Account page)
            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            stage.setScene(createAccountScene);
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
}