package com.example.addressbook.model;

public class Portfolio {
    private int id;
    private String portfolioName;
    private String portfolioDescription;
    private Integer contactID;

    private String imagePath; // for portfolio image path

    public Portfolio(String portfolioName, String portfolioDescription, Integer contactID) {
        this.portfolioName = portfolioName;
        this.portfolioDescription = portfolioDescription;
        this.contactID = contactID;  // Initialize contact_id in the constructor
    }

    // Constructor with image path, if there's an image is available

    public Portfolio(String portfolioName, String portfolioDescription, Integer contactID, String imagePath) {
        this.portfolioName = portfolioName;
        this.portfolioDescription = portfolioDescription;
        this.contactID = contactID;
        this.imagePath = imagePath;


    }


    // Getters and setters for the image path
    public String getImagePath() {
        return imagePath;

    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;

    }



    public int getId(){
        return id;
    }

    public void setId(int id) {this.id = id; }



    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName, String portfolioDescription) {
        this.portfolioName = portfolioName;
        this.portfolioDescription = portfolioDescription;

    }



    // added for MyPortfoliosController - edit title only
    public String getPortfolioTitle() {
        return portfolioName;
    }

    public void setPortfolioTitle(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }


}
