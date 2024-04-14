import com.example.addressbook.model.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DatabaseTest {
    @Test
    public void testConnection() {
        Connection conn = SqliteConnection.getInstance();
        assertEquals(true, conn != null);
    }
}
