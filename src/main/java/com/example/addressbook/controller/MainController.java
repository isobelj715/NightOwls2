package com.example.addressbook.controller;

import com.example.addressbook.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;
/**
 * The controller class for the Main view of the Address Book application.
 * This class handles the user interactions in the Main view.
 */
public class MainController {
    @FXML
    private ListView<Contact> contactsListView;
    private ContactManager contactManager;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private VBox contactContainer;
    @FXML
    private TextField searchTextField;
    /**
     * Constructs a new MainController with a ContactManager that uses a SqliteContactDAO
     * to perform CRUD operations on contacts.
     */
    public MainController() {
        contactManager = new ContactManager(new SqliteContactDAO());
    }

    /**
     * Programmatically selects a contact in the list view and
     * updates the text fields with the contact's information.
     * @param contact The contact to select.
     */
    private void selectContact(Contact contact) {
        contactsListView.getSelectionModel().select(contact);
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        emailTextField.setText(contact.getEmail());
        phoneTextField.setText(contact.getPhone());
    }

    /**
     * Renders a cell in the contacts list view by setting the text to the contact's full name.
     * @param contactListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Contact> renderCell(ListView<Contact> contactListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a contact is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onContactSelected(MouseEvent mouseEvent) {
                ListCell<Contact> clickedCell = (ListCell<Contact>) mouseEvent.getSource();
                // Get the selected contact from the list view
                Contact selectedContact = clickedCell.getItem();
                if (selectedContact != null) selectContact(selectedContact);
            }

            /**
             * Updates the item in the cell by setting the text to the contact's full name.
             * @param contact The contact to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || contact == null || contact.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onContactSelected);
                } else {
                    setText(contact.getFullName());
                }
            }
        };
    }

    /**
     * Synchronizes the contacts list view with the contacts in the database.
     */
    private void syncContacts() {
        contactsListView.getItems().clear();
        // --- New code to search for contacts ---
        String query = searchTextField.getText();
        List<Contact> contacts = contactManager.searchContacts(query);
        // --- End of new code ---
        boolean hasContact = !contacts.isEmpty();
        if (hasContact) {
            contactsListView.getItems().addAll(contacts);
        }
        // Show / hide based on whether there are contacts
        contactContainer.setVisible(hasContact);
    }
    /**
     * Initializes the controller class by setting up the contacts list view and
     * selecting the first contact in the list view.
     */
    @FXML
    public void initialize() {
        contactsListView.setCellFactory(this::renderCell);
        syncContacts();
        // Select the first contact and display its information
        contactsListView.getSelectionModel().selectFirst();
        Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
        if (firstContact != null) {
            selectContact(firstContact);
        }
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> syncContacts());
    }
    /**
     * Handles the action of confirming the edit of a contact.
     * Updates the selected contact's information in the database.
     */
    @FXML
    private void onEditConfirm() {
        // Get the selected contact from the list view
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            selectedContact.setFirstName(firstNameTextField.getText());
            selectedContact.setLastName(lastNameTextField.getText());
            selectedContact.setEmail(emailTextField.getText());
            selectedContact.setPhone(phoneTextField.getText());
            contactManager.updateContact(selectedContact);
            syncContacts();
        }
    }
    /**
     * Handles the action of deleting a contact.
     * Deletes the selected contact from the database.
     */
    @FXML
    private void onDelete() {
        // Get the selected contact from the list view
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contactManager.deleteContact(selectedContact);
            syncContacts();
        }
    }
    /**
     * Handles the action of adding a new contact.
     * Adds a new contact with default values to the database.
     */

    // USE ****************************************************
    @FXML
    private void onAdd() {
        // Default values for a new contact
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Contact";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        final String DEFAULT_PASSWORD = "";
        Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE, DEFAULT_PASSWORD);
        // Add the new contact to the database
        contactManager.addContact(newContact);
        syncContacts();
        // Select the new contact in the list view
        // and focus the first name text field


        //REMOVING VIEW OF CONTACT:
        selectContact(newContact);
        firstNameTextField.requestFocus();
    }
    /**
     * Handles the action of cancelling the edit of a contact.
     * Reverts the text fields to the selected contact's information.
     */
    @FXML
    private void onCancel() {
        // Find the selected contact
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            // Since the contact hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectContact(selectedContact);
        }
    }
}