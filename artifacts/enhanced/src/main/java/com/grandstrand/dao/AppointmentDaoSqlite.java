package com.grandstrand.dao;

import com.grandstrand.domain.Appointment;
import com.grandstrand.repository.AppointmentRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of AppointmentRepository using JDBC.
 * Dates are stored as epoch milliseconds (INTEGER) for correct ordering.
 * All queries use PreparedStatements to prevent SQL injection.
 */
public class AppointmentDaoSqlite implements AppointmentRepository {
    private final DatabaseManager dbManager;

    public AppointmentDaoSqlite(DatabaseManager dbManager) {
        if (dbManager == null) {
            throw new IllegalArgumentException("DatabaseManager cannot be null");
        }
        this.dbManager = dbManager;
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        String sql = "INSERT INTO appointments (appo_id, appo_date, description) VALUES (?, ?, ?) " +
                     "ON CONFLICT(appo_id) DO UPDATE SET " +
                     "appo_date = excluded.appo_date, description = excluded.description";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getAppoId());
            stmt.setLong(2, appointment.getAppoDate().getTime());
            stmt.setString(3, appointment.getAppoDescription());
            stmt.executeUpdate();
            return appointment;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save appointment " + appointment.getAppoId(), e);
        }
    }

    @Override
    public Optional<Appointment> findById(String appoId) {
        String sql = "SELECT appo_id, appo_date, description FROM appointments WHERE appo_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find appointment " + appoId, e);
        }
    }

    @Override
    public List<Appointment> findAll() {
        String sql = "SELECT appo_id, appo_date, description FROM appointments";
        List<Appointment> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve appointments", e);
        }
    }

    @Override
    public List<Appointment> findByDate(Date date) {
        String sql = "SELECT appo_id, appo_date, description FROM appointments WHERE appo_date = ?";
        List<Appointment> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, date.getTime());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find appointments by date", e);
        }
    }

    /**
     * Finds appointments within a date range using the idx_appointments_date index.
     * Replaces the Milestone Three in-memory TreeMap index with a database index.
     */
    public List<Appointment> findByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT appo_id, appo_date, description FROM appointments " +
                     "WHERE appo_date BETWEEN ? AND ? ORDER BY appo_date";
        List<Appointment> results = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, startDate.getTime());
            stmt.setLong(2, endDate.getTime());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find appointments by date range", e);
        }
    }

    @Override
    public void deleteById(String appoId) {
        String sql = "DELETE FROM appointments WHERE appo_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete appointment " + appoId, e);
        }
    }

    @Override
    public boolean existsById(String appoId) {
        if (appoId == null) {
            return false;
        }
        String sql = "SELECT 1 FROM appointments WHERE appo_id = ? LIMIT 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appoId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to check appointment existence " + appoId, e);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM appointments";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to count appointments", e);
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM appointments";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to clear appointments", e);
        }
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        return new Appointment(
            rs.getString("appo_id"),
            rs.getString("description"),
            new Date(rs.getLong("appo_date"))
        );
    }
}
