package com.grandstrand;

import com.grandstrand.dao.DatabaseManager;
import com.grandstrand.dao.ContactDaoSqlite;
import com.grandstrand.domain.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ContactDaoSqlite.
 * Uses a temporary database file per test for isolation.
 */
class ContactDaoSqliteTest {
    private ContactDaoSqlite dao;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseManager dbManager = new DatabaseManager(tempDir.resolve("test.db").toString());
        dbManager.initializeSchema();
        dao = new ContactDaoSqlite(dbManager);
    }

    @Test
    void testSaveAndFindById() {
        Contact contact = new Contact("C001", "John", "Doe", "1234567890", "123 Main St");
        dao.save(contact);
        Optional<Contact> found = dao.findById("C001");
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void testSaveUpdatesExisting() {
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        dao.save(new Contact("C001", "Jane", "Doe", "1234567890", "123 Main St"));
        assertEquals("Jane", dao.findById("C001").get().getFirstName());
        assertEquals(1, dao.count());
    }

    @Test
    void testFindByIdNotFound() {
        assertFalse(dao.findById("NOPE").isPresent());
    }

    @Test
    void testFindAll() {
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        dao.save(new Contact("C002", "Jane", "Smith", "0987654321", "456 Oak Ave"));
        List<Contact> all = dao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testDeleteById() {
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        dao.deleteById("C001");
        assertFalse(dao.existsById("C001"));
    }

    @Test
    void testExistsById() {
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        assertTrue(dao.existsById("C001"));
        assertFalse(dao.existsById("NOPE"));
        assertFalse(dao.existsById(null));
    }

    @Test
    void testCount() {
        assertEquals(0, dao.count());
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        assertEquals(1, dao.count());
    }

    @Test
    void testClear() {
        dao.save(new Contact("C001", "John", "Doe", "1234567890", "123 Main St"));
        dao.clear();
        assertEquals(0, dao.count());
    }

    @Test
    void testSqlInjectionSafe() {
        // Classic SQL injection attempt — must be treated as a literal string
        String malicious = "x' OR '1'='1";
        assertFalse(dao.existsById(malicious));
        assertEquals(0, dao.count());
    }

    @Test
    void testDatabaseConstraintEnforcement() {
        // Phone with letters violates the GLOB constraint in the schema
        assertThrows(Exception.class, () -> {
            dao.save(new Contact("C001", "John", "Doe", "123456789A", "123 Main St"));
        });
    }
}
