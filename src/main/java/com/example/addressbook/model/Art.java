package com.example.addressbook.model;

public class Art {
    private int id;
    private String artTitle;
    private String category;
    private Integer year;
    private String medium;
    private String material;
    private Integer width;
    private Integer height;
    private Integer depth;
    private String units;
    private String description;
    private String filePath;
    private Integer portfolioId;

    /**
     * Constructs a new ArtPiece with the specified Title and Year.
     * The rest of the fields are optional and can be set using setters.
     *
     * @param artTitle The title of the artwork (required)
     * @param year The year the artwork was created (required)
     */
    public Art(String artTitle, Integer year) {
        this.artTitle = artTitle;
        this.year = year;
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for Title
    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    // Getter and Setter for Year
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    // Optional fields with getters and setters

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for file path
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Getters and Setters for portfolioId
    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }
}