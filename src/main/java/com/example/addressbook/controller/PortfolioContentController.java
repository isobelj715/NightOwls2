package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PortfolioContentController {

    @FXML
    private Label portfolioTitleLabel;
    @FXML
    private Label portfolioDescriptionLabel;
    @FXML
    private GridPane artGrid;

    private ArtManager artManager;
    private Portfolio currentPortfolio;

    @FXML
    public void handleGeneratePdfButtonClick() {
        if (currentPortfolio == null) {
            System.out.println("Error: No portfolio selected.");
            return;
        }

        String destinationPath = "portfolio_" + currentPortfolio.getId() + ".pdf";

        try {
            // Generate the PDF using PortfolioPdfGenerator
            PDFController pdfController = new PDFController();
            pdfController.generatePdf(destinationPath, currentPortfolio, artManager.getAllArtInPortfolio(currentPortfolio.getId()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Successful");
            alert.setHeaderText(null);
            alert.setContentText("The PDF has been successfully downloaded.");
            alert.showAndWait();

            System.out.println("PDF generated successfully: " + destinationPath);
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Download Failed");
            alert.setHeaderText(null);
            alert.setContentText("There was an error generating the PDF. Please try again.");
            alert.showAndWait();
        }
    }

    public void setPortfolio(Portfolio portfolio) {
        this.currentPortfolio = portfolio;
        this.artManager = new ArtManager(new SqliteArtDAO());

        setPortfolioTitle(portfolio.getPortfolioName());
        setPortfolioDescription(portfolio.getPortfolioDescription());
    }

    public void setPortfolioTitle(String portfolioTitle) {
        portfolioTitleLabel.setText(portfolioTitle);
    }

    public void setPortfolioDescription(String portfolioDescription) {
        portfolioDescriptionLabel.setText(portfolioDescription);
    }

    public void loadPortfolioArtworks(List<Art> artworks) {
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

            // Add onClick event to the VBox
            artBox.setOnMouseClicked(event -> {
                // Define the behavior when an artwork is clicked
                System.out.println("Artwork clicked: " + art.getArtTitle());
                // You can also trigger other actions, such as opening a detailed view
//                openArtworkDetails(art);  // This is just an example of what you might do

// Load the portfolio overview FXML file and switch the scene
                try {
                    // Load the FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/display-art-view.fxml"));

                    // Load the root node from the FXML file
                    Parent root = loader.load();
                    DisplayArtController controller = loader.getController();

                    controller.displayArt(art);
                    Stage stage = (Stage) artGrid.getScene().getWindow();  // Assuming artGrid is the GridPane or any node in your current scene

                    // Set the new scene with the loaded root node
                    Scene scene = new Scene(root);

                    // Optionally, set the window title
                    stage.setTitle("Artwork");

                    // Switch to the new scene
                    stage.setScene(scene);

                    // Optionally, show the stage (though it's usually not necessary as the stage is already shown)
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Optionally, show an error dialog or handle the exception as needed
                }
            });

            // Update column and row for the next artwork
            col++;
            if (col == columns) {
                col = 0;
                row++;
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
}
