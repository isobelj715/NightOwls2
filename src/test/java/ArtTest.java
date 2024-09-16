import com.example.addressbook.model.Art;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtTest {
    private Art art;

    @BeforeEach
    public void setUp() {
        art = new Art("Test", 2000);
    }

    @Test
    public void testGetId() {
        art.setId(1);
        assertEquals(1, art.getId());
    }
    @Test
    public void testGetArtTitle() {
        assertEquals("Test", art.getArtTitle());
    }

    @Test
    public void testSetArtTitle() {
        art.setArtTitle("Test2");
        assertEquals("Test2", art.getArtTitle());
    }

    @Test
    public void testGetArtYear() {
        assertEquals(2000, art.getYear());
    }

    @Test
    public void testSetArtYear() {
        art.setYear(2001);
        assertEquals(2001, art.getYear());
    }
}

