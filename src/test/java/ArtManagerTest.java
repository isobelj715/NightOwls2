import com.example.addressbook.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtManagerTest {
    private ArtManager artManager;
    private Art[] arts = {
            new Art("Test1", 2000),
            new Art("Test2", 2000),
            new Art("Test3", 2000)
    };

    @BeforeEach
    public void setUp() {
        artManager = new ArtManager(new IArtDAO() {
            public final ArrayList<Art> arts = new ArrayList<>();
            private int autoIncrementedId = 0;

            @Override
            public void addArt(Art art) {
                art.setId(autoIncrementedId);
                autoIncrementedId++;
                arts.add(art);
            }

            @Override
            public void updateArt(Art art) {
                for (int i = 0; i < arts.size(); i++) {
                    if (arts.get(i).getId() == art.getId()) {
                        arts.set(i, art);
                        break;
                    }
                }

            }

            @Override
            public void deleteArt(Art art) {
                arts.remove(art);
            }

            @Override
            public Art getArt(int id) {
                for (Art art : arts) {
                    if (art.getId() == id) {
                        return art;
                    }
                }
                return null;
            }

            @Override
            public List<Art> getAllArt() {
                return new ArrayList<>(arts);
            }
        });
    }

    @Test
    public void testSearchInOneArt() {
        artManager.addArt(arts[0]);
        List<Art> arts = artManager.searchArt("Test1");
        assertEquals(1, arts.size());
        assertEquals(this.arts[0], arts.get(0));
    }

    @Test
    public void testSearchInMultipleArt() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt(String.valueOf(2000));
        assertEquals(3, arts.size());
        for (Art art : arts) {
            assertTrue(art.getYear().equals(2000));
        }
    }

    @Test
    public void testSearchNoResults() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt("Wrong");
        assertEquals(0, arts.size());
    }

    @Test
    public void testSearchEmptyQuery() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt("");
        assertEquals(3, arts.size());
    }

    @Test
    public void testSearchNullQuery() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt(null);
        assertEquals(3, arts.size());
    }

    @Test
    public void testSearchCaseInsensitive() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt("test1");
        assertEquals(1, arts.size());
        for (Art art : arts) {
            assertTrue(art.getArtTitle().equalsIgnoreCase("Test1"));
        }
    }

    @Test
    public void testSearchPartialQuery() {
        for (Art art : arts) {
            artManager.addArt(art);
        }
        List<Art> arts = artManager.searchArt("Test");
        assertEquals(3, arts.size());
        assertTrue(arts.get(0).getArtTitle().equals("Test1"));
        assertTrue(arts.get(1).getArtTitle().equals("Test2"));
        assertTrue(arts.get(2).getArtTitle().equals("Test3"));
    }

    @Test
    public void testSearchEmptyArt() {
        List<Art> arts = artManager.searchArt("Test4");
        assertEquals(0, arts.size());
    }

}
