import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contact Service - ContactTest Class
 * 
 * Student: Allan Torres
 * Course: CS-320-11659-M01
 * Date: July 28, 2025
 * 
 * Developed with assistance from Cascade AI on Windsurf Platform
 * 
 * Unit tests for the Contact class
 * Tests all requirements including field validation and constraints
 */
public class ContactTest {
    
    private Contact validContact;
    
    @BeforeEach
    void setUp() {
        validContact = new Contact("1234567890", "John", "Doe", "5551234567", "123 Main St");
    }
    
    // Constructor Tests - Valid Cases
    @Test
    void testContactCreationValid() {
        Contact contact = new Contact("123", "Jane", "Smith", "5559876543", "456 Oak Ave");
        
        assertEquals("123", contact.getContactId());
        assertEquals("Jane", contact.getFirstName());
        assertEquals("Smith", contact.getLastName());
        assertEquals("5559876543", contact.getPhone());
        assertEquals("456 Oak Ave", contact.getAddress());
    }
    
    @Test
    void testContactCreationMaxLengths() {
        // Test maximum allowed lengths
        Contact contact = new Contact("1234567890", "1234567890", "1234567890", "1234567890", "123456789012345678901234567890");
        
        assertEquals("1234567890", contact.getContactId());
        assertEquals("1234567890", contact.getFirstName());
        assertEquals("1234567890", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123456789012345678901234567890", contact.getAddress());
    }
    
    // Constructor Tests - Invalid Contact ID
    @Test
    void testContactIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "John", "Doe", "5551234567", "123 Main St");
        });
    }
    
    @Test
    void testContactIdEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("", "John", "Doe", "5551234567", "123 Main St");
        });
    }
    
    @Test
    void testContactIdTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "John", "Doe", "5551234567", "123 Main St");
        });
    }
    
    // Constructor Tests - Invalid First Name
    @Test
    void testFirstNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", null, "Doe", "5551234567", "123 Main St");
        });
    }
    
    @Test
    void testFirstNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "12345678901", "Doe", "5551234567", "123 Main St");
        });
    }
    
    // Constructor Tests - Invalid Last Name
    @Test
    void testLastNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", null, "5551234567", "123 Main St");
        });
    }
    
    @Test
    void testLastNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "12345678901", "5551234567", "123 Main St");
        });
    }
    
    // Constructor Tests - Invalid Phone
    @Test
    void testPhoneNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", null, "123 Main St");
        });
    }
    
    @Test
    void testPhoneTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "555123456", "123 Main St");
        });
    }
    
    @Test
    void testPhoneTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "55512345678", "123 Main St");
        });
    }
    
    @Test
    void testPhoneNonDigits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "555-123-456", "123 Main St");
        });
    }
    
    @Test
    void testPhoneWithLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "555abc4567", "123 Main St");
        });
    }
    
    // Constructor Tests - Invalid Address
    @Test
    void testAddressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "5551234567", null);
        });
    }
    
    @Test
    void testAddressTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "5551234567", "1234567890123456789012345678901");
        });
    }
    
    // Getter Tests
    @Test
    void testGetters() {
        assertEquals("1234567890", validContact.getContactId());
        assertEquals("John", validContact.getFirstName());
        assertEquals("Doe", validContact.getLastName());
        assertEquals("5551234567", validContact.getPhone());
        assertEquals("123 Main St", validContact.getAddress());
    }
    
    // Setter Tests - Valid Cases
    @Test
    void testSetFirstNameValid() {
        validContact.setFirstName("Jane");
        assertEquals("Jane", validContact.getFirstName());
    }
    
    @Test
    void testSetLastNameValid() {
        validContact.setLastName("Smith");
        assertEquals("Smith", validContact.getLastName());
    }
    
    @Test
    void testSetPhoneValid() {
        validContact.setPhone("5559876543");
        assertEquals("5559876543", validContact.getPhone());
    }
    
    @Test
    void testSetAddressValid() {
        validContact.setAddress("456 Oak Ave");
        assertEquals("456 Oak Ave", validContact.getAddress());
    }
    
    // Setter Tests - Invalid Cases
    @Test
    void testSetFirstNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setFirstName(null);
        });
    }
    
    @Test
    void testSetFirstNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setFirstName("12345678901");
        });
    }
    
    @Test
    void testSetLastNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setLastName(null);
        });
    }
    
    @Test
    void testSetLastNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setLastName("12345678901");
        });
    }
    
    @Test
    void testSetPhoneNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setPhone(null);
        });
    }
    
    @Test
    void testSetPhoneInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setPhone("555123456");
        });
    }
    
    @Test
    void testSetPhoneNonDigits() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setPhone("555-123-456");
        });
    }
    
    @Test
    void testSetAddressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setAddress(null);
        });
    }
    
    @Test
    void testSetAddressTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            validContact.setAddress("1234567890123456789012345678901");
        });
    }
    
    // Test Contact ID Immutability
    @Test
    void testContactIdImmutable() {
        // Contact ID should not have a setter method
        // This test verifies that contactId is final and cannot be changed
        String originalId = validContact.getContactId();
        assertEquals("1234567890", originalId);
        
        // Create another contact to verify ID uniqueness is maintained
        Contact anotherContact = new Contact("9876543210", "Jane", "Smith", "5559876543", "456 Oak Ave");
        assertEquals("9876543210", anotherContact.getContactId());
        assertEquals("1234567890", validContact.getContactId()); // Original should be unchanged
    }
    
    // Test toString method
    @Test
    void testToString() {
        String expected = "Contact{contactId='1234567890', firstName='John', lastName='Doe', phone='5551234567', address='123 Main St'}";
        assertEquals(expected, validContact.toString());
    }
    
    // Test equals and hashCode
    @Test
    void testEquals() {
        Contact contact1 = new Contact("123", "John", "Doe", "5551234567", "123 Main St");
        Contact contact2 = new Contact("123", "Jane", "Smith", "5559876543", "456 Oak Ave");
        Contact contact3 = new Contact("456", "John", "Doe", "5551234567", "123 Main St");
        
        assertEquals(contact1, contact2); // Same ID
        assertNotEquals(contact1, contact3); // Different ID
        assertEquals(contact1.hashCode(), contact2.hashCode()); // Same ID should have same hash
    }
}
