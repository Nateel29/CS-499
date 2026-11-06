package com.grandstrand.repository;

import com.grandstrand.domain.Contact;
import java.util.*;

/**
 * In-memory implementation of ContactRepository using HashMap.
 * Preserves the original CS 320 behavior while implementing the new interface.
 */
public class InMemoryContactRepository implements ContactRepository {
    private final Map<String, Contact> contacts = new HashMap<>();

    @Override
    public Contact save(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        contacts.put(contact.getContactId(), contact);
        return contact;
    }

    @Override
    public Optional<Contact> findById(String contactId) {
        return Optional.ofNullable(contacts.get(contactId));
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }

    @Override
    public void deleteById(String contactId) {
        contacts.remove(contactId);
    }

    @Override
    public boolean existsById(String contactId) {
        return contactId != null && contacts.containsKey(contactId);
    }

    @Override
    public int count() {
        return contacts.size();
    }

    @Override
    public void clear() {
        contacts.clear();
    }
}
