import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Unit tests for the AppointmentService class
 * Tests all requirements for appointment service operations
 * 
 * @author Allan Torres
 * @version 1.0
 * @since 2025-08-05
 */
public class AppointmentServiceTest {

    private AppointmentService appointmentService;

    /**
     * Set up a fresh AppointmentService before each test
     */
    @BeforeEach
    public void setUp() {
        appointmentService = new AppointmentService();
    }

    /**
     * Helper method to create a future date
     */
    private Date getFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1); // Tomorrow
        return cal.getTime();
    }

    /**
     * Helper method to create another future date
     */
    private Date getAnotherFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2); // Day after tomorrow
        return cal.getTime();
    }

    //test adding a valid appointment
    @Test
    public void testAddAppointment() {
        Date futureDate = getFutureDate();
        Appointment appointment = appointmentService.addAppointment("12345", "Doctor visit", futureDate);
        
        assertNotNull(appointment);
        assertEquals("12345", appointment.getAppoId());
        assertEquals("Doctor visit", appointment.getAppoDescription());
        assertEquals(futureDate, appointment.getAppoDate());
    }

    //test adding appointment with unique ID requirement
    @Test
    public void testAddAppointmentUniqueId() {
        Date futureDate = getFutureDate();
        
        //add first appointment
        appointmentService.addAppointment("12345", "First appointment", futureDate);
        
        //try to add second appointment with same ID - should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("12345", "Second appointment", futureDate);
        });
    }

    //test adding multiple appointments with different IDs
    @Test
    public void testAddMultipleAppointments() {
        Date futureDate = getFutureDate();
        
        Appointment appt1 = appointmentService.addAppointment("12345", "First appointment", futureDate);
        Appointment appt2 = appointmentService.addAppointment("67890", "Second appointment", futureDate);
        
        assertNotNull(appt1);
        assertNotNull(appt2);
        assertEquals(2, appointmentService.getAppointmentCount());
    }

    //test retrieving an appointment by ID
    @Test
    public void testGetAppointment() {
        Date futureDate = getFutureDate();
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        
        Appointment retrieved = appointmentService.getAppointment("12345");
        assertNotNull(retrieved);
        assertEquals("12345", retrieved.getAppoId());
        assertEquals("Test appointment", retrieved.getAppoDescription());
    }

    //test retrieving non-existent appointment
    @Test
    public void testGetNonExistentAppointment() {
        Appointment retrieved = appointmentService.getAppointment("NONEXISTENT");
        assertNull(retrieved);
    }

    //test deleting an appointment by ID
    @Test
    public void testDeleteAppointment() {
        Date futureDate = getFutureDate();
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        
        //verify appointment exists
        assertTrue(appointmentService.appointmentExists("12345"));
        
        //delete appointment
        boolean deleted = appointmentService.deleteAppointment("12345");
        assertTrue(deleted);
        
        //verify appointment no longer exists
        assertFalse(appointmentService.appointmentExists("12345"));
        assertNull(appointmentService.getAppointment("12345"));
    }

    //test deleting non-existent appointment
    @Test
    public void testDeleteNonExistentAppointment() {
        boolean deleted = appointmentService.deleteAppointment("NONEXISTENT");
        assertFalse(deleted);
    }

    //test appointment exists functionality
    @Test
    public void testAppointmentExists() {
        Date futureDate = getFutureDate();
        
        //initially should not exist
        assertFalse(appointmentService.appointmentExists("12345"));
        
        // Add appointment
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        
        //now should exist
        assertTrue(appointmentService.appointmentExists("12345"));
    }

    // Test updating appointment date
    @Test
    public void testUpdateAppointmentDate() {
        Date futureDate = getFutureDate();
        Date newDate = getAnotherFutureDate();
        
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        
        boolean updated = appointmentService.updateAppointmentDate("12345", newDate);
        assertTrue(updated);
        
        Appointment appointment = appointmentService.getAppointment("12345");
        assertEquals(newDate, appointment.getAppoDate());
    }

    //test updating appointment date for non-existent appointment
    @Test
    public void testUpdateNonExistentAppointmentDate() {
        Date newDate = getFutureDate();
        boolean updated = appointmentService.updateAppointmentDate("NONEXISTENT", newDate);
        assertFalse(updated);
    }

    // Test updating appointment description
    @Test
    public void testUpdateAppointmentDescription() {
        Date futureDate = getFutureDate();
        appointmentService.addAppointment("12345", "Original description", futureDate);
        
        boolean updated = appointmentService.updateAppointmentDescription("12345", "Updated description");
        assertTrue(updated);
        
        Appointment appointment = appointmentService.getAppointment("12345");
        assertEquals("Updated description", appointment.getAppoDescription());
    }

    // Test updating appointment description for non-existent appointment
    @Test
    public void testUpdateNonExistentAppointmentDescription() {
        boolean updated = appointmentService.updateAppointmentDescription("NONEXISTENT", "New description");
        assertFalse(updated);
    }

    // Test getting all appointments
    @Test
    public void testGetAllAppointments() {
        Date futureDate = getFutureDate();
        
        appointmentService.addAppointment("12345", "First appointment", futureDate);
        appointmentService.addAppointment("67890", "Second appointment", futureDate);
        
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        assertEquals(2, allAppointments.size());
    }

    // Test getting appointment count
    @Test
    public void testGetAppointmentCount() {
        assertEquals(0, appointmentService.getAppointmentCount());
        
        Date futureDate = getFutureDate();
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        assertEquals(1, appointmentService.getAppointmentCount());
        
        appointmentService.addAppointment("67890", "Another appointment", futureDate);
        assertEquals(2, appointmentService.getAppointmentCount());
        
        appointmentService.deleteAppointment("12345");
        assertEquals(1, appointmentService.getAppointmentCount());
    }

    // Test getting appointments by date
    @Test
    public void testGetAppointmentsByDate() {
        Date date1 = getFutureDate();
        Date date2 = getAnotherFutureDate();
        
        appointmentService.addAppointment("12345", "Appointment on date1", date1);
        appointmentService.addAppointment("67890", "Another appointment on date1", date1);
        appointmentService.addAppointment("ABCDE", "Appointment on date2", date2);
        
        List<Appointment> appointmentsOnDate1 = appointmentService.getAppointmentsByDate(date1);
        assertEquals(2, appointmentsOnDate1.size());
        
        List<Appointment> appointmentsOnDate2 = appointmentService.getAppointmentsByDate(date2);
        assertEquals(1, appointmentsOnDate2.size());
    }

    // Test clearing all appointments
    @Test
    public void testClearAllAppointments() {
        Date futureDate = getFutureDate();
        
        appointmentService.addAppointment("12345", "First appointment", futureDate);
        appointmentService.addAppointment("67890", "Second appointment", futureDate);
        
        assertEquals(2, appointmentService.getAppointmentCount());
        
        appointmentService.clearAllAppointments();
        assertEquals(0, appointmentService.getAppointmentCount());
        
        assertNull(appointmentService.getAppointment("12345"));
        assertNull(appointmentService.getAppointment("67890"));
    }

    // Test toString method
    @Test
    public void testToString() {
        Date futureDate = getFutureDate();
        appointmentService.addAppointment("12345", "Test appointment", futureDate);
        
        String result = appointmentService.toString();
        assertTrue(result.contains("AppointmentService"));
        assertTrue(result.contains("1 appointments"));
        assertTrue(result.contains("12345"));
    }

    // Test service handles invalid appointment parameters
    @Test
    public void testAddAppointmentWithInvalidParameters() {
        Date futureDate = getFutureDate();
        
        // Test null ID
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment(null, "Description", futureDate);
        });
        
        // Test null description
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("12345", null, futureDate);
        });
        
        // Test null date
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("12345", "Description", null);
        });
    }

    // Test that service maintains data integrity after operations
    @Test
    public void testDataIntegrityAfterOperations() {
        Date futureDate = getFutureDate();
        
        // Add appointments
        appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        appointmentService.addAppointment("APPT2", "Second appointment", futureDate);
        appointmentService.addAppointment("APPT3", "Third appointment", futureDate);
        
        assertEquals(3, appointmentService.getAppointmentCount());
        
        // Delete one
        appointmentService.deleteAppointment("APPT2");
        assertEquals(2, appointmentService.getAppointmentCount());
        
        // Verify remaining appointments are intact
        assertNotNull(appointmentService.getAppointment("APPT1"));
        assertNull(appointmentService.getAppointment("APPT2"));
        assertNotNull(appointmentService.getAppointment("APPT3"));
        
        //update remaining appointments
        appointmentService.updateAppointmentDescription("APPT1", "Updated first appointment");
        assertEquals("Updated first appointment", appointmentService.getAppointment("APPT1").getAppoDescription());
    }

    /*
     * commented test cases for appointment availability/limit functionality
     * these tests are commented out because customer did not specify
     * appointment limit requirements - uncomment when feature is activated
     */

    /*
    //test appointment availability checking for staff users
    @Test
    public void testAppointmentAvailabilityStaffMessage() {
        Date futureDate = getFutureDate();
        
        //this test would verify availability checking with detailed staff messages
        //would require MAX_APPOINTMENTS_PER_SLOT to be defined
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        //appointmentService.addAppointment("APPT2", "Second appointment", futureDate);
        //appointmentService.addAppointment("APPT3", "Third appointment", futureDate);
        
        //IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        //    appointmentService.addAppointmentWithAvailabilityCheck("APPT4", "Fourth appointment", futureDate, true);
        //});
        
        //assertTrue(exception.getMessage().contains("Cannot schedule more than"));
        //assertTrue(exception.getMessage().contains("Current appointments:"));
    }
    */

    /*
    //test appointment availability checking for customer users
    @Test
    public void testAppointmentAvailabilityCustomerMessage() {
        Date futureDate = getFutureDate();
        
        //this test would verify availability checking with simple customer messages
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        //appointmentService.addAppointment("APPT2", "Second appointment", futureDate);
        
        //IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        //    appointmentService.addAppointmentWithAvailabilityCheck("APPT3", "Third appointment", futureDate, false);
        //});
        
        //assertEquals("This time slot is not available. Please select a different time.", 
        //    exception.getMessage());
    }
    */

    /*
    //test time slot availability checking
    @Test
    public void testTimeSlotAvailability() {
        Date futureDate = getFutureDate();
        
        //this test would verify time slot availability checking
        
        //example test implementation (commented out):
        //assertTrue(appointmentService.isTimeSlotAvailable(futureDate)); //initially available
        
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        //assertTrue(appointmentService.isTimeSlotAvailable(futureDate)); //still available under limit
        
        //add appointments up to the limit
        //for (int i = 2; i <= MAX_APPOINTMENTS_PER_SLOT; i++) {
        //    appointmentService.addAppointment("APPT" + i, "Appointment " + i, futureDate);
        //}
        
        //assertFalse(appointmentService.isTimeSlotAvailable(futureDate)); //now at capacity
    }
    */

    /*
    //test availability checking allows appointments within limit
    @Test
    public void testAvailabilityWithinLimit() {
        Date futureDate = getFutureDate();
        
        //this test would verify that appointments are allowed when under the limit
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        
        //assertDoesNotThrow(() -> {
        //    appointmentService.addAppointmentWithAvailabilityCheck("APPT2", "Second appointment", futureDate, true);
        //});
        
        //assertDoesNotThrow(() -> {
        //    appointmentService.addAppointmentWithAvailabilityCheck("APPT3", "Third appointment", futureDate, false);
        //});
        
        //assertEquals(3, appointmentService.getAppointmentCount());
    }
    */
}
