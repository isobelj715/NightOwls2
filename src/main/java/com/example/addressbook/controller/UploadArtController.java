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

/**
 * The UploadPortfolioController class manages the upload of new art pieces to a user's portfolio.
 * It provides functionality for creating new portfolios, uploading art, previewing images, and managing input fields.
 */
public class UploadArtController extends BaseController{

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


    @FXML
    private Button uploadButton;

    @FXML
    private Button cancelButton;



    private File selectedFile;

    private final ArtManager artManager;
    private final SqlitePortfolioDAO portfolioDAO;

    /**
     * Constructor for UploadPortfolioController. Initialises the ArtManager and PortfolioDAO
     * for managing art and portfolio data.
     */
    public UploadArtController() {
        artManager = new ArtManager(new SqliteArtDAO());
        portfolioDAO = new SqlitePortfolioDAO();
    }

    /**
     * Initialises the controller, loading existing portfolios into the ComboBox for selection.
     */
    @FXML
    public void initialize() {
        loadPortfolios();  // Load portfolios into the ComboBox
    }

    /**
     * Loads all portfolios belonging to the logged-in user into the ComboBox.
     * If no user is logged in, an error alert is shown.
     */
    private void loadPortfolios() {
        // Get the current logged-in user
        Contact loggedInUser = SessionManager.getInstance().getLoggedInUser();
        int userId = loggedInUser.getId();

        if (userId == -1) {
            showAlert("Error", "No user is logged in.");
            return;
        }

        // Fetch portfolios for the logged-in user
        List<Portfolio> portfolios = portfolioDAO.getAllPortfolio();

        // Convert to ObservableList
        ObservableList<Portfolio> portfolioList = FXCollections.observableArrayList(portfolios);

        // Set the items in the ComboBox
        portfolioComboBox.setItems(portfolioList);

        // Set up a custom CellFactory to display portfolio titles
        portfolioComboBox.setCellFactory(param -> new ListCell<Portfolio>() {
            @Override
            protected void updateItem(Portfolio item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getPortfolioName());
                }
            }
        });
        // Ensure the selected item displays correctly
        portfolioComboBox.setButtonCell(new ListCell<Portfolio>() {
            @Override
            protected void updateItem(Portfolio item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getPortfolioDescription());
                }
            }
        });
    }

    /**
     * Handles file selection and image preview when the user clicks the "Browse" button.
     * Allows users to select an image file for the art, displaying the selected image in the ImageView.
     *
     * @param actionEvent the event triggered by the "Browse" button.
     */
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

    /**
     * Handles the upload action when the user clicks the "Upload" button.
     * Gathers all input data, validates required fields, and adds the new art to the selected portfolio.
     *
     * @param actionEvent the event triggered by the "Upload" button.
     */
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

        // TODO: Apply this validation to width/height/depth as well
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

        loadPage(actionEvent, "/com/example/addressbook/my-portfolios-view.fxml");
    }


    /**
     * Handles the cancel action when the user clicks the "Cancel" button.
     * Redirects the user back to the My Portfolios view without saving any changes.
     *
     * @param actionEvent the event triggered by the "Cancel" button.
     */
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        loadPage(actionEvent, "/com/example/addressbook/my-portfolios-view.fxml");

    }

}
