package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The EditPortfolioController manages the editing of an existing portfolio in the application.
 * It allows users to update the name and description of a portfolio and persist these changes
 * to the database via the SqlitePortfolioDAO.
 */
public class EditPortfolioController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    private Portfolio portfolio;
    private SqlitePortfolioDAO portfolioDAO;
    private boolean portfolioUpdated = false;

    /**
     * Sets the DAO (Data Access Object) responsible for managing portfolio persistence.
     *
     * @param portfolioDAO the DAO used to interact with the portfolio database.
     */
    public void setPortfolioDAO(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    /**
     * Sets the portfolio to be edited and populates the text fields with its current values.
     *
     * @param portfolio the portfolio to edit.
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        nameTextField.setText(portfolio.getPortfolioName());
        descriptionTextField.setText(portfolio.getPortfolioDescription());
    }

    /**
     * Returns whether the portfolio has been updated.
     *
     * @return {@code true} if the portfolio has been updated, otherwise {@code false}.
     */
    public boolean isPortfolioUpdated() {
        return portfolioUpdated;
    }

    /**
     * Handles the action of saving the changes made to the portfolio.
     * It updates the portfolio's name and description with the new values entered by the user,
     * performs validation to ensure fields are not empty, and persists the changes to the database.
     */
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

    /**
     * Handles the action of canceling the edit operation.
     * It closes the dialog without saving any changes.
     */
    @FXML
    public void onCancel() {
        closeDialog();
    }

    /**
     * Closes the dialog window.
     * This method is called after saving or canceling the edit operation.
     */
    private void closeDialog() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert dialog to the user for validation errors or messages.
     *
     * @param title   the title of the alert dialog.
     * @param message the message content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        // Show an alert
    }
}
