package com.grandstrand.repository;

import com.grandstrand.domain.Appointment;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Appointment persistence.
 */
public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(String appoId);
    List<Appointment> findAll();
    List<Appointment> findByDate(Date date);
    void deleteById(String appoId);
    boolean existsById(String appoId);
    int count();
    void clear();
}
