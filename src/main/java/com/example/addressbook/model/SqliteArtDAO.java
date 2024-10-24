package com.example.addressbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SqliteArtDAO implements IArtDAO {
    private Connection connection;

    public SqliteArtDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS art ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "artTitle VARCHAR NOT NULL, "
                    + "year INTEGER NOT NULL, "
                    + "category VARCHAR, "
                    + "medium VARCHAR, "
                    + "material VARCHAR, "
                    + "width INTEGER, "
                    + "height INTEGER, "
                    + "depth INTEGER, "
                    + "units VARCHAR, "
                    + "description TEXT, "
                    + "filePath TEXT,"
                    + "portfolio_id INTEGER"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addArt(Art art) {
        try {
            String query = "INSERT INTO art (artTitle, year, category, medium, material, width, height, depth, units, description, filePath, portfolio_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, art.getArtTitle());
            statement.setInt(2, art.getYear());
            statement.setString(3, art.getCategory());
            statement.setString(4, art.getMedium());
            statement.setString(5, art.getMaterial());
            if (art.getWidth() != null) {
                statement.setInt(6, art.getWidth());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            if (art.getHeight() != null) {
                statement.setInt(7, art.getHeight());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (art.getDepth() != null) {
                statement.setInt(8, art.getDepth());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setString(9, art.getUnits());
            statement.setString(10, art.getDescription());
            statement.setString(11, art.getFilePath());

            // Set portfolio_id (can be null)
            if (art.getPortfolioId() != null) {
                statement.setInt(12, art.getPortfolioId());
            } else {
                statement.setNull(12, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                art.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Other CRUD methods should be similarly updated to handle filePath

    @Override
    public void updateArt(Art art) {
        try {
            String query = "UPDATE art SET artTitle = ?, year = ?, category = ?, medium = ?, material = ?, width = ?, height = ?, depth = ?, units = ?, description = ?, filePath = ?, portfolio_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, art.getArtTitle());
            statement.setInt(2, art.getYear());
            statement.setString(3, art.getCategory());
            statement.setString(4, art.getMedium());
            statement.setString(5, art.getMaterial());
            if (art.getWidth() != null) {
                statement.setInt(6, art.getWidth());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            if (art.getHeight() != null) {
                statement.setInt(7, art.getHeight());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (art.getDepth() != null) {
                statement.setInt(8, art.getDepth());
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            statement.setString(9, art.getUnits());
            statement.setString(10, art.getDescription());
            statement.setString(11, art.getFilePath());

            // Update portfolio_id (can be null)
            if (art.getPortfolioId() != null) {
                statement.setInt(12, art.getPortfolioId());
            } else {
                statement.setNull(12, Types.INTEGER);
            }

            statement.setInt(13, art.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteArt(Art art) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM art WHERE id = ?");
            statement.setInt(1, art.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Art getArt(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM art WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String artTitle = resultSet.getString("artTitle");
                Integer year = resultSet.getInt("year");
                Art art = new Art(artTitle, year);
                art.setId(id);
                art.setCategory(resultSet.getString("category"));
                art.setMedium(resultSet.getString("medium"));
                art.setMaterial(resultSet.getString("material"));
                art.setWidth(resultSet.getObject("width", Integer.class));
                art.setHeight(resultSet.getObject("height", Integer.class));
                art.setDepth(resultSet.getObject("depth", Integer.class));
                art.setUnits(resultSet.getString("units"));
                art.setDescription(resultSet.getString("description"));
                art.setFilePath(resultSet.getString("filePath"));
                art.setPortfolioId(resultSet.getInt("portfolio_id")); // Set portfolio_id
                return art;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Art> getAllArt() {
        List<Art> arts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM art";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String artTitle = resultSet.getString("artTitle");
                Integer year = resultSet.getInt("year");
                Art art = new Art(artTitle, year);
                art.setId(id);

                // Retrieve optional fields, checking for nulls
                art.setCategory(resultSet.getString("category"));
                art.setMedium(resultSet.getString("medium"));
                art.setMaterial(resultSet.getString("material"));

                // Use getObject to handle nullable integer fields
                art.setWidth((Integer) resultSet.getObject("width"));
                art.setHeight((Integer) resultSet.getObject("height"));
                art.setDepth((Integer) resultSet.getObject("depth"));

                art.setUnits(resultSet.getString("units"));
                art.setDescription(resultSet.getString("description"));

                art.setFilePath(resultSet.getString("filePath"));
                art.setPortfolioId(resultSet.getInt("portfolio_id"));
                arts.add(art);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arts;
    }

}
