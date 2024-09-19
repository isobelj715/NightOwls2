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
import java.sql.Connection;
import java.util.List;

public class portfolioViewController {

    private SqlitePortfolioDAO portfolioDAO;
    private SqliteArtDAO artDAO;
    private int portfolioID;

    //This references the front end
    @FXML
    private ListView<Art> artworksListView;

    @FXML
    public void initialize(){
        //Code to run on load of page

    }

    public void  setPortfolioID(int portfolioID){
        this.portfolioID = portfolioID;
    }
    public int getPortfolioID(){
        return  portfolioID;
    }

    // Utility method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPortfolioArtwork(){
        if (getLoggedInUserId() == -1) {
            showAlert("Error", "No user is logged in. Please log in first.");
            return;
        }
        String filter = "portfolio_ID = " + portfolioID;
        List<Art> artworks = artDAO.getAllArtFiltered(filter);
        ObservableList<Art> artworksList = FXCollections.observableArrayList(artworks);
        //This needs to reference the front end--------------------------------------------------------------------!!!!!!!!
        artworksListView.setItems(artworksList);
//        artworksListView.setCellFactory(param -> new artworksListCell());
    }

    @FXML
    public void handleArtworkDisplay(ActionEvent event) {

    }

    // Get the logged-in user ID
    private int getLoggedInUserId() {
        if (SessionManager.getInstance().getLoggedInUser() == null) {
            return -1; // Indicates that no user is logged in
        }
        return SessionManager.getInstance().getLoggedInUser().getId();
    }
}