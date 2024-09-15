package com.example.addressbook.controller;

import com.example.addressbook.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The controller class for the Hello view of the Art Tasker application.
 * This class handles the user interactions in the Hello view.
 */
public class HelloController {
    @FXML
    private TextArea termsAndConditions;
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        termsAndConditions.setText("""
1. User Account
    - You must be at least 13 years old to create an account.
    - You agree to provide accurate and complete information during registration.
    - You are responsible for maintaining the confidentiality of your account information and password. ArtTasker is not liable for any loss or damage arising from unauthorised access to your account.

2. Intellectual Property
    - All content uploaded to ArtTasker, including artwork, images, and text, remains your intellectual property.
    - By posting your content, you grant ArtTasker a non-exclusive, worldwide, royalty-free license to display and promote your work within the platform.
    - You agree not to upload content that infringes on the intellectual property rights of others.

3. User Conduct
    - You are solely responsible for any content you upload, share, or display on ArtTasker.
    - You agree not to use the platform for illegal activities, including but not limited to distributing copyrighted material without permission or uploading harmful, inappropriate, or offensive content.
    - ArtTasker reserves the right to remove any content that violates these terms or is deemed inappropriate.

4. Privacy
    - We are committed to protecting your privacy. Please review our Privacy Policy to understand how we collect, use, and share your information.
    - You agree that ArtTasker may use your information as outlined in the Privacy Policy.

5. Termination of Account
    - You may terminate your account at any time. ArtTasker reserves the right to terminate or suspend your account if you violate these Terms and Conditions or engage in conduct that harms the platform or other users.
    - Upon termination, all licenses granted to ArtTasker for displaying your content will expire, but content already shared may remain on the platform.

6. Disclaimer of Warranties
    - ArtTasker is provided "as is" without any warranties of any kind. We do not guarantee the availability or accuracy of the app or that the platform will meet your expectations.

7. Limitation of Liability
    - ArtTasker will not be liable for any indirect, incidental, special, or consequential damages arising from your use of the platform, including loss of data or profit.

8. Changes to the Terms
    - ArtTasker reserves the right to update these Terms and Conditions at any time. We will notify users of any significant changes. Continued use of the platform after changes are made indicates your acceptance of the updated terms.

9. Governing Law
    - These Terms and Conditions are governed by the laws of Australia, without regard to conflict of law principles.

By clicking "I agree to the terms and conditions" and creating an account, you acknowledge that you have read, understood, and agree to be bound by these Terms and Conditions.

If you have any questions or concerns, please contact us at ArtTasker@gmail.com.
""");
    }
    /**
     * Handles the action of clicking the agree checkbox.
     * Enables or disables the next button based on the checkbox state.
     */
    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }
    /**
     * Handles the action of clicking the next button.
     * Loads the main view of the application.
     *
     * @throws IOException if the fxml file for the main view isn't found.
     */
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();

        // EDIT THIS TO CHANGE WHERE BUTTON LEADS TO!!
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
    /**
     * Handles the action of clicking the cancel button.
     * Closes the application.
     */
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }
}