package com.example.addressbook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlitePortfolioDAO implements IPortfolioDAO{
    private Connection connection;

    public SqlitePortfolioDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Drop the existing table (if any) - TODO: This is just for development, will need to be changed for final
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS portfolios");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the new table with all the required columns
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS portfolios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "portfolioName VARCHAR NOT NULL, "
                    + "portfolioDescription TEXT"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addPortfolio(Portfolio portfolio) {
        try {
            String query = "INSERT INTO portfolios (portfolioName, portfolioDescription) "
                    + "VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, portfolio.getPortfolioName());
            statement.setString(2, portfolio.getPortfolioDescription());
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
            String query = "UPDATE portfolios SET portfolioName = ?, portfolioDescription = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, portfolio.getPortfolioName());
            statement.setString(2, portfolio.getPortfolioDescription());
            statement.setInt(3, portfolio.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM portfolios WHERE id = ?");
            statement.setInt(1, portfolio.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Portfolio getPortfolio(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM portfolios WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String portfolioName = resultSet.getString("portfolioName");
                String portfolioDescription = resultSet.getString("portfolioDescription");
                Portfolio portfolio = new Portfolio(portfolioName, portfolioDescription);
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
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM portfolios";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String portfolioName = resultSet.getString("portfolioName");
                String portfolioDescription = resultSet.getString("portfolioDescription");
                Portfolio portfolio = new Portfolio(portfolioName, portfolioDescription);
                portfolio.setId(id);
                portfolios.add(portfolio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portfolios;
    }

}
