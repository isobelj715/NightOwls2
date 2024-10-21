package com.example.addressbook.controller;

import com.example.addressbook.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PortfolioContentController extends BaseController {

    private SqlitePortfolioDAO portfolioDAO;
    private Portfolio portfolio;
    @FXML
    private Label portfolioTitleLabel;
    @FXML
    private Label portfolioDescriptionLabel;
    @FXML
    private GridPane artGrid;

    public void setPortfolioContentControllerDAO(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public void setPortfolioContentControllerPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

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

        artGrid.setVgap(60);
        for (Art art : artworks) {
            // Create an ImageView for the artwork's image (assuming Art class has a getImageURL method)
            ImageView imageView = new ImageView(new Image(art.getFilePath()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            // Create a label for the artwork's name or title
            Label artLabel = new Label(art.getArtTitle());

            // Create a VBox to contain the ImageView and Label
            VBox artBox = new VBox(imageView, artLabel);
            artBox.setPrefHeight(120);
            artBox.setSpacing(10);  // Optional: spacing between image and label

            // Add the VBox to the GridPane
            artGrid.add(artBox, col, row);
            // Add onClick event to the VBox
            artBox.setOnMouseClicked(event -> {
                // Define the behavior when an artwork is clicked
                System.out.println("Artwork clicked: " + art.getArtTitle());
                // You can also trigger other actions, such as opening a detailed view

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

    public void onEdit(ActionEvent actionEvent) {
        if (portfolio == null) {
            showAlert("Error", "Unable to edit portfolio.");
            return;
        }

        try {
            // Load the edit portfolio popup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/edit-portfolio-view.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the portfolio data
            EditPortfolioController editController = loader.getController();
            editController.setPortfolioDAO(portfolioDAO);
            editController.setPortfolio(portfolio); // Pass the portfolio to the edit controller

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Portfolio");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            // Instead of using editButton, we use createPortfolioButton or portfolioListView to get the window
//            dialogStage.initOwner(portfolioListView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // After closing the dialog, check if the portfolio was updated and refresh the page
            if (editController.isPortfolioUpdated()) {
                portfolioDescriptionLabel.setText(portfolio.getPortfolioDescription());
                portfolioTitleLabel.setText(portfolio.getPortfolioTitle());

            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the edit portfolio dialog.");
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        if (portfolio == null) {
            showAlert("Error", "Unable to delete portfolio.");
            return;
        }
        // Show the delete confirmation dialog
        boolean confirmed = showDeleteConfirmationDialog();

        // Proceed with deletion if confirmed
        if (confirmed) {
            portfolioDAO.deletePortfolio(portfolio);
            //redirect to myportfolios page
            loadPage(actionEvent, "/com/example/addressbook/my-portfolios-view.fxml");
        }
    }


    private boolean showDeleteConfirmationDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/delete-confirmation-popup.fxml"));
            Parent root = loader.load();

            DeleteConfirmationController controller = loader.getController();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirm Delete");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Return true if the user confirmed the deletion
            return controller.isConfirmed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

// onClick of artwork function
//                controller.displayArt(firstArt); // Display the first piece of art --------------------------- All displayArt controler functions needs to be fucked off from here and moved into Portfolio Content Controller
}
