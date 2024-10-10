package com.example.addressbook.controller;

import com.example.addressbook.model.Portfolio;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InsidePortfolioControllerTest extends ApplicationTest {

    private InsidePortfolioController controller;

    @BeforeEach
    public void setUp() {

        // Initialize the controller
        controller = new InsidePortfolioController();


        controller.portfolioImageView = mock(ImageView.class);
    }

    @Test
    public void testLoadPortfolioDetails_WithValidImagePath() {

        // test portfolio with an image path
        Portfolio portfolio = new Portfolio("Test Portfolio", "Test Description", 1, "path/to/image.jpg");


        File imageFile = mock(File.class);
        when(imageFile.exists()).thenReturn(true);


        controller.loadPortfolioDetails(portfolio);


        assertNotNull(controller.portfolioImageView.getImage(), "Image should be loaded for a valid image path.");


    }

    @Test
    public void testLoadPortfolioDetails_WithInvalidImagePath() {

        Portfolio portfolio = new Portfolio("Test Portfolio", "Test Description", 1, "invalid/path/to/image.jpg");


        File imageFile = mock(File.class);
        when(imageFile.exists()).thenReturn(false);


        controller.loadPortfolioDetails(portfolio);



        assertNull(controller.portfolioImageView.getImage(), "Image should not be loaded for an invalid image path.");


    }


}
