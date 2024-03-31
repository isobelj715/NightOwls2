module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.addressbook to javafx.fxml;
    exports com.example.addressbook;
    exports com.example.addressbook.controller;
    opens com.example.addressbook.controller to javafx.fxml;
    exports com.example.addressbook.model;
    opens com.example.addressbook.model to javafx.fxml;
}