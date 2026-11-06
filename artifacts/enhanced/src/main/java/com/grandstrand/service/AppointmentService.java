package com.grandstrand.service;

import com.grandstrand.domain.Appointment;
import com.grandstrand.repository.AppointmentRepository;
import com.grandstrand.repository.IndexedAppointmentRepository;
import com.grandstrand.dao.AppointmentDaoSqlite;
import java.util.Date;
import java.util.List;

/**
 * AppointmentService with dependency injection of AppointmentRepository.
 * Enhanced from original CS 320 version to use the Repository pattern.
 * 
 * Milestone Three enhancement: Uses IndexedAppointmentRepository for
 * O(log n) date range queries instead of O(n) linear scans.
 */
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    public Appointment addAppointment(String appoId, String appoDescription, Date appoDate) {
        if (repository.existsById(appoId)) {
            throw new IllegalArgumentException("Appointment with ID '" + appoId + "' already exists");
        }
        Appointment appointment = new Appointment(appoId, appoDescription, appoDate);
        return repository.save(appointment);
    }

    public Appointment getAppointment(String appoId) {
        return repository.findById(appoId)
            .orElseThrow(() -> new IllegalArgumentException("Appointment with ID '" + appoId + "' not found"));
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public List<Appointment> getAppointmentsByDate(Date date) {
        return repository.findByDate(date);
    }

    /**
     * Finds appointments within a date range.
     * Uses TreeMap subMap for O(log n + k) performance when repository supports it.
     */
    public List<Appointment> getAppointmentsByDateRange(Date startDate, Date endDate) {
        if (repository instanceof IndexedAppointmentRepository) {
            return ((IndexedAppointmentRepository) repository).findByDateRange(startDate, endDate);
        }
        if (repository instanceof AppointmentDaoSqlite) {
            return ((AppointmentDaoSqlite) repository).findByDateRange(startDate, endDate);
        }
        // Fallback to linear scan for non-indexed repositories
        List<Appointment> result = new java.util.ArrayList<>();
        for (Appointment a : repository.findAll()) {
            if (!a.getAppoDate().before(startDate) && !a.getAppoDate().after(endDate)) {
                result.add(a);
            }
        }
        return result;
    }

    public boolean deleteAppointment(String appoId) {
        if (!repository.existsById(appoId)) {
            return false;
        }
        repository.deleteById(appoId);
        return true;
    }

    public boolean updateAppointmentDate(String appoId, Date newDate) {
        try {
            Appointment appointment = getAppointment(appoId);
            appointment.setAppoDate(newDate);
            repository.save(appointment);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean updateAppointmentDescription(String appoId, String newDescription) {
        try {
            Appointment appointment = getAppointment(appoId);
            appointment.setAppoDescription(newDescription);
            repository.save(appointment);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean appointmentExists(String appoId) {
        return repository.existsById(appoId);
    }

    public int getAppointmentCount() {
        return repository.count();
    }

    public void clearAllAppointments() {
        repository.clear();
    }
}
