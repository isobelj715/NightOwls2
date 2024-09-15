package com.example.addressbook.model;

import javax.sound.sampled.Port;
import java.util.List;

public interface IPortfolioDAO {
    /** Adds a new Portfolio to the database.
         * @param portfolio The portfolio to add.
         */
    public void addPortfolio(Portfolio portfolio);
    /**
     * Updates an existing portfolio in the database.
     * @param portfolio The portfolio to update.
     */
    public void updatePortfolio(Portfolio portfolio);

    /**
     * Deletes a portfolio from the database.
     * @param portfolio The portfolio to delete.
     */
    public void deletePortfolio(Portfolio portfolio);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the portfolio to retrieve.
     * @return The portfolio with the given portfolio, or null if not found.
     */
    public Portfolio getPortfolio(int id);
    /**
     * Retrieves all portfolios from the database.
     * @return A list of all portfolios in the database.
     */
    public List<Portfolio> getAllPortfolio();
}

