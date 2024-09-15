package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import com.example.addressbook.model.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        portfolioDAO.deletePortfolio(selectedPortfolio);
        loadPortfolios(); // Reload the portfolio list
        showAlert("Success", "Portfolio deleted successfully!");
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

    // Custom ListCell for portfolio items with a GridPane layout
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
            openButton.getStyleClass().add("portfolio-button");
            deleteButton.getStyleClass().add("portfolio-button");

            openButton.setOnAction(event -> onOpenPortfolio(getItem()));
            deleteButton.setOnAction(event -> onDeletePortfolio(new ActionEvent()));

            // Setting up the GridPane layout
            content = new GridPane();
            content.getStyleClass().add("portfolio-list-cell");
            content.setHgap(20); // Horizontal gap between columns

            // Adding elements to the GridPane
            content.add(portfolioNameLabel, 0, 0);
            content.add(portfolioDescriptionLabel, 1, 0);
            HBox buttonBox = new HBox(10, openButton, deleteButton); // HBox for buttons with spacing
            content.add(buttonBox, 2, 0);

            // Making columns stretch to fill available space
            GridPane.setHgrow(portfolioNameLabel, Priority.ALWAYS);
            GridPane.setHgrow(portfolioDescriptionLabel, Priority.ALWAYS);
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

        // Open the selected portfolio (for now, just show an alert)
        private void onOpenPortfolio(Portfolio portfolio) {
            try {
                // Load the portfolio overview FXML file and switch the scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/portfolio-overview.fxml"));
                AnchorPane overviewPane = loader.load();
                Scene overviewScene = new Scene(overviewPane);

                // Get the current stage and set the new scene (Portfolio Overview page)
                Stage stage = (Stage) openButton.getScene().getWindow();
                stage.setScene(overviewScene);
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
                showAlert("Success", "Portfolio created successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the create portfolio dialog.");
        }
    }

}
