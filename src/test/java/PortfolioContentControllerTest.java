package com.example.addressbook.controller;

import com.example.addressbook.model.Art;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field; // fixed javafx error with reflection
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortfolioContentControllerTest {


    private PortfolioContentController controller;


    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

    }

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        controller = new PortfolioContentController();



        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {

            try {
                // initialize fxml
                setPrivateField(controller, "portfolioTitleLabel", new Label());
                setPrivateField(controller, "portfolioDescriptionLabel", new Label());
                setPrivateField(controller, "artGrid", new GridPane());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                latch.countDown();
            }

        });

        latch.await();
    }

    @Test
    public void testSetPortfolioTitle() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.setPortfolioTitle("Sample Portfolio");

            try {
                Label titleLabel = getPrivateField(controller, "portfolioTitleLabel");
                assertEquals("Sample Portfolio", titleLabel.getText());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                latch.countDown();

            }

        });
        latch.await();

    }

    @Test
    public void testSetPortfolioDescription() throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.setPortfolioDescription("This is a sample description");

            try {
                Label descriptionLabel = getPrivateField(controller, "portfolioDescriptionLabel");
                assertEquals("This is a sample description", descriptionLabel.getText());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void testLoadPortfolioArtworks() throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        List<Art> artworks = new ArrayList<>();
        Art art1 = new Art("Artwork 1", 2020);
        art1.setFilePath("file:src/test/resources/mockImage.png");  // Provide a valid image path for testing
        artworks.add(art1);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.loadPortfolioArtworks(artworks);
            try {
                GridPane artGrid = getPrivateField(controller, "artGrid");
                assertEquals(1, artGrid.getChildren().size());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                latch.countDown();

            }
        });
        latch.await();
    }



    // Helper method to set private fields via reflection
    private void setPrivateField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);

    }

    // Helper method to get private fields via reflection
    private <T> T getPrivateField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(object);



    }

}
