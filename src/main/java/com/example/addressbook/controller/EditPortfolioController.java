package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPortfolioController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    private Portfolio portfolio;
    private SqlitePortfolioDAO portfolioDAO;
    private boolean portfolioUpdated = false;

    public void setPortfolioDAO(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        nameTextField.setText(portfolio.getPortfolioName());
        descriptionTextField.setText(portfolio.getPortfolioDescription());
    }

    public boolean isPortfolioUpdated() {
        return portfolioUpdated;
    }

    @FXML
    public void onSave() {
        // Update portfolio with new name and description
        String newName = nameTextField.getText().trim();
        String newDescription = descriptionTextField.getText().trim();

        if (newName.isEmpty() || newDescription.isEmpty()) {
            showAlert("Error", "Name and description cannot be empty.");
            return;
        }

        portfolio.setPortfolioName(newName);
        portfolio.setPortfolioDescription(newDescription);

        // Update the portfolio in the database
        portfolioDAO.updatePortfolio(portfolio);

        portfolioUpdated = true; // Mark the portfolio as updated
        closeDialog();
    }

    @FXML
    public void onCancel() {
        closeDialog();
    }

    // Close the dialog window
    private void closeDialog() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        // Show an alert (similar to how it's done in the main controller)
    }
}
