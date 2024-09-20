// Import statements
import com.example.addressbook.model.Art;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;public class HeaderControllerTest {

    /**
     * Handles button click events by determining which FXML file to load based on the button's fx:id.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void handleButtonClick(ActionEvent event) {
        // Determine which button was clicked by its fx:id
        String buttonId = ((Node) event.getSource()).getId();

        // Determine which FXML file to load based on the button clicked
        String fxmlFile = getFxmlFilePath(buttonId);

        // Load the corresponding FXML file and switch scenes
        if (!fxmlFile.isEmpty()) {
            try {
                Parent newSceneRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(newSceneRoot));

                if (!stage.isFullScreen()) {
                    stage.setFullScreen(true);
                }

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines the FXML file path based on the provided button ID.
     *
     * @param buttonId The fx:id of the clicked button.
     * @return The corresponding FXML file path, or an empty string if the button ID is unrecognized.
     */
    public String getFxmlFilePath(String buttonId) {
        switch (buttonId) {
            case "headerHome":
                return "/com/example/addressbook/my-portfolios-view.fxml";
            case "headerUpload":
                return "/com/example/addressbook/upload-portfolio-view.fxml";
            // When pages are created, add cases here
            // case "headerProfile":
            //     return "/com/example/addressbook/profile-view.fxml";
            default:
                return "";
        }
    }
}
