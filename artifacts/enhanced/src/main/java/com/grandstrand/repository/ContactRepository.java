package com.grandstrand.repository;

import com.grandstrand.domain.Contact;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Contact persistence.
 * Part of the Repository pattern — abstracts storage so the service layer
 * does not depend on a concrete implementation.
 */
public interface ContactRepository {
    Contact save(Contact contact);
    Optional<Contact> findById(String contactId);
    List<Contact> findAll();
    void deleteById(String contactId);
    boolean existsById(String contactId);
    int count();
    void clear();
}
