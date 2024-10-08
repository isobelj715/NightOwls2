package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlitePortfolioDAO implements IPortfolioDAO {
    private Connection connection;
    private SessionManager sessionManager;

    public SqlitePortfolioDAO() {
        connection = SqliteConnection.getInstance();
        sessionManager = SessionManager.getInstance();
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            // Create the new table with all the required columns
            String query = "CREATE TABLE IF NOT EXISTS portfolios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "portfolioName TEXT NOT NULL, "
                    + "portfolioDescription TEXT, "
                    + "contact_id INTEGER, "
                    + "FOREIGN KEY(contact_id) REFERENCES contacts(id) ON DELETE CASCADE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPortfolio(Portfolio portfolio) {
        try {
            String query = "INSERT INTO portfolios (portfolioName, portfolioDescription, contact_id) "
                    + "VALUES (?, ?, ?)";  // Correctly include the third placeholder for contact_id
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, portfolio.getPortfolioName());
            statement.setString(2, portfolio.getPortfolioDescription());
            statement.setInt(3, portfolio.getContactID());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                portfolio.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePortfolio(Portfolio portfolio) {
        try {
            // The SQL query only has four placeholders, so only four parameters should be set
            String query = "UPDATE portfolios SET portfolioName = ?, portfolioDescription = ?, contact_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, portfolio.getPortfolioName());
            statement.setString(2, portfolio.getPortfolioDescription());
            statement.setInt(3, portfolio.getContactID());
            statement.setInt(4, portfolio.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deletePortfolio(Portfolio portfolio) {
        try {
            // Correct the SQL query to have only one parameter (id)
            String query = "DELETE FROM portfolios WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // Set only the portfolio ID as the parameter
            statement.setInt(1, portfolio.getId());

            // Execute the delete query
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Portfolio getPortfolio(int id) {
        try {
            String query = "SELECT * FROM portfolios WHERE id = ? AND contact_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, sessionManager.getLoggedInUser().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String portfolioName = resultSet.getString("portfolioName");
                String portfolioDescription = resultSet.getString("portfolioDescription");
                Integer contactID = resultSet.getInt("contact_id");

                Portfolio portfolio = new Portfolio(portfolioName, portfolioDescription, contactID);
                portfolio.setId(id);
                return portfolio;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Portfolio> getAllPortfolio() {
        List<Portfolio> portfolios = new ArrayList<>();
        try {
            String query = "SELECT * FROM portfolios WHERE contact_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, sessionManager.getLoggedInUser().getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String portfolioName = resultSet.getString("portfolioName");
                String portfolioDescription = resultSet.getString("portfolioDescription");
                Integer contactID = resultSet.getInt("contact_id");

                Portfolio portfolio = new Portfolio(portfolioName, portfolioDescription, contactID);
                portfolio.setId(id);
                portfolios.add(portfolio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portfolios;
    }
}