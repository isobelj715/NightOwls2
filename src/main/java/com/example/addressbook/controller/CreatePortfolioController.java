package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import com.example.addressbook.model.SqlitePortfolioDAO;
import com.example.addressbook.model.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The CreatePortfolioController handles the creation of new portfolios in the application.
 * It manages user input, validates the form, and interacts with the SqlitePortfolioDAO
 * to store the newly created portfolio in the database.
 */
public class CreatePortfolioController extends BaseController {

    @FXML
    private TextField portfolioNameField;

    @FXML
    private TextField portfolioDescriptionField;

    private SqlitePortfolioDAO portfolioDAO;

    private boolean portfolioCreated = false;

    /**
     * Sets the Portfolio Data Access Object (DAO) used to manage portfolios in the database.
     *
     * @param portfolioDAO the DAO to interact with portfolio data.
     */
    public void setPortfolioDAO(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }


    /**
     * Indicates whether a portfolio has been successfully created.
     *
     * @return {@code true} if a portfolio has been created, otherwise {@code false}.
     */
    public boolean isPortfolioCreated() {
        return portfolioCreated;
    }


    /**
     * Handles the action of creating a portfolio when the user submits the form.
     * Validates the input fields and creates a new portfolio in the database if valid.
     */
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
        Portfolio newPortfolio = new Portfolio(name, description, userId);

        portfolioDAO.addPortfolio(newPortfolio);
        portfolioCreated = true;

        // Close the dialog
        Stage stage = (Stage) portfolioNameField.getScene().getWindow();
        stage.close();
    }
}