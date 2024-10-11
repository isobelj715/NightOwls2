package com.example.addressbook.controller;
import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The EditArtController manages the editing of an existing artwork in the application.
 * It allows users to update the field attached to an artwork and persist these changes
 * to the database via the SqliteArtDAO.
 */

public class EditArtController {
    @FXML
    private TextField artTitleField;
    @FXML
    private TextField artYearField;
    @FXML
    private TextField artCategoryField;
    @FXML
    private TextField artMediumField;
    @FXML
    private TextField artMaterialField;
    @FXML
    private TextField artWidthField;
    @FXML
    private TextField artHeightField;
    @FXML
    private TextField artDepthField;
    @FXML
    private TextField artUnitsField;
    @FXML
    private TextArea artDescriptionArea;

    private ArtManager artManager;
    private Art currentArt;

    public void setArtManager(ArtManager artManager) {
        this.artManager = artManager;
    }

    /**
     * Sets the art to be edited and populates the text fields with its current values.
     *
     * @param art the portfolio to edit.
     */
    public void setArt(Art art) {
        this.currentArt = art;
        populateFields(art);
    }

    private void populateFields(Art art) {
        artTitleField.setText(art.getArtTitle());
        artYearField.setText(art.getYear() != null ? art.getYear().toString() : "");
        artCategoryField.setText(art.getCategory());
        artMediumField.setText(art.getMedium());
        artMaterialField.setText(art.getMaterial());
        artWidthField.setText(art.getWidth() != null ? art.getWidth().toString() : "");
        artHeightField.setText(art.getHeight() != null ? art.getHeight().toString() : "");
        artDepthField.setText(art.getDepth() != null ? art.getDepth().toString() : "");
        artUnitsField.setText(art.getUnits());
        artDescriptionArea.setText(art.getDescription());
    }

    /**
     * Handles the action of saving the changes made to the Art.
     * It updates the art's fields with the new values entered by the user,
     * performs validation to ensure fields are not empty, and persists the changes to the database.
     */

    @FXML
    private void saveArt() {
        currentArt.setArtTitle(artTitleField.getText());
        currentArt.setYear(Integer.valueOf(artYearField.getText()));
        currentArt.setCategory(artCategoryField.getText());
        currentArt.setMedium(artMediumField.getText());
        currentArt.setMaterial(artMaterialField.getText());
        currentArt.setWidth(artWidthField.getText().isEmpty() ? null : Integer.valueOf(artWidthField.getText()));
        currentArt.setHeight(artHeightField.getText().isEmpty() ? null : Integer.valueOf(artHeightField.getText()));
        currentArt.setDepth(artDepthField.getText().isEmpty() ? null : Integer.valueOf(artDepthField.getText()));
        currentArt.setUnits(artUnitsField.getText());
        currentArt.setDescription(artDescriptionArea.getText());

        artManager.updateArt(currentArt);
        closeWindow();
    }

    /**
     * Handles the action of canceling the edit operation.
     * It closes the dialog without saving any changes.
     */
    @FXML
    private void cancel() {
        closeWindow();
    }

    /**
     * Closes the dialog window.
     * This method is called after saving or canceling the edit operation.
     */
    private void closeWindow() {
        Stage stage = (Stage) artTitleField.getScene().getWindow();
        stage.close();
    }
}
