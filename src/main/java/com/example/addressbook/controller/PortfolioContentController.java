package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import com.example.addressbook.model.ArtManager;
import com.example.addressbook.model.SqliteArtDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class PortfolioContentController {

    @FXML
    private Label portfolioTitleLabel;
    @FXML
    private Label portfolioDescriptionLabel;

    public void setPortfolioTitle(String portfolioTitle) {
        portfolioTitleLabel.setText(portfolioTitle);
    }

    public void setPortfolioDescription(String portfolioDescription){
        portfolioDescriptionLabel.setText(portfolioDescription);
    }

    public void loadPortfolioArtworks(){
        ArtManager artManager = new ArtManager(new SqliteArtDAO());

    }
}
