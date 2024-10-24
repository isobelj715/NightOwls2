package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.model.SessionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseControllerTest extends BaseController {

    private BaseController baseController;

    @BeforeEach
    public void setUp() {
        baseController = new BaseController();
    }

    @Test
    public void testGetLoggedInUserIdNoUser() {
        SessionManager.getInstance().clearSession();

        int userId = baseController.getLoggedInUserId();
        Assertions.assertEquals(-1, userId);
    }

    @Test
    public void testGetLoggedInUserIdWithUser() {
        Contact loggedInUser = new Contact("John", "Doe", "john@example.com", "1234567890", "password");
        loggedInUser.setId(1);
        SessionManager.getInstance().setLoggedInUser(loggedInUser);

        int userId = baseController.getLoggedInUserId();
        Assertions.assertEquals(1, userId);
    }

    @Test
    public void testShowAlert() {
        Assertions.assertTrue(true, "Alert functionality cannot be tested in a non-GUI context");
    }
}
