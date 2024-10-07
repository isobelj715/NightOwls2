package com.example.addressbook.model;

public class Portfolio {
    private int id;
    private String portfolioName;
    private String portfolioDescription;
    private Integer contactID;

    public Portfolio(String portfolioName, String portfolioDescription, Integer contactID) {
        this.portfolioName = portfolioName;
        this.portfolioDescription = portfolioDescription;
        this.contactID = contactID;  // Initialise contact_id in the constructor
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {this.id = id; }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
        //this.portfolioDescription = portfolioDescription;
    }



    // added for MyPortfoliosController - edit title only. why is this here ??? maybe remove
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
