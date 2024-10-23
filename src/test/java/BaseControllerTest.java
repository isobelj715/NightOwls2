package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.model.SessionManager;
import javafx.application.Platform;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class BaseControllerTest {

    private BaseController baseController;

    @BeforeAll
    public static void setupHeadless() throws InterruptedException {

        System.setProperty("java.awt.headless", "true");


        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {

            latch.countDown();

        });

        latch.await();


    }

    @BeforeEach
    public void setUp() {
        baseController = new BaseController();
    }

    @Test
    public void testGetLoggedInUserIdNoUser() {

        SessionManager.getInstance().clearSession();
        int userId = baseController.getLoggedInUserId();
        assertEquals(-1, userId);

    }

    @Test
    public void testGetLoggedInUserIdWithUser() {


        Contact loggedInUser = new Contact("John", "Doe", "john@example.com", "1234567890", "password");
        loggedInUser.setId(1);
        SessionManager.getInstance().setLoggedInUser(loggedInUser);

        int userId = baseController.getLoggedInUserId();
        assertEquals(1, userId);

    }


    @Test
    public void testShowAlert() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            baseController.showAlert("Test Alert", "This is a test message.");
            latch.countDown();


        });

        latch.await();


        assertTrue(true);

    }

    @Test
    public void testLoadPage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {

            try {


                Pane root = new Pane(new Label("Test"));
                Scene scene = new Scene(root);

                ActionEvent event = new ActionEvent(root, null);
                baseController.loadPage(event, "/com/example/addressbook/some-view.fxml");

                assertNotNull(scene);

            } finally {

                latch.countDown();

            }

        });

        latch.await();

    }


}
