import com.example.addressbook.model.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortfolioTest {
    private Portfolio artPortfolio;

    @BeforeEach
    public void setUp() {
        artPortfolio = new Portfolio("Modern Art Collection", "A curated selection of modern art pieces", 1);
    }


    @Test
    public void testGetId() {
        artPortfolio.setId(1);
        assertEquals(1, artPortfolio.getId());
    }

    @Test
    public void testSetId() {
        artPortfolio.setId(2);
        assertEquals(2, artPortfolio.getId());
    }

    @Test
    public void testGetPortfolioName() {
        assertEquals("Modern Art Collection", artPortfolio.getPortfolioName());
    }

    @Test
    public void testSetPortfolioName() {
        artPortfolio.setPortfolioName("Contemporary Art", "A portfolio showcasing contemporary art pieces");
        assertEquals("Contemporary Art", artPortfolio.getPortfolioName());
        assertEquals("A portfolio showcasing contemporary art pieces", artPortfolio.getPortfolioDescription());
    }

    @Test
    public void testGetPortfolioDescription() {
        assertEquals("A curated selection of modern art pieces", artPortfolio.getPortfolioDescription());
    }

    @Test
    public void testSetPortfolioDescription() {
        artPortfolio.setPortfolioDescription("An exclusive collection of abstract paintings");
        assertEquals("An exclusive collection of abstract paintings", artPortfolio.getPortfolioDescription());
    }

    @Test
    public void testGetContactID() {
        assertEquals(1, artPortfolio.getContactID());
    }

    @Test
    public void testSetContactID() {
        artPortfolio.setContactID(2);
        assertEquals(2, artPortfolio.getContactID());
    }

    @Test
    public void testGetPortfolioTitle() {
        assertEquals("Modern Art Collection", artPortfolio.getPortfolioTitle());
    }

    @Test
    public void testSetPortfolioTitle() {
        artPortfolio.setPortfolioTitle("Abstract Art Showcase");
        assertEquals("Abstract Art Showcase", artPortfolio.getPortfolioTitle());
    }




    @Test
    public void testSetNullValues() {
        // Setting both the name and description to null
        artPortfolio.setPortfolioName(null, null);

        assertEquals(null, artPortfolio.getPortfolioName());
        assertEquals(null, artPortfolio.getPortfolioDescription());
    }

    @Test
    public void testSetEmptyValues() {
        artPortfolio.setPortfolioName("", "");

        assertEquals("", artPortfolio.getPortfolioName());
        assertEquals("", artPortfolio.getPortfolioDescription());
    }



}
