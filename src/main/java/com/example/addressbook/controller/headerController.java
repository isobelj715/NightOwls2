package com.example.addressbook.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.event.ActionEvent;

public class headerController extends BaseController{
    @FXML
    public void handleButtonClick(ActionEvent event) {
        // Determine which button was clicked by its fx:id
        String buttonId = ((Node) event.getSource()).getId();

        // Determine which FXML file to load based on the button clicked
        String fxmlFile = fileLocation(buttonId);

        // Load the corresponding FXML file and switch scenes
        loadPage(event, fxmlFile);


    }

    public String fileLocation(String id){

        String fxmlFile = "";
        switch (id) {
            case "headerHome":
                fxmlFile = "/com/example/addressbook/my-portfolios-view.fxml";
                break;
            case "headerUpload":
                fxmlFile = "/com/example/addressbook/upload-art-view.fxml";
                break;
            case "headerProfile":
                fxmlFile = "/com/example/addressbook/profile-views.fxml";
                break;
        }
        return fxmlFile;
    }
}

