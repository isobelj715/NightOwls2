package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import com.example.addressbook.model.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreatePortfolioController {

    @FXML
    private TextField portfolioNameField;

    @FXML
    private TextField portfolioDescriptionField;

    private SqlitePortfolioDAO portfolioDAO;

    private boolean portfolioCreated = false;

    public void setPortfolioDAO(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public boolean isPortfolioCreated() {
        return portfolioCreated;
    }

    @FXML
    private void onCreate() {
        String name = portfolioNameField.getText().trim();
        String description = portfolioDescriptionField.getText().trim();

        if (name.isEmpty()) {
            showAlert("Validation Error", "Portfolio name is required.");
            return;
        }

        int userId = getLoggedInUserId();
        if (userId == -1) {
            showAlert("Error", "No user is logged in. Please log in first.");
            return;
        }

        // Create a new Portfolio using the constructor that expects three arguments
        Portfolio newPortfolio = new Portfolio(description, name, userId);

        portfolioDAO.addPortfolio(newPortfolio);
        portfolioCreated = true;

        // Close the dialog
        Stage stage = (Stage) portfolioNameField.getScene().getWindow();
        stage.close();
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
        Alert alert = new Alert(Alert.AlertType.WARNING); // Changed to WARNING for validation errors
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}