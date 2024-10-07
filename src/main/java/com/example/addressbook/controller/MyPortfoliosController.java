package com.example.addressbook.controller;

import com.example.addressbook.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.ListCell;
import javafx.stage.Modality;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MyPortfoliosController {

    @FXML
    private ListView<Portfolio> portfolioListView;

    @FXML
    private Button createPortfolioButton;

    private final SqlitePortfolioDAO portfolioDAO;

    public MyPortfoliosController() {
        portfolioDAO = new SqlitePortfolioDAO();
    }

    @FXML
    public void initialize() {
        loadPortfolios(); // Load existing portfolios into the ListView
    }

    // Load the portfolios from the database into the ListView
    private void loadPortfolios() {
        if (getLoggedInUserId() == -1) {
            showAlert("Error", "No user is logged in. Please log in first.");
            return;
        }
        List<Portfolio> portfolios = portfolioDAO.getAllPortfolio();
        ObservableList<Portfolio> portfolioList = FXCollections.observableArrayList(portfolios);
        portfolioListView.setItems(portfolioList);
        portfolioListView.setCellFactory(param -> new PortfolioListCell());
    }

    // Handle deleting a selected portfolio
    @FXML
    public void onDeletePortfolio(ActionEvent event) {
        Portfolio selectedPortfolio = portfolioListView.getSelectionModel().getSelectedItem();

        if (selectedPortfolio == null) {
            showAlert("Error", "Please select a portfolio to delete.");
            return;
        }

        // Show the delete confirmation dialog
        boolean confirmed = showDeleteConfirmationDialog();

        // Proceed with deletion if confirmed
        if (confirmed) {
            portfolioDAO.deletePortfolio(selectedPortfolio);
            loadPortfolios(); // Reload the portfolio list

        }
    }

    // Show the delete confirmation dialog
    private boolean showDeleteConfirmationDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/delete-confirmation-popup.fxml"));
            Parent root = loader.load();

            DeleteConfirmationController controller = loader.getController();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirm Delete");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(createPortfolioButton.getScene().getWindow()); // Use any existing window as the owner
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Return true if the user confirmed the deletion
            return controller.isConfirmed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the logged-in user ID
    private int getLoggedInUserId() {
        if (SessionManager.getInstance().getLoggedInUser() == null) {
            return -1; // Indicates that no user is logged in
        }
        return SessionManager.getInstance().getLoggedInUser().getId();
    }

    // Utility method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Custom ListCell for portfolio items with "Open" and "Delete" buttons with a GridPane layout
    private class PortfolioListCell extends ListCell<Portfolio> {
        private final GridPane content;
        private final Label portfolioNameLabel;
        private final Label portfolioDescriptionLabel;
        private final Button openButton;
        private final Button deleteButton;

        public PortfolioListCell() {
            portfolioNameLabel = new Label();
            portfolioDescriptionLabel = new Label();
            openButton = new Button("Open");
            deleteButton = new Button("Delete");

            // Apply CSS classes
            portfolioNameLabel.getStyleClass().add("portfolio-title");
            portfolioDescriptionLabel.getStyleClass().add("portfolio-description");
            openButton.getStyleClass().add("portfolio-open-button"); // New style class for open button
            deleteButton.getStyleClass().add("delete-portfolio-button"); // Use the existing .nav-button style for delete

            // Enable text wrapping for both title and description
            portfolioNameLabel.setWrapText(true);
            portfolioDescriptionLabel.setWrapText(true);

            // Set alignment for description label to the left
            //portfolioNameLabel.setAlignment(Pos.CENTER_LEFT);
            //GridPane.setHalignment(portfolioNameLabel, HPos.LEFT); // Align the name to the left
            // Set alignment for description label to the left
            portfolioDescriptionLabel.setAlignment(Pos.CENTER_LEFT);
            //GridPane.setHalignment(portfolioDescriptionLabel, HPos.LEFT); // Align the description to the left


            // Setting up the GridPane layout
            content = new GridPane();
            content.getStyleClass().add("portfolio-list-cell");
            content.setHgap(10); // Horizontal gap between columns

            // Set maximum width for the title and description to allow wrapping
            portfolioNameLabel.setMaxWidth(400); // Adjust as needed
            portfolioDescriptionLabel.setMaxWidth(200); // Adjust as needed

            // Allow labels to grow in the GridPane
            //GridPane.setHgrow(portfolioNameLabel, Priority.ALWAYS);
            //GridPane.setHgrow(portfolioDescriptionLabel, Priority.ALWAYS);

            // Set column constraints to align the description correctly
            // Make the first column (description) fixed-width
            ColumnConstraints descriptionCol = new ColumnConstraints();
            descriptionCol.setMinWidth(300); // Set minimum width for the description column
            descriptionCol.setHalignment(HPos.LEFT); // Align content to the left

            // Set the second column (title) to grow if needed
            ColumnConstraints titleCol = new ColumnConstraints();
            titleCol.setHgrow(Priority.ALWAYS);
            titleCol.setHalignment(HPos.LEFT);

            // Add column constraints to the GridPane
            content.getColumnConstraints().addAll(descriptionCol, titleCol);



            // Adding elements to the GridPane with flipped order: Title first, then Description
            content.add(portfolioNameLabel, 0, 0);       // Title on the left
            content.add(portfolioDescriptionLabel, 1, 0); // Description next to title
            HBox buttonBox = new HBox(10, openButton, deleteButton); // HBox for buttons with spacing
            content.add(buttonBox, 2, 0); // Buttons to the right

            openButton.setOnAction(event -> onOpenPortfolio(getItem()));
            deleteButton.setOnAction(event -> onDeletePortfolio(new ActionEvent()));
        }

        @Override
        protected void updateItem(Portfolio portfolio, boolean empty) {
            super.updateItem(portfolio, empty);
            if (empty || portfolio == null) {
                setGraphic(null);
            } else {
                portfolioNameLabel.setText(portfolio.getPortfolioName());
                portfolioDescriptionLabel.setText(portfolio.getPortfolioDescription()); // Display description
                setGraphic(content);
            }
        }

        // Open the selected portfolio
        private void onOpenPortfolio(Portfolio portfolio) {
            try {
                // Get the first artwork in the portfolio
                ArtManager artManager = new ArtManager(new SqliteArtDAO());
                List <Art> artworks = artManager.getAllArtInPortfolio(portfolio.getId());//This needs to be changed----------------------------------------------------------------------------------------------------------------------------

                // Check if the list is empty or contains artworks
                if (artworks.isEmpty()) {
                    System.out.println("No artworks found for portfolio ID: " + portfolio.getId());
                } else {
                    // Loop through the artworks and print each one
                    System.out.println("Artworks for portfolio ID: " + portfolio.getId());
                    for (Art artwork : artworks) {
                        System.out.println(artwork);  // Assuming Art has a meaningful toString() method
                    }
                }

                // Load the portfolio overview FXML file and switch the scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/portfolio-content-view.fxml"));

                Parent enlargedArtRoot = loader.load();

                PortfolioContentController controller = loader.getController();



//                controller.displayArt(firstArt); // Display the first piece of art --------------------------- All displayArt controler functions needs to be fucked off from here and moved into Portfolio Content Controller
                controller.setPortfolioTitle(portfolio.getPortfolioTitle());
                controller.setPortfolioDescription(portfolio.getPortfolioDescription());
                controller.loadPortfolioArtworks(artworks);

                // Get the current stage and set the new scene (Portfolio Overview page)
                Scene displayArtScene = new Scene(enlargedArtRoot);
                Stage stage = (Stage) openButton.getScene().getWindow();
                stage.setScene(displayArtScene);
                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void onCreatePortfolio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/create-portfolio-popup.fxml"));
            Parent root = loader.load();

            CreatePortfolioController dialogController = loader.getController();
            dialogController.setPortfolioDAO(portfolioDAO);

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Portfolio");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(createPortfolioButton.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // After the dialog is closed, refresh the portfolio list
            if (dialogController.isPortfolioCreated()) {
                loadPortfolios();

            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the create portfolio dialog.");
        }
    }

}
