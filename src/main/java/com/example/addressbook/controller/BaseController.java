package com.example.addressbook.controller;

import com.example.addressbook.model.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    /**
     * Utility method to show alert dialogs.
     *
     * @param title   the title of the alert dialog.
     * @param message the message content of the alert dialog.
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Retrieves the ID of the currently logged-in user.
     *
     * @return the logged-in user ID, or {@code -1} if no user is logged in.
     */
    protected int getLoggedInUserId() {
        if (SessionManager.getInstance().getLoggedInUser() == null) {
            return -1; // Indicates that no user is logged in
        }
        return SessionManager.getInstance().getLoggedInUser().getId();
    }


    /**
     * Loads a new page by changing the scene for the current stage.
     *
     * @param event     The event that triggered the scene change (used to retrieve the current stage).
     * @param fxmlPath  The path to the FXML file to load.
     */
    protected void loadPage(javafx.event.ActionEvent event, String fxmlPath) {
        try {
            Parent newSceneRoot = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newSceneRoot));

            if (!stage.isFullScreen()) {
                stage.setFullScreen(true);  // Enter fullscreen if not already in fullscreen
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
