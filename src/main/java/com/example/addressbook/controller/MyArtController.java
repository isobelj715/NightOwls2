package com.example.addressbook.controller;

import com.example.addressbook.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class MyArtController {

    @FXML
    private Label userGreetingLabel;

    @FXML
    public void initialize() {
        // Access the logged-in user from the SessionManager
        Contact loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser != null) {
            // Display user-specific information, like a greeting message
            userGreetingLabel.setText("Welcome, " + loggedInUser.getFirstName() + "!");
        }
        else {
        userGreetingLabel.setText("Welcome!");
        }
    }
}