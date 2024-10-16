package com.example.addressbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class landingHomeController extends BaseController {
    @FXML
    public void handleButtonClick(ActionEvent event) {
        // Determine which button was clicked by its fx:id
        String buttonId = ((Node) event.getSource()).getId();
        // Determine which FXML file to load based on the button clicked
        String fxmlFile = "";
        switch (buttonId) {
            case "login":
                fxmlFile = "/com/example/addressbook/login-view.fxml";
                break;
            case "signup":
                fxmlFile = "/com/example/addressbook/createaccount-view.fxml";
                break;
//                when pages created add in
//            case "headerProfile":
//                fxmlFile = "/com/example/arttasker/profile-view.fxml";
//                break;
        }
        // Load the corresponding FXML file and switch scenes
        loadPage(event, fxmlFile);
    }
}
