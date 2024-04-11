import com.example.addressbook.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ContactManagerTest {
    private ContactManager contactManager;
    private Contact[] contacts = {
            new Contact("John", "Doe", "johndoe@example.com", "0423423423"),
            new Contact("Jane", "Doe", "janedoe@example.com", "0423423424"),
            new Contact("Jay", "Doe", "jaydoe@example.com", "0423423425"),
            new Contact("John", "Smith", "johnsmith@example.com", "0423423426"),
            new Contact("Jane", "Smith", "janesmith@example.com", "0423423427"),
            new Contact("Alice", "Graystone", "aliceg@gmail.com", "0423423428"),
            new Contact("Shane", "Graystone", "shaneg@gmail.com", "0423423429")
    };

    @BeforeEach
    public void setUp() {
        contactManager = new ContactManager(new MockContactDAO());
    }

    @Test
    public void testSearchInOneContact() {
        contactManager.addContact(contacts[0]);
        List<Contact> contacts = contactManager.searchContacts("John");
        assertEquals(1, contacts.size());
        assertEquals(this.contacts[0], contacts.get(0));
    }

    @Test
    public void testSearchInMultipleContacts() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("Doe");
        assertEquals(3, contacts.size());
        for (Contact contact : contacts) {
            assertTrue(contact.getLastName().equals("Doe"));
        }
    }

    @Test
    public void testSearchNoResults() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("Dan");
        assertEquals(0, contacts.size());
    }

    @Test
    public void testSearchEmptyQuery() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("");
        assertEquals(7, contacts.size());
    }

    @Test
    public void testSearchNullQuery() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts(null);
        assertEquals(7, contacts.size());
    }

    @Test
    public void testSearchCaseInsensitive() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("jane");
        assertEquals(2, contacts.size());
        for (Contact contact : contacts) {
            assertTrue(contact.getFirstName().equalsIgnoreCase("Jane"));
        }
    }

    @Test
    public void testSearchPartialQuery() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("ane");
        assertEquals(3, contacts.size());
        assertTrue(contacts.get(0).getFirstName().equals("Jane"));
        assertTrue(contacts.get(1).getFirstName().equals("Jane"));
        assertTrue(contacts.get(2).getFirstName().equals("Shane"));
    }

    @Test
    public void testSearchEmptyContacts() {
        List<Contact> contacts = contactManager.searchContacts("John");
        assertEquals(0, contacts.size());
    }

    @Test
    public void testSearchByEmail() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("example.com");
        assertEquals(5, contacts.size());
    }

    @Test
    public void testSearchByPhone() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("0423423423");
        assertEquals(1, contacts.size());
        assertEquals("John", contacts.get(0).getFirstName());
    }

    @Test
    public void testSearchByFullName() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("Jane Doe");
        assertEquals(1, contacts.size());
        assertEquals("Jane", contacts.get(0).getFirstName());
        assertEquals("Doe", contacts.get(0).getLastName());
    }

    @Test
    public void testSearchByFullNameCaseInsensitive() {
        for (Contact contact : contacts) {
            contactManager.addContact(contact);
        }
        List<Contact> contacts = contactManager.searchContacts("jane doe");
        assertEquals(1, contacts.size());
        assertEquals("Jane", contacts.get(0).getFirstName());
        assertEquals("Doe", contacts.get(0).getLastName());
    }
}