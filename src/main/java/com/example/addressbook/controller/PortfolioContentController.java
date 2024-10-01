package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

public class PortfolioContentController {

    @FXML
    private Label portfolioTitleLabel;
    @FXML
    private Label portfolioDescriptionLabel;
    @FXML
    private GridPane artGrid;

    public void setPortfolioTitle(String portfolioTitle) {
        portfolioTitleLabel.setText(portfolioTitle);
    }

    public void setPortfolioDescription(String portfolioDescription){
        portfolioDescriptionLabel.setText(portfolioDescription);
    }

    public void loadPortfolioArtworks(List<Art> artworks){
        int columns = 3;  // Number of columns in the grid
        int row = 0;      // Current row
        int col = 0;      // Current column

        for (Art art : artworks) {
            // Create an ImageView for the artwork's image (assuming Art class has a getImageURL method)
            ImageView imageView = new ImageView(new Image(art.getFilePath()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            // Create a label for the artwork's name or title
            Label artLabel = new Label(art.getArtTitle());

            // Create a VBox to contain the ImageView and Label
            VBox artBox = new VBox(imageView, artLabel);
            artBox.setSpacing(10);  // Optional: spacing between image and label

            // Add the VBox to the GridPane
            artGrid.add(artBox, col, row);

            // Update column and row for the next artwork
            col++;
            if (col == columns) {
                col = 0;
                row++;
            }
        }


//This code in a for loop for each artwork
        // Load and display the artwork image
//        if (art.getFilePath() != null && !art.getFilePath().isEmpty()) {
//            File file = new File(art.getFilePath());
//            if (file.exists()) {
//                artImageView.setImage(new Image(file.toURI().toString()));
//            } else {
//                artImageView.setImage(new Image("path/to/placeholder/image.png")); // Replace with a valid placeholder image path
//            }
//        } else {
//            artImageView.setImage(new Image("path/to/placeholder/image.png")); // Replace with a valid placeholder image path
//        }
    }


// onClick of artwork function
//                controller.displayArt(firstArt); // Display the first piece of art --------------------------- All displayArt controler functions needs to be fucked off from here and moved into Portfolio Content Controller
}
