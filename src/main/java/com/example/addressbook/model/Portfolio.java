package com.example.addressbook.model;

public class Portfolio {
    private int id;
    private String portfolioName;
    private String portfolioDescription;

    public Portfolio(String portfolioName, String portfolioDescription) {
        this.portfolioName = portfolioName;
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

    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }


}
