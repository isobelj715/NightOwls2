package com.example.addressbook.model;

import java.util.List;
/**
 * Interface for the Art Data Access Object that handles
 * the CRUD operations for the Art class with the database.
 */
public interface IArtDAO {
        /**
         * Adds a new Art Piece to the database.
         * @param art The contact to add.
         */
        public void addArt(Art art);
        /**
         * Updates an existing contact in the database.
         * @param art The contact to update.
         */
        public void updateArt(Art art);
        /**
         * Deletes a contact from the database.
         * @param art The contact to delete.
         */
        public void deleteArt(Art art);
        /**
         * Retrieves a contact from the database.
         * @param id The id of the art to retrieve.
         * @return The contact with the given art, or null if not found.
         */
        public Art getArt(int id);
        /**
         * Retrieves all contacts from the database.
         * @return A list of all contacts in the database.
         */
        public List<Art> getAllArt();
}
