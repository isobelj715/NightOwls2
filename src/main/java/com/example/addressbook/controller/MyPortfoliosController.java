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


/**
 * The MyPortfoliosController manages the display and interaction of portfolios in the application.
 * It allows users to view, create, edit, and delete portfolios. It also handles loading portfolios
 * from the database and provides custom UI components for each portfolio.
 */
public class MyPortfoliosController extends BaseController{

    @FXML
    private ListView<Portfolio> portfolioListView;

    @FXML
    private Button createPortfolioButton;



    private final SqlitePortfolioDAO portfolioDAO;

    //public MyPortfoliosController() {
    //    portfolioDAO = new SqlitePortfolioDAO();
    //}

    // trying to get unit testing to work :
    // Default constructor

    /**
     * Default constructor for the controller. Initialises the DAO to interact with the database.
     */
    public MyPortfoliosController() {
        this.portfolioDAO = new SqlitePortfolioDAO();
    }

    /**
     * Constructor for dependency injection, used for testing purposes.
     *
     * @param portfolioDAO DAO used to interact with portfolio data.
     */
    public MyPortfoliosController(SqlitePortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    /**
     * Sets the ListView for testing purposes.
     *
     * @param portfolioListView the ListView to set.
     */
    public void setPortfolioListView(ListView<Portfolio> portfolioListView) {
        this.portfolioListView = portfolioListView;
    }

    /**
     * Returns the portfolio ListView for testing.
     *
     * @return the portfolio ListView.
     */
    public ListView<Portfolio> getPortfolioListView() {
        return portfolioListView;
    }


    /**
     * Initialises the controller by loading existing portfolios into the ListView.
     */
    @FXML
    public void initialize() {
        loadPortfolios(); // Load existing portfolios into the ListView
    }

    /**
     * Loads the portfolios from the database into the ListView.
     * This method fetches all portfolios for the logged-in user.
     */
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


    /**
     * Handles the deletion of a portfolio after confirmation.
     *
     * @param portfolio the portfolio to delete.
     */
    public void onDeletePortfolio(Portfolio portfolio) {
        if (portfolio == null) {
            showAlert("Error", "Unable to delete portfolio.");
            return;
        }

        // Show the delete confirmation dialog
        boolean confirmed = showDeleteConfirmationDialog();

        // Proceed with deletion if confirmed
        if (confirmed) {
            portfolioDAO.deletePortfolio(portfolio);
            loadPortfolios(); // Reload the portfolio list
        }
    }

    /**
     * Displays a confirmation dialog before deleting a portfolio.
     *
     * @return {@code true} if the user confirmed the deletion, otherwise {@code false}.
     */
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


    /**
     * Custom ListCell class for displaying portfolio items with Open, Edit, and Delete buttons.
     */
    private class PortfolioListCell extends ListCell<Portfolio> {
        private final GridPane content;
        private final Label portfolioNameLabel;
        private final Label portfolioDescriptionLabel;
        private final Button openButton;
        private final Button deleteButton;
        private final Button editButton;

        public PortfolioListCell() {
            portfolioNameLabel = new Label();
            portfolioDescriptionLabel = new Label();
            openButton = new Button("Open");
            deleteButton = new Button("Delete");
            editButton = new Button("Edit");

            // Apply CSS classes
            portfolioNameLabel.getStyleClass().add("portfolio-name");
            portfolioDescriptionLabel.getStyleClass().add("portfolio-description");
            openButton.getStyleClass().add("portfolio-open-button"); // New style class for open button
            editButton.getStyleClass().add("portfolio-open-button"); // same as open button for now
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
            portfolioNameLabel.setMaxWidth(200); // Adjust as needed
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
            HBox buttonBox = new HBox(10, openButton, editButton, deleteButton); // HBox for buttons with spacing
            content.add(buttonBox, 2, 0); // Buttons to the right

            openButton.setOnAction(event -> onOpenPortfolio(getItem()));
            editButton.setOnAction(event -> onEditPortfolio(getItem()));
            deleteButton.setOnAction(event -> onDeletePortfolio(getItem()));



        }

        /**
         * Updates the item (portfolio) in the cell and sets the graphic content.
         *
         * @param portfolio the portfolio to display in the cell.
         * @param empty     whether the cell is empty.
         */
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

        /**
         * Opens the selected portfolio, loading its contents into the Portfolio Overview scene.
         *
         * @param portfolio the portfolio to open.
         */
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

    /**
     * Handles the action when the "Create Portfolio" button is clicked.
     * Opens the create portfolio dialog.
     *
     * @param event the event triggered when the create portfolio button is clicked.
     */
    @FXML
    public void onCreatePortfolio(ActionEvent event) {
        // base controller doesnt quite work currently cos the dialog box should not be full screen:
        //loadPage(event, "/com/example/addressbook/create-portfolio-popup.fxml");

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


    /**
     * Handles the editing of a portfolio. Opens an edit dialog that allows the user to modify
     * the selected portfolio's details. After editing, the portfolio list is refreshed if changes
     * were made.
     *
     * @param portfolio the portfolio to be edited. If the portfolio is null, an error alert is shown.
     */
    private void onEditPortfolio(Portfolio portfolio) {
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
            dialogStage.initOwner(portfolioListView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // After closing the dialog, check if the portfolio was updated and refresh the list
            if (editController.isPortfolioUpdated()) {
                loadPortfolios(); // Reload the updated portfolio list
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the edit portfolio dialog.");
        }
    }
}
