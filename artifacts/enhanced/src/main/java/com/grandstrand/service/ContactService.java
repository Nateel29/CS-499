package com.grandstrand.service;

import com.grandstrand.domain.Contact;
import com.grandstrand.repository.ContactRepository;
import java.util.List;

/**
 * ContactService with dependency injection of ContactRepository.
 * Enhanced from original CS 320 version to use the Repository pattern.
 */
public class ContactService {
    private final ContactRepository repository;

    /**
     * Constructor with dependency injection.
     * @param repository the ContactRepository implementation to use
     */
    public ContactService(ContactRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    public Contact addContact(String contactId, String firstName, String lastName, String phone, String address) {
        if (repository.existsById(contactId)) {
            throw new IllegalArgumentException("Contact with ID " + contactId + " already exists");
        }
        Contact contact = new Contact(contactId, firstName, lastName, phone, address);
        return repository.save(contact);
    }

    public Contact getContact(String contactId) {
        return repository.findById(contactId)
            .orElseThrow(() -> new IllegalArgumentException("Contact with ID " + contactId + " does not exist"));
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public void deleteContact(String contactId) {
        if (!repository.existsById(contactId)) {
            throw new IllegalArgumentException("Contact with ID " + contactId + " does not exist");
        }
        repository.deleteById(contactId);
    }

    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getContact(contactId);
        contact.setFirstName(firstName);
        repository.save(contact);
    }

    public void updateLastName(String contactId, String lastName) {
        Contact contact = getContact(contactId);
        contact.setLastName(lastName);
        repository.save(contact);
    }

    public void updatePhone(String contactId, String phone) {
        Contact contact = getContact(contactId);
        contact.setPhone(phone);
        repository.save(contact);
    }

    public void updateAddress(String contactId, String address) {
        Contact contact = getContact(contactId);
        contact.setAddress(address);
        repository.save(contact);
    }

    public int getContactCount() {
        return repository.count();
    }

    public boolean contactExists(String contactId) {
        return repository.existsById(contactId);
    }

    public void clearAllContacts() {
        repository.clear();
    }
}
