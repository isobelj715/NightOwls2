package com.example.addressbook.model;

import java.util.List;

public class ArtManager {
    private IArtDAO artDAO;

    /**
     * Constructs a new ContactManager with the specified IContactDAO.
     *
     * @param artDAO The data access object to be used for contact operations.
     */
    public ArtManager(IArtDAO artDAO) {
        this.artDAO = artDAO;
    }

    /**
     * Searches for art that matches the given query.
     *
     * @param query The query to match art.
     * @return A list of art that match the query.
     */
    public List<Art> searchArt(String query) {
        return artDAO.getAllArt()
                .stream()
                .filter(art -> isArtMatched(art, query))
                .toList();
    }

    /**
     * Checks if art matches the given query.
     *
     * @param art The art to check.
     * @param query The query to match.
     * @return true if the art matches the query, false otherwise.
     */
    private boolean isArtMatched(Art art, String query) {
        if (query == null || query.isEmpty()) return true;
        query = query.toLowerCase();
        String searchString = art.getArtTitle()
                + " " + art.getYear();
        return searchString.toLowerCase().contains(query);
    }

    /**
     * Adds a new art piece.
     *
     * @param art The art to add.
     */
    public void addArt(Art art) {
        artDAO.addArt(art);
    }

    /**
     * Deletes an art piece.
     *
     * @param art The contact to delete.
     */
    public void deleteArt(Art art) {
        artDAO.deleteArt(art);
    }

    /**
     * Updates an art.
     *
     * @param art The art to update.
     */
    public void updateArt(Art art) {
        artDAO.updateArt(art);
    }

    /**
     * Returns all art.
     *
     * @return A list of all art.
     */
    public List<Art> getAllArt() {
        return artDAO.getAllArt();
    }


    public Art getFirstArtInPortfolio(int portfolioId) {
        return artDAO.getAllArt()
                .stream()
                .filter(art -> art.getPortfolioId() != null && art.getPortfolioId() == portfolioId)
                .findFirst()
                .orElse(null);
    }
}

