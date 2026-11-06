package com.grandstrand;

import com.grandstrand.domain.Appointment;
import com.grandstrand.repository.AppointmentRepository;
import com.grandstrand.repository.InMemoryAppointmentRepository;
import com.grandstrand.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AppointmentService with dependency injection.
 */
class AppointmentServiceTest {
    private AppointmentService appointmentService;
    private AppointmentRepository repository;
    private Date futureDate;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAppointmentRepository();
        appointmentService = new AppointmentService(repository);
        futureDate = new Date(System.currentTimeMillis() + 86400000); // 24 hours in future
    }

    @Test
    void testAddAppointment() {
        Appointment appointment = appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        assertNotNull(appointment);
        assertEquals("A001", appointment.getAppoId());
        assertEquals(1, appointmentService.getAppointmentCount());
    }

    @Test
    void testAddDuplicateAppointment() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("A001", "Dentist", futureDate);
        });
    }

    @Test
    void testGetAppointment() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        Appointment appointment = appointmentService.getAppointment("A001");
        assertEquals("Doctor Visit", appointment.getAppoDescription());
    }

    @Test
    void testUpdateAppointmentDescription() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        appointmentService.updateAppointmentDescription("A001", "Updated Description");
        assertEquals("Updated Description", appointmentService.getAppointment("A001").getAppoDescription());
    }

    @Test
    void testDeleteAppointment() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        assertTrue(appointmentService.deleteAppointment("A001"));
        assertEquals(0, appointmentService.getAppointmentCount());
    }

    @Test
    void testDeleteNonExistentAppointment() {
        assertFalse(appointmentService.deleteAppointment("NONEXISTENT"));
    }

    @Test
    void testAppointmentExists() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        assertTrue(appointmentService.appointmentExists("A001"));
        assertFalse(appointmentService.appointmentExists("NONEXISTENT"));
    }

    @Test
    void testGetAppointmentsByDate() {
        appointmentService.addAppointment("A001", "Doctor Visit", futureDate);
        assertEquals(1, appointmentService.getAppointmentsByDate(futureDate).size());
    }
}
