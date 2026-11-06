package com.grandstrand.repository;

import com.grandstrand.domain.Appointment;
import java.util.*;

/**
 * Enhanced AppointmentRepository with TreeMap date index for O(log n) range queries.
 * Replaces the O(n) linear scan of the original in-memory implementation.
 */
public class IndexedAppointmentRepository implements AppointmentRepository {
    private final Map<String, Appointment> appointmentsById;
    private final TreeMap<Date, List<Appointment>> appointmentsByDate;

    public IndexedAppointmentRepository() {
        this.appointmentsById = new HashMap<>();
        this.appointmentsByDate = new TreeMap<>();
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        
        // Remove from old date index if updating
        Appointment existing = appointmentsById.get(appointment.getAppoId());
        if (existing != null) {
            removeFromDateIndex(existing);
        }
        
        appointmentsById.put(appointment.getAppoId(), appointment);
        addToDateIndex(appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(String appoId) {
        return Optional.ofNullable(appointmentsById.get(appoId));
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointmentsById.values());
    }

    @Override
    public List<Appointment> findByDate(Date date) {
        List<Appointment> result = appointmentsByDate.get(date);
        return result != null ? new ArrayList<>(result) : new ArrayList<>();
    }

    /**
     * Finds appointments within a date range using TreeMap subMap.
     * Time complexity: O(log n + k) where k = number of results.
     * Original linear scan was O(n).
     */
    public List<Appointment> findByDateRange(Date startDate, Date endDate) {
        List<Appointment> result = new ArrayList<>();
        Map<Date, List<Appointment>> subMap = appointmentsByDate.subMap(startDate, true, endDate, true);
        for (List<Appointment> appointments : subMap.values()) {
            result.addAll(appointments);
        }
        return result;
    }

    @Override
    public void deleteById(String appoId) {
        Appointment appointment = appointmentsById.remove(appoId);
        if (appointment != null) {
            removeFromDateIndex(appointment);
        }
    }

    @Override
    public boolean existsById(String appoId) {
        return appoId != null && appointmentsById.containsKey(appoId);
    }

    @Override
    public int count() {
        return appointmentsById.size();
    }

    @Override
    public void clear() {
        appointmentsById.clear();
        appointmentsByDate.clear();
    }

    private void addToDateIndex(Appointment appointment) {
        appointmentsByDate.computeIfAbsent(appointment.getAppoDate(), k -> new ArrayList<>()).add(appointment);
    }

    private void removeFromDateIndex(Appointment appointment) {
        List<Appointment> dateList = appointmentsByDate.get(appointment.getAppoDate());
        if (dateList != null) {
            dateList.remove(appointment);
            if (dateList.isEmpty()) {
                appointmentsByDate.remove(appointment.getAppoDate());
            }
        }
    }
}
