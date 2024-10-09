import com.example.addressbook.controller.headerController;
import com.example.addressbook.model.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderControllerTest {
    private headerController headerController;
    @Test
    public void testfileLocationheaderhomeinput() {
        headerController headerController = new headerController();
        String output = headerController.fileLocation("headerHome");
        assertEquals(output, "/com/example/addressbook/my-portfolios-view.fxml");
    }
    @Test
    public void testfileLocationheaderUploadinput() {
        headerController headerController = new headerController();
        String output = headerController.fileLocation("headerUpload");
        assertEquals(output, "/com/example/addressbook/upload-portfolio-view.fxml");
    }
    @Test
    public void testfileLocationheaderProfileinput() {
        headerController headerController = new headerController();
        String output = headerController.fileLocation("headerProfile");
        assertEquals(output, "/com/example/addressbook/profile-views.fxml");
    }
}
