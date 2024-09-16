package com.example.addressbook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteConfirmationController {

    @FXML
    private Button yesButton;

    @FXML
    private Button cancelButton;

    private boolean confirmed;

    @FXML
    public void initialize() {
        // Set up button actions
        yesButton.setOnAction(event -> {
            confirmed = true;
            closeDialog();
        });

        cancelButton.setOnAction(event -> {
            confirmed = false;
            closeDialog();
        });
    }

    // Returns whether the deletion was confirmed
    public boolean isConfirmed() {
        return confirmed;
    }

    // Close the dialog
    private void closeDialog() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }
}
