package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;

import com.example.addressbook.model.SessionManager;  // Importing SessionManager

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class BaseControllerTest {

    private BaseController baseController;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {

        // Initialize JavaFX
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

    }

    @BeforeEach
    public void setUp() {
        baseController = new BaseController();

    }

    // Test method
    @Test
    public void testShowAlert() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {

            baseController.showAlert("Test Title", "Test Message");



            Alert expectedAlert = new Alert(Alert.AlertType.INFORMATION, "Test Message", ButtonType.OK);
            expectedAlert.setTitle("Test Title");


            Optional<ButtonType> result = expectedAlert.showAndWait();
            assertTrue(result.isPresent() && result.get() == ButtonType.OK);

            latch.countDown();

        });

        latch.await();
    }

    // Test for no user is logged
    @Test
    public void testGetLoggedInUserId_NoUser() {
        SessionManager.getInstance().clearSession();

        int userId = baseController.getLoggedInUserId();

        assertEquals(-1, userId, "Expected no user to be logged in, so user ID should be -1");

    }

    // Test user is logged in
    @Test
    public void testGetLoggedInUserId_WithUser() {

        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.clearSession();

        // Create a test user
        Contact testUser = new Contact("John", "Doe", "john@example.com", "1234567890", "password123");

        testUser.setId(1); // Set a test user ID
        sessionManager.setLoggedInUser(testUser);


        int userId = baseController.getLoggedInUserId();
        assertEquals(1, userId, "Expected logged-in user ID to be 1");


    }

}
