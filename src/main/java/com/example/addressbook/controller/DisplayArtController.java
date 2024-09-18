package com.example.addressbook.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class DisplayArtController {

    // Art Upload Section
    @FXML
    private ImageView imagePreview;

    @FXML
    private TextField filePathTextField;

    private File selectedFile;


    // Handle file selection and image preview
    @FXML
    public void onBrowseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Art File");

        // Optional file extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) filePathTextField.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            filePathTextField.setText(selectedFile.getAbsolutePath());

            // Attempt to load the image and display it in the preview
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imagePreview.setImage(image);  // Update the imagePreview with the selected image
            } catch (Exception e) {
                showAlert("Error", "Selected file is not a valid image.");
            }





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
