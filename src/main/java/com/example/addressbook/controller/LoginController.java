package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.model.ContactManager;
import com.example.addressbook.model.SessionManager;
import com.example.addressbook.model.SqliteContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;  // Import for VBox
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The LoginController manages the login and account creation process in the application.
 * It validates user credentials, allows navigation between the login screen and the
 * account creation screen, and manages session data for logged-in users.
 */
public class LoginController extends BaseController{

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    private ContactManager contactManager;

    /**
     * Initialises the controller and the ContactManager for handling contact-related
     * database operations. The ContactManager is initialised with a SqliteContactDAO.
     */
    public LoginController() {
        contactManager = new ContactManager(new SqliteContactDAO());
    }

    /**
     * Handles the action when the "Log In" button is clicked.
     * It validates the user's email and password, checks the database for the user,
     * and if valid, logs the user into the application and navigates to the portfolios view.
     *
     * @param event the event triggered when the login button is clicked.
     */
    @FXML
    private void onLogin(ActionEvent event) {
        String email = emailTextField.getText().trim();
        String password = passwordField.getText().trim();

        // Basic input validation
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill out both fields.");
            return;
        }

        // Retrieve contact by email
        Contact contact = contactManager.getContactByEmail(email);

        if (contact != null && contact.getPassword().trim().equals(password)) {

            // Store the logged-in user in SessionManager
            SessionManager.getInstance().setLoggedInUser(contact);

            try {
                // switch the address between my-portfolios-view or upload-art-view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/my-portfolios-view.fxml"));

                AnchorPane myArtPane = loader.load(); // Anchorpane for portfolios view and Vbox for upload art
                Scene myArtScene = new Scene(myArtPane);

                // Get the current stage and set the new scene (My Art page)
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(myArtScene);

                // Disable the fullscreen exit hint
                stage.setFullScreenExitHint("");
                // Make the window fullscreen
                stage.setFullScreen(true);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }

    /**
     * Handles the action when the "Create Account" button is clicked.
     * It navigates to the account creation screen by loading the relevant FXML file.
     *
     * @param event the event triggered when the create account button is clicked.
     */
    @FXML
    private void onCreateAccount(ActionEvent event) {
        // Load the "Create Account" view using the loadPage method from BaseController
        loadPage(event, "/com/example/addressbook/createaccount-view.fxml");
    }
}