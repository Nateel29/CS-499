package com.grandstrand.api;

import com.grandstrand.domain.Contact;
import com.grandstrand.dto.ContactDto;
import com.grandstrand.service.ContactService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API controller for Contact operations.
 * Part of the presentation layer in the enhanced architecture.
 * 
 * Note: This is a simplified controller showing the API design.
 * A full implementation would use a framework like Javalin or Spring Boot
 * with actual HTTP endpoints, JSON serialization, and error handling.
 */
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        if (contactService == null) {
            throw new IllegalArgumentException("ContactService cannot be null");
        }
        this.contactService = contactService;
    }

    /**
     * POST /api/contacts
     * Creates a new contact.
     */
    public ContactDto createContact(ContactDto dto) {
        Contact contact = contactService.addContact(
            dto.getContactId(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getPhone(),
            dto.getAddress()
        );
        return toDto(contact);
    }

    /**
     * GET /api/contacts/{id}
     * Retrieves a contact by ID.
     */
    public ContactDto getContact(String contactId) {
        Contact contact = contactService.getContact(contactId);
        return toDto(contact);
    }

    /**
     * GET /api/contacts
     * Retrieves all contacts.
     */
    public List<ContactDto> getAllContacts() {
        return contactService.getAllContacts().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * PUT /api/contacts/{id}
     * Updates a contact.
     */
    public ContactDto updateContact(String contactId, ContactDto dto) {
        if (dto.getFirstName() != null) {
            contactService.updateFirstName(contactId, dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            contactService.updateLastName(contactId, dto.getLastName());
        }
        if (dto.getPhone() != null) {
            contactService.updatePhone(contactId, dto.getPhone());
        }
        if (dto.getAddress() != null) {
            contactService.updateAddress(contactId, dto.getAddress());
        }
        return toDto(contactService.getContact(contactId));
    }

    /**
     * DELETE /api/contacts/{id}
     * Deletes a contact.
     */
    public void deleteContact(String contactId) {
        contactService.deleteContact(contactId);
    }

    private ContactDto toDto(Contact contact) {
        return new ContactDto(
            contact.getContactId(),
            contact.getFirstName(),
            contact.getLastName(),
            contact.getPhone(),
            contact.getAddress()
        );
    }
}
