package com.example.addressbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application class for the Address Book application.
 * This class extends the JavaFX Application class and starts the JavaFX lifecycle.
 */
public class HelloApplication extends Application {
    /**
     * The title of the application window.
     */
    public static final String TITLE = "ArtTasker";

    /**
     * The width of the application window, in pixels.
     */
    public static final int WIDTH = 1280;
    /**
     * The height of the application window, in pixels.
     */
    public static final int HEIGHT = 720;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if the fxml file isn't found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-landing.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        // Disable the fullscreen  exit hint
        stage.setFullScreenExitHint("");
        // Make the window fullscreen
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * The main entry point for the application.
     * This method is used to launch the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}