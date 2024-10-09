package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * The DisplayArtController handles the presentation of artwork details in the application.
 * It loads artwork information from an Art object and displays the relevant details,
 * including the title, year, category, medium, material, dimensions, and description.
 * It also displays an image of the artwork.
 */
public class DisplayArtController {
    @FXML
    private ImageView artImageView;
    @FXML
    private Label artTitleLabel;
    @FXML
    private Label artYearLabel;
    @FXML
    private Label artCategoryLabel;
    @FXML
    private Label artMediumLabel;
    @FXML
    private Label artMaterialLabel;
    @FXML
    private Label artDimensionsLabel;
    @FXML
    private Label artDescriptionLabel;
    @FXML
    private Label portfolioTitleLabel;//This will need to change

    /**
     * Sets the title of the portfolio (or artwork) to display.
     *
     * @param portfolioTitle the title of the portfolio to set.
     */
    public void setPortfolioTitle(String portfolioTitle) {// WIll need to change this to be set artwork title
        portfolioTitleLabel.setText(portfolioTitle);
    }

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

            System.out.println("Art details:");
            System.out.println("Title: " + art.getArtTitle());
            System.out.println("Year: " + art.getYear());
            System.out.println("Category: " + art.getCategory());
            System.out.println("Medium: " + art.getMedium());
            System.out.println("Material: " + art.getMaterial());
            System.out.println("Width: " + art.getWidth());
            System.out.println("Height: " + art.getHeight());
            System.out.println("Depth: " + art.getDepth());
            System.out.println("Units: " + art.getUnits());
            System.out.println("Description: " + art.getDescription());

            // Load and display the artwork image
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

            // Set the art information, only if available
            artTitleLabel.setText(art.getArtTitle() != null ? art.getArtTitle() : "Unknown Title");
            artYearLabel.setText(art.getYear() != null ? "Year: " + art.getYear() : "");
            artCategoryLabel.setText(art.getCategory() != null ? "Category: " + art.getCategory() : "");
            artMediumLabel.setText(art.getMedium() != null ? "Medium: " + art.getMedium() : "");
            artMaterialLabel.setText(art.getMaterial() != null ? "Material: " + art.getMaterial() : "");

            // Handle dimensions if any (show width, height, depth only if present)
            if (art.getWidth() != null || art.getHeight() != null || art.getDepth() != null) {
                StringBuilder dimensions = new StringBuilder("Dimensions: ");
                if (art.getWidth() != null) dimensions.append(art.getWidth()).append("").append(art.getUnits() != null ? art.getUnits() : "units").append(" x ");
                if (art.getHeight() != null) dimensions.append(art.getHeight()).append("").append(art.getUnits() != null ? art.getUnits() : "units").append(" x ");
                if (art.getDepth() != null) dimensions.append(art.getDepth()).append("").append(art.getUnits() != null ? art.getUnits() : "units").append("");
                artDimensionsLabel.setText(dimensions.toString());
            } else {
                artDimensionsLabel.setText("Dimensions: "); // Don't show dimensions if all fields are empty
            }

            artDescriptionLabel.setText(art.getDescription() != null ? "Description: " + art.getDescription() : "");
        }
    }
}
