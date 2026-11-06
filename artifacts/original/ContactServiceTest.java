import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contact Service - ContactServiceTest Class
 * 
 * Student: Allan Torres
 * Course: CS-320-11659-M01
 * Date: July 28, 2025
 * 
 * Developed with assistance from Cascade AI on Windsurf Platform
 * 
 * Unit tests for the ContactService class
 * Tests all service requirements including add, delete, and update operations
 */
public class ContactServiceTest {
    
    private ContactService contactService;
    private Contact testContact1;
    private Contact testContact2;
    
    @BeforeEach
    void setUp() {
        contactService = new ContactService();
        testContact1 = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
        testContact2 = new Contact("0987654321", "Jane", "Smith", "5559876543", "456 Oak Ave");
    }
    
    // Add Contact Tests
    @Test
    void testAddContactValid() {
        contactService.addContact(testContact1);
        
        assertEquals(1, contactService.getContactCount());
        assertTrue(contactService.contactExists("1234567890"));
        assertEquals(testContact1, contactService.getContact("1234567890"));
    }
    
    @Test
    void testAddMultipleContacts() {
        contactService.addContact(testContact1);
        contactService.addContact(testContact2);
        
        assertEquals(2, contactService.getContactCount());
        assertTrue(contactService.contactExists("1234567890"));
        assertTrue(contactService.contactExists("0987654321"));
    }
    
    @Test
    void testAddContactNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(null);
        });
    }
    
    @Test
    void testAddContactDuplicateId() {
        contactService.addContact(testContact1);
        
        // Try to add another contact with the same ID
        Contact duplicateContact = new Contact("1234567890", "Jane", "Smith", "5559876543", "456 Oak Ave");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(duplicateContact);
        });
        
        // Verify original contact is still there and count is still 1
        assertEquals(1, contactService.getContactCount());
        assertEquals("John", contactService.getContact("1234567890").getFirstName());
    }
    
    // Delete Contact Tests
    @Test
    void testDeleteContactValid() {
        contactService.addContact(testContact1);
        contactService.addContact(testContact2);
        
        assertEquals(2, contactService.getContactCount());
        
        contactService.deleteContact("1234567890");
        
        assertEquals(1, contactService.getContactCount());
        assertFalse(contactService.contactExists("1234567890"));
        assertTrue(contactService.contactExists("0987654321"));
    }
    
    @Test
    void testDeleteContactNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact(null);
        });
    }
    
    @Test
    void testDeleteContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("nonexistent");
        });
    }
    
    @Test
    void testDeleteAllContacts() {
        contactService.addContact(testContact1);
        contactService.addContact(testContact2);
        
        contactService.deleteContact("1234567890");
        contactService.deleteContact("0987654321");
        
        assertEquals(0, contactService.getContactCount());
    }
    
    // Update First Name Tests
    @Test
    void testUpdateFirstNameValid() {
        contactService.addContact(testContact1);
        
        contactService.updateFirstName("1234567890", "Johnny");
        
        assertEquals("Johnny", contactService.getContact("1234567890").getFirstName());
    }
    
    @Test
    void testUpdateFirstNameContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("nonexistent", "Johnny");
        });
    }
    
    @Test
    void testUpdateFirstNameNull() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("1234567890", null);
        });
    }
    
    @Test
    void testUpdateFirstNameTooLong() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("1234567890", "12345678901");
        });
    }
    
    // Update Last Name Tests
    @Test
    void testUpdateLastNameValid() {
        contactService.addContact(testContact1);
        
        contactService.updateLastName("1234567890", "Johnson");
        
        assertEquals("Johnson", contactService.getContact("1234567890").getLastName());
    }
    
    @Test
    void testUpdateLastNameContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("nonexistent", "Johnson");
        });
    }
    
    @Test
    void testUpdateLastNameNull() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("1234567890", null);
        });
    }
    
    @Test
    void testUpdateLastNameTooLong() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("1234567890", "12345678901");
        });
    }
    
    // Update Phone Tests
    @Test
    void testUpdatePhoneValid() {
        contactService.addContact(testContact1);
        
        contactService.updatePhone("1234567890", "5559998888");
        
        assertEquals("5559998888", contactService.getContact("1234567890").getPhone());
    }
    
    @Test
    void testUpdatePhoneContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("nonexistent", "5559998888");
        });
    }
    
    @Test
    void testUpdatePhoneNull() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("1234567890", null);
        });
    }
    
    @Test
    void testUpdatePhoneInvalidLength() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("1234567890", "555999888");
        });
    }
    
    @Test
    void testUpdatePhoneNonDigits() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("1234567890", "555-999-888");
        });
    }
    
    // Update Address Tests
    @Test
    void testUpdateAddressValid() {
        contactService.addContact(testContact1);
        
        contactService.updateAddress("1234567890", "789 Pine Street");
        
        assertEquals("789 Pine Street", contactService.getContact("1234567890").getAddress());
    }
    
    @Test
    void testUpdateAddressContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("nonexistent", "789 Pine Street");
        });
    }
    
    @Test
    void testUpdateAddressNull() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("1234567890", null);
        });
    }
    
    @Test
    void testUpdateAddressTooLong() {
        contactService.addContact(testContact1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("1234567890", "1234567890123456789012345678901");
        });
    }
    
    // Get Contact Tests
    @Test
    void testGetContactValid() {
        contactService.addContact(testContact1);
        
        Contact retrieved = contactService.getContact("1234567890");
        
        assertEquals(testContact1, retrieved);
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
    }
    
    @Test
    void testGetContactNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact(null);
        });
    }
    
    @Test
    void testGetContactNotExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact("nonexistent");
        });
    }
    
    // Contact Exists Tests
    @Test
    void testContactExistsTrue() {
        contactService.addContact(testContact1);
        
        assertTrue(contactService.contactExists("1234567890"));
    }
    
    @Test
    void testContactExistsFalse() {
        assertFalse(contactService.contactExists("nonexistent"));
    }
    
    @Test
    void testContactExistsNull() {
        assertFalse(contactService.contactExists(null));
    }
    
    // Contact Count Tests
    @Test
    void testContactCountEmpty() {
        assertEquals(0, contactService.getContactCount());
    }
    
    @Test
    void testContactCountAfterOperations() {
        assertEquals(0, contactService.getContactCount());
        
        contactService.addContact(testContact1);
        assertEquals(1, contactService.getContactCount());
        
        contactService.addContact(testContact2);
        assertEquals(2, contactService.getContactCount());
        
        contactService.deleteContact("1234567890");
        assertEquals(1, contactService.getContactCount());
        
        contactService.deleteContact("0987654321");
        assertEquals(0, contactService.getContactCount());
    }
    
    // Clear All Contacts Test
    @Test
    void testClearAllContacts() {
        contactService.addContact(testContact1);
        contactService.addContact(testContact2);
        
        assertEquals(2, contactService.getContactCount());
        
        contactService.clearAllContacts();
        
        assertEquals(0, contactService.getContactCount());
        assertFalse(contactService.contactExists("1234567890"));
        assertFalse(contactService.contactExists("0987654321"));
    }
    
    // Integration Tests - Multiple Operations
    @Test
    void testCompleteWorkflow() {
        // Add contacts
        contactService.addContact(testContact1);
        contactService.addContact(testContact2);
        assertEquals(2, contactService.getContactCount());
        
        // Update contact information
        contactService.updateFirstName("1234567890", "Johnny");
        contactService.updateLastName("1234567890", "Johnson");
        contactService.updatePhone("1234567890", "5559998888");
        contactService.updateAddress("1234567890", "789 Pine Street");
        
        // Verify updates
        Contact updated = contactService.getContact("1234567890");
        assertEquals("Johnny", updated.getFirstName());
        assertEquals("Johnson", updated.getLastName());
        assertEquals("5559998888", updated.getPhone());
        assertEquals("789 Pine Street", updated.getAddress());
        
        // Verify other contact unchanged
        Contact unchanged = contactService.getContact("0987654321");
        assertEquals("Jane", unchanged.getFirstName());
        assertEquals("Smith", unchanged.getLastName());
        
        // Delete one contact
        contactService.deleteContact("0987654321");
        assertEquals(1, contactService.getContactCount());
        assertTrue(contactService.contactExists("1234567890"));
        assertFalse(contactService.contactExists("0987654321"));
    }
    
    @Test
    void testContactIdImmutabilityThroughService() {
        contactService.addContact(testContact1);
        
        // Verify that contact ID cannot be changed through service operations
        String originalId = contactService.getContact("1234567890").getContactId();
        assertEquals("1234567890", originalId);
        
        // Update other fields
        contactService.updateFirstName("1234567890", "Johnny");
        contactService.updateLastName("1234567890", "Johnson");
        contactService.updatePhone("1234567890", "5559998888");
        contactService.updateAddress("1234567890", "789 Pine Street");
        
        // Contact ID should remain unchanged
        assertEquals("1234567890", contactService.getContact("1234567890").getContactId());
    }
}
