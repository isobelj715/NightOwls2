package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UploadController {

    @FXML
    private TextField artTitleTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField mediumContainer;
    @FXML
    private TextField materialTextField;
    @FXML
    private TextField widthTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private TextField depthTextField;
    @FXML
    private TextField unitsTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextField filePathTextField;

    private File selectedFile;

    private final ArtManager artManager;

    public UploadController() {
        artManager = new ArtManager(new SqliteArtDAO());
    }

    // Handles the file selection
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
        }
    }

    // Handles the upload action
    @FXML
    public void onUpload(ActionEvent actionEvent) {
        // Get required inputs
        String artTitle = artTitleTextField.getText();
        String yearString = yearTextField.getText();

        // Validate required inputs
        if (artTitle.isEmpty() || yearString.isEmpty() || selectedFile == null) {
            showAlert("Error", "Art Title, Year, and File are required.");
            return;
        }

        Integer year;
        try {
            year = Integer.valueOf(yearString);
        } catch (NumberFormatException e) {
            showAlert("Error", "Year must be a valid number.");
            return;
        }

        // Create the Art object with the required fields
        Art newArt = new Art(artTitle, year);

        // Set optional fields
        newArt.setCategory(categoryTextField.getText());
        newArt.setMedium(mediumContainer.getText());
        newArt.setMaterial(materialTextField.getText());

        if (!widthTextField.getText().isEmpty()) {
            newArt.setWidth(Integer.valueOf(widthTextField.getText()));
        }
        if (!heightTextField.getText().isEmpty()) {
            newArt.setHeight(Integer.valueOf(heightTextField.getText()));
        }
        if (!depthTextField.getText().isEmpty()) {
            newArt.setDepth(Integer.valueOf(depthTextField.getText()));
        }

        newArt.setUnits(unitsTextField.getText());
        newArt.setDescription(descriptionTextField.getText());

        // Set the file path for the selected art file
        newArt.setFilePath(selectedFile.getAbsolutePath());

        // Add the new art to the database
        artManager.addArt(newArt);

        showAlert("Success", "Art uploaded successfully!");
        Stage stage = (Stage) artTitleTextField.getScene().getWindow();
        stage.close();
    }

    // Cancel action
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) artTitleTextField.getScene().getWindow();
        stage.close();
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