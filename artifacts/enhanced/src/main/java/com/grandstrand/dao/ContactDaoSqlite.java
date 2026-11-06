package com.grandstrand.dao;

import com.grandstrand.domain.Contact;
import com.grandstrand.repository.ContactRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of ContactRepository using JDBC.
 * All queries use PreparedStatements to prevent SQL injection.
 * Multi-step writes are wrapped in transactions.
 */
public class ContactDaoSqlite implements ContactRepository {
    private final DatabaseManager dbManager;

    public ContactDaoSqlite(DatabaseManager dbManager) {
        if (dbManager == null) {
            throw new IllegalArgumentException("DatabaseManager cannot be null");
        }
        this.dbManager = dbManager;
    }

    @Override
    public Contact save(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        String sql = "INSERT INTO contacts (contact_id, first_name, last_name, phone, address) " +
                     "VALUES (?, ?, ?, ?, ?) " +
                     "ON CONFLICT(contact_id) DO UPDATE SET " +
                     "first_name = excluded.first_name, last_name = excluded.last_name, " +
                     "phone = excluded.phone, address = excluded.address";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contact.getContactId());
            stmt.setString(2, contact.getFirstName());
            stmt.setString(3, contact.getLastName());
            stmt.setString(4, contact.getPhone());
            stmt.setString(5, contact.getAddress());
            stmt.executeUpdate();
            return contact;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save contact " + contact.getContactId(), e);
        }
    }

    @Override
    public Optional<Contact> findById(String contactId) {
        String sql = "SELECT contact_id, first_name, last_name, phone, address FROM contacts WHERE contact_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contactId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find contact " + contactId, e);
        }
    }

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT contact_id, first_name, last_name, phone, address FROM contacts";
        List<Contact> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve contacts", e);
        }
    }

    @Override
    public void deleteById(String contactId) {
        String sql = "DELETE FROM contacts WHERE contact_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contactId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete contact " + contactId, e);
        }
    }

    @Override
    public boolean existsById(String contactId) {
        if (contactId == null) {
            return false;
        }
        String sql = "SELECT 1 FROM contacts WHERE contact_id = ? LIMIT 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contactId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to check contact existence " + contactId, e);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM contacts";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to count contacts", e);
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM contacts";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to clear contacts", e);
        }
    }

    private Contact mapRow(ResultSet rs) throws SQLException {
        return new Contact(
            rs.getString("contact_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("address")
        );
    }
}
