package com.grandstrand.repository;

import com.grandstrand.domain.Appointment;
import java.util.*;

/**
 * In-memory implementation of AppointmentRepository using HashMap.
 */
public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final Map<String, Appointment> appointments = new HashMap<>();

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        appointments.put(appointment.getAppoId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(String appoId) {
        return Optional.ofNullable(appointments.get(appoId));
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments.values());
    }

    @Override
    public List<Appointment> findByDate(Date date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getAppoDate().equals(date)) {
                result.add(appointment);
            }
        }
        return result;
    }

    @Override
    public void deleteById(String appoId) {
        appointments.remove(appoId);
    }

    @Override
    public boolean existsById(String appoId) {
        return appoId != null && appointments.containsKey(appoId);
    }

    @Override
    public int count() {
        return appointments.size();
    }

    @Override
    public void clear() {
        appointments.clear();
    }
}
