package com.example.addressbook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The DeleteConfirmationController handles the confirmation dialog for deletion operations.
 * It provides options for the user to confirm or cancel the deletion and manages the dialog's state.
 */
public class DeleteConfirmationController {

    @FXML
    private Button yesButton;

    @FXML
    private Button cancelButton;

    private boolean confirmed;

    /**
     * Initialises the controller and sets up the button actions for the confirmation dialog.
     * The "Yes" button confirms the deletion, and the "Cancel" button cancels the deletion.
     */
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

    /**
     * Returns whether the deletion has been confirmed by the user.
     *
     * @return {@code true} if the deletion is confirmed, otherwise {@code false}.
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Closes the confirmation dialog.
     * This method is called after the user confirms or cancels the deletion.
     */
    private void closeDialog() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }
}
