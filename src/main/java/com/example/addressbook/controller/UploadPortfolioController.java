package com.example.addressbook.controller;

import com.example.addressbook.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class UploadPortfolioController {

    // Portfolio Section
    @FXML
    private ComboBox<Portfolio> portfolioComboBox;
    @FXML
    private TextField portfolioNameTextField;
    @FXML
    private TextArea portfolioDescriptionTextArea;

    // Art Upload Section
    @FXML
    private ImageView imagePreview;
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
    private final SqlitePortfolioDAO portfolioDAO;

    public UploadPortfolioController() {
        artManager = new ArtManager(new SqliteArtDAO());
        portfolioDAO = new SqlitePortfolioDAO();
    }

    // Initialize portfolios in the ComboBox when the UI is loaded
    @FXML
    public void initialize() {
        loadPortfolios();  // Load portfolios into the ComboBox
    }

    // Load all portfolios into the ComboBox
    private void loadPortfolios() {
        List<Portfolio> portfolios = portfolioDAO.getAllPortfolio();  // Fetch all portfolios from the database
        ObservableList<Portfolio> portfolioList = FXCollections.observableArrayList(portfolios);
        portfolioComboBox.setItems(portfolioList);
    }

    // Handle the creation of a new portfolio when the user clicks the "Create New Portfolio" button
    @FXML
    public void onCreatePortfolio(ActionEvent event) {
        String portfolioName = portfolioNameTextField.getText().trim();
        String portfolioDescription = portfolioDescriptionTextArea.getText().trim();

        if (portfolioName.isEmpty()) {
            showAlert("Error", "Portfolio name cannot be empty.");
            return;
        }

        // Get the current logged-in user
        Contact loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            showAlert("Error", "No user is logged in.");
            return;
        }

        // Create a new portfolio with name, description, and contact ID
        Portfolio newPortfolio = new Portfolio(portfolioName, portfolioDescription, loggedInUser.getId());
        portfolioDAO.addPortfolio(newPortfolio);  // Add the new portfolio to the database
        loadPortfolios();  // Reload the portfolios into the ComboBox
        portfolioComboBox.getSelectionModel().select(newPortfolio);  // Select the newly created portfolio
        showAlert("Success", "Portfolio created successfully!");
    }

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

    // Handle the upload action
    @FXML
    public void onUpload(ActionEvent actionEvent) {
        // Get required inputs
        String artTitle = artTitleTextField.getText();
        String yearString = yearTextField.getText();

        // Get the selected portfolio
        Portfolio selectedPortfolio = portfolioComboBox.getSelectionModel().getSelectedItem();

        // Validate required inputs
        if (artTitle.isEmpty() || yearString.isEmpty() || selectedFile == null) {
            showAlert("Error", "Art Title, Year, and File are required.");
            return;
        }

        if (selectedPortfolio == null) {
            showAlert("Error", "Please select a portfolio.");
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
        newArt.setPortfolioId(selectedPortfolio.getId());

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