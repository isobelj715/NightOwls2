package com.example.addressbook.controller;

import com.example.addressbook.model.SqliteArtDAO;
import com.example.addressbook.model.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;

import java.util.List;


public class artworkDisplayCanvasController {

    private SqliteArtDAO artDAO;
    private portfolioViewController portfolioViewController = new portfolioViewController();

    @FXML
    private List<Art> artworks;

    public void initialize(){
        this.artDAO = new SqliteArtDAO();
        getImages(portfolioViewController.getPortfolioID());
        buildArtworkDiplayTable();
    }

    //Gets images and sets images
    private void getImages(int portfolioID){
        String filter = "portfolio_id =" + portfolioID;
//        this.artworks = artDAO.getAllArtFiltered(filter);
        this.artworks = artDAO.getAllArt();

        System.out.println(this.artworks);
        System.out.println(portfolioID);
    }

    //Builds artworks display table
    private void buildArtworkDiplayTable(){

    }

    private void buildArtWorkDisplayRow(List<Art> artworks){
        HBox hbox = new HBox(10);
        hbox.getStyleClass().add("display-canvas-row");
       for (int i=0; i<artworks.size(); i++){
//           hbox.getChildren().add(artworks[i].);
       }
    }
}
