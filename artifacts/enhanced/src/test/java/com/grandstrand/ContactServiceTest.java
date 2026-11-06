package com.grandstrand;

import com.grandstrand.domain.Contact;
import com.grandstrand.repository.ContactRepository;
import com.grandstrand.repository.InMemoryContactRepository;
import com.grandstrand.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ContactService with dependency injection.
 */
class ContactServiceTest {
    private ContactService contactService;
    private ContactRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryContactRepository();
        contactService = new ContactService(repository);
    }

    @Test
    void testAddContact() {
        Contact contact = contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        assertNotNull(contact);
        assertEquals("C001", contact.getContactId());
        assertEquals(1, contactService.getContactCount());
    }

    @Test
    void testAddDuplicateContact() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("C001", "Jane", "Smith", "0987654321", "456 Oak Ave");
        });
    }

    @Test
    void testGetContact() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        Contact contact = contactService.getContact("C001");
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testGetNonExistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact("NONEXISTENT");
        });
    }

    @Test
    void testUpdateFirstName() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        contactService.updateFirstName("C001", "Jane");
        assertEquals("Jane", contactService.getContact("C001").getFirstName());
    }

    @Test
    void testDeleteContact() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        contactService.deleteContact("C001");
        assertEquals(0, contactService.getContactCount());
    }

    @Test
    void testDeleteNonExistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("NONEXISTENT");
        });
    }

    @Test
    void testContactExists() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        assertTrue(contactService.contactExists("C001"));
        assertFalse(contactService.contactExists("NONEXISTENT"));
    }

    @Test
    void testClearAllContacts() {
        contactService.addContact("C001", "John", "Doe", "1234567890", "123 Main St");
        contactService.addContact("C002", "Jane", "Smith", "0987654321", "456 Oak Ave");
        contactService.clearAllContacts();
        assertEquals(0, contactService.getContactCount());
    }
}
