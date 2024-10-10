package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;

/**
 * The DisplayArtController handles the presentation of artwork details in the application.
 * It loads artwork information from an Art object and displays the relevant details,
 * including the title, year, category, medium, material, dimensions, and description.
 * It also displays an image of the artwork.
 */
public class DisplayArtController extends BaseController{
    @FXML
    private ImageView artImageView;
    @FXML
    private Label artTitleLabel;

    @FXML
    private TextFlow artYearLabel;
    @FXML
    private Text artYearValue;

    @FXML
    private TextFlow artCategoryLabel;
    @FXML
    private Text artCategoryValue;

    @FXML
    private TextFlow artMediumLabel;
    @FXML
    private Text artMediumValue;

    @FXML
    private TextFlow artMaterialLabel;
    @FXML
    private Text artMaterialValue;

    @FXML
    private TextFlow artDimensionsLabel;
    @FXML
    private Text artDimensionsValue;

    @FXML
    private TextFlow artDescriptionLabel;
    @FXML
    private Text artDescriptionValue;

    private ArtManager artManager;

    /**
     * Constructor for the DisplayArtController.
     * Initialises the ArtManager with a SqliteArtDAO for database operations.
     */
    public DisplayArtController() {
        this.artManager = new ArtManager(new SqliteArtDAO());
    }

    /**
     * Displays the details of the provided artwork object.
     * It loads the artwork image from the file system if available and updates the labels
     * to show the artwork's title, year, category, medium, material, dimensions, and description.
     *
     * @param art The artwork to display.
     */
    public void displayArt(Art art) {
        if (art != null) {
            // Display the artwork title if available, otherwise hide the label
            if (art.getArtTitle() != null && !art.getArtTitle().isEmpty()) {
                artTitleLabel.setText(art.getArtTitle());
                artTitleLabel.setVisible(true);
            } else {
                artTitleLabel.setVisible(false);
            }

            // Display the year if available, otherwise hide the label
            if (art.getYear() != null) {
                artYearValue.setText(art.getYear().toString());
                artYearLabel.setVisible(true);
            } else {
                artYearLabel.setVisible(false);
            }

            // Display the category if available, otherwise hide the label
            if (art.getCategory() != null && !art.getCategory().isEmpty()) {
                artCategoryValue.setText(art.getCategory());
                artCategoryLabel.setVisible(true);
            } else {
                artCategoryLabel.setVisible(false);
            }

            // Display the medium if available, otherwise hide the label
            if (art.getMedium() != null && !art.getMedium().isEmpty()) {
                artMediumValue.setText(art.getMedium());
                artMediumLabel.setVisible(true);
            } else {
                artMediumLabel.setVisible(false);
            }

            // Display the material if available, otherwise hide the label
            if (art.getMaterial() != null && !art.getMaterial().isEmpty()) {
                artMaterialValue.setText(art.getMaterial());
                artMaterialLabel.setVisible(true);
            } else {
                artMaterialLabel.setVisible(false);
            }

            // Display the dimensions if available, otherwise hide the label
            if (art.getWidth() != null || art.getHeight() != null || art.getDepth() != null) {
                StringBuilder dimensions = new StringBuilder();
                if (art.getWidth() != null) dimensions.append(art.getWidth()).append(art.getUnits() != null ? " " + art.getUnits() : "").append(" x ");
                if (art.getHeight() != null) dimensions.append(art.getHeight()).append(art.getUnits() != null ? " " + art.getUnits() : "").append(" x ");
                if (art.getDepth() != null) dimensions.append(art.getDepth()).append(art.getUnits() != null ? " " + art.getUnits() : "");
                artDimensionsValue.setText(dimensions.toString());
                artDimensionsLabel.setVisible(true);
            } else {
                artDimensionsLabel.setVisible(false);
            }

            // Display the description if available, otherwise hide the label
            if (art.getDescription() != null && !art.getDescription().isEmpty()) {
                artDescriptionValue.setText(art.getDescription());
                artDescriptionLabel.setVisible(true);
            } else {
                artDescriptionLabel.setVisible(false);
            }

            // Load and display the artwork image (if available)
            if (art.getFilePath() != null && !art.getFilePath().isEmpty()) {
                File file = new File(art.getFilePath());
                if (file.exists()) {
                    artImageView.setImage(new Image(file.toURI().toString()));
                } else {
                    artImageView.setImage(new Image("path/to/placeholder/image.png")); // Replace with a valid placeholder image path
                }
            } else {
                artImageView.setImage(new Image("path/to/placeholder/image.png")); // Replace with a valid placeholder image path
            }
        }
    }



    // REPLACE !!
    @FXML
    private void onBack(ActionEvent event) {
        // Load the "My Portfolios" view using the loadPage method from BaseController
        loadPage(event, "/com/example/addressbook/my-portfolios-view.fxml");
    }







}

