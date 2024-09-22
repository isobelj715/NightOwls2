import com.example.addressbook.model.Contact;
import com.example.addressbook.model.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        // Ensure a clean instance of SessionManager before each test
        sessionManager = SessionManager.getInstance();
        sessionManager.clearSession();
    }

    @Test
    public void testSetAndGetLoggedInUser() {
        // Create a new contact to set as logged-in user
        Contact user = new Contact("John", "Doe", "john.doe@example.com", "1234567890", "password123");
        user.setId(1);

        // Set the user in session
        sessionManager.setLoggedInUser(user);

        // Verify the user was set correctly
        assertEquals(user, sessionManager.getLoggedInUser());
        assertEquals(1, sessionManager.getLoggedInUser().getId());
    }

    @Test
    public void testClearSession() {
        // Set a logged-in user
        Contact user = new Contact("John", "Doe", "john.doe@example.com", "1234567890", "password123");
        sessionManager.setLoggedInUser(user);

        // Clear the session
        sessionManager.clearSession();

        // Verify that the logged-in user is null after clearing the session
        assertNull(sessionManager.getLoggedInUser());
    }

    @Test
    public void testNoLoggedInUserInitially() {
        // Initially, there should be no logged-in user
        assertNull(sessionManager.getLoggedInUser());
    }
}
