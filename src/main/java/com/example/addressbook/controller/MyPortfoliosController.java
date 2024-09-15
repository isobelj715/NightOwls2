package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import com.example.addressbook.model.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListCell;
import javafx.stage.Modality;
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

    // Custom ListCell for portfolio items with "Open" and "Delete" buttons
    private class PortfolioListCell extends ListCell<Portfolio> {
        private final HBox content;
        private final Label portfolioNameLabel;
        private final Label portfolioDescriptionLabel;
        private final Button openButton;
        private final Button deleteButton;

        public PortfolioListCell() {
            portfolioNameLabel = new Label();
            portfolioDescriptionLabel = new Label();
            openButton = new Button("Open");
            deleteButton = new Button("Delete");

            openButton.setOnAction(event -> onOpenPortfolio(getItem()));
            deleteButton.setOnAction(event -> onDeletePortfolio(new ActionEvent()));

            // Adding the labels and buttons to the HBox
            content = new HBox(portfolioNameLabel, portfolioDescriptionLabel, openButton, deleteButton);
            content.setSpacing(10); // Adjust spacing if necessary
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
            showAlert("Open Portfolio", "Open portfolio: " + portfolio.getPortfolioName());
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
