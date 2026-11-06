import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Calendar;

/**
 * unit tests for the Appointment class
 * tests all requirements for appointment objescts
 * 
 * @author Allan Torres
 * @version 1.0
 * @since 2025-08-05
 */
public class AppointmentTest {

    /**
     * helper method to create a future date
     */
    private Date getFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1); // Tomorrow
        return cal.getTime();
    }

    /**
     * helper method to create a past date
     */
    private Date getPastDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1); // Yesterday
        return cal.getTime();
    }

    //test valid appointment creation
    @Test
    public void testValidAppointmentCreation() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Doctor visit", futureDate);
        
        assertEquals("12345", appointment.getAppoId());
        assertEquals("Doctor visit", appointment.getAppoDescription());
        assertEquals(futureDate, appointment.getAppoDate());
    }

    //test appointment ID validation - null ID
    @Test
    public void testAppointmentIdNull() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(null, "Description", futureDate);
        });
    }

    //test appointment ID validation - empty ID
    @Test
    public void testAppointmentIdEmpty() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("", "Description", futureDate);
        });
    }

    //test appointment ID validation - whitespace only ID
    @Test
    public void testAppointmentIdWhitespace() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("   ", "Description", futureDate);
        });
    }

    //test appointment ID validation - too long ID
    @Test
    public void testAppointmentIdTooLong() {
        Date futureDate = getFutureDate();
        String longId = "12345678901"; // 11 characters (exceeds 10 limit)
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(longId, "Description", futureDate);
        });
    }

    //test appointment ID validation - maximum length ID (should pass)
    @Test
    public void testAppointmentIdMaxLength() {
        Date futureDate = getFutureDate();
        String maxId = "1234567890"; // Exactly 10 characters
        assertDoesNotThrow(() -> {
            new Appointment(maxId, "Description", futureDate);
        });
    }

    //test appointment date validation - null date
    @Test
    public void testAppointmentDateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", "Description", null);
        });
    }

    //test appointment date validation - past date
    @Test
    public void testAppointmentDateInPast() {
        Date pastDate = getPastDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", "Description", pastDate);
        });
    }

    //test appointment description validation - null description
    @Test
    public void testAppointmentDescriptionNull() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", null, futureDate);
        });
    }

    //test appointment description validation - empty description
    @Test
    public void testAppointmentDescriptionEmpty() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", "", futureDate);
        });
    }

    //test appointment description validation - whitespace only description
    @Test
    public void testAppointmentDescriptionWhitespace() {
        Date futureDate = getFutureDate();
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", "   ", futureDate);
        });
    }

    //test appointment description validation - too long description
    @Test
    public void testAppointmentDescriptionTooLong() {
        Date futureDate = getFutureDate();
        String longDesc = "This description is way too long and exceeds the fifty character limit that is required";
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345", longDesc, futureDate);
        });
    }

    //test appointment description validation - maximum length description (should pass)
    @Test
    public void testAppointmentDescriptionMaxLength() {
        Date futureDate = getFutureDate();
        String maxDesc = "12345678901234567890123456789012345678901234567890"; // Exactly 50 characters
        assertDoesNotThrow(() -> {
            new Appointment("12345", maxDesc, futureDate);
        });
    }

    //test getter methods
    @Test
    public void testGetterMethods() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("TEST123", "Test appointment", futureDate);
        
        assertEquals("TEST123", appointment.getAppoId());
        assertEquals("Test appointment", appointment.getAppoDescription());
        assertEquals(futureDate, appointment.getAppoDate());
    }

    //test setter methods for updatable fields
    @Test
    public void testSetAppoDate() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Description", futureDate);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2); // Day after tomorrow
        Date newDate = cal.getTime();
        
        appointment.setAppoDate(newDate);
        assertEquals(newDate, appointment.getAppoDate());
    }

    @Test
    public void testSetAppoDescription() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Original description", futureDate);
        
        String newDescription = "Updated description";
        appointment.setAppoDescription(newDescription);
        assertEquals(newDescription, appointment.getAppoDescription());
    }

    //test setter validation - past date
    @Test
    public void testSetAppoPastDate() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Description", futureDate);
        
        Date pastDate = getPastDate();
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setAppoDate(pastDate);
        });
    }

    //test setter validation - invalid description
    @Test
    public void testSetAppoInvalidDescription() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Description", futureDate);
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setAppoDescription(null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.setAppoDescription("");
        });
    }

    //test toString method
    @Test
    public void testToString() {
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("12345", "Test appointment", futureDate);
        
        String result = appointment.toString();
        assertTrue(result.contains("12345"));
        assertTrue(result.contains("Test appointment"));
        assertTrue(result.contains(futureDate.toString()));
    }

    //test that ID is not updatable (no setter method exists)
    @Test
    public void testIdNotUpdatable() {
        //this test verifies that there's no setAppoId method
        //we can't directly test the absence of a method, but we can verify
        //that the ID remains unchanged after object creation
        Date futureDate = getFutureDate();
        Appointment appointment = new Appointment("ORIGINAL", "Description", futureDate);
        
        String originalId = appointment.getAppoId();
        //ID should remain the same - no way to change it
        assertEquals("ORIGINAL", originalId);
        assertEquals("ORIGINAL", appointment.getAppoId());
    }

    /*
     * commented test cases for appointment availability/limit functionality
     * these tests are commented out because customer did not specify
     * appointment limit requirements - uncomment when feature is activated
     */

    /*
    //test appointment limit validation for staff users
    @Test
    public void testAppointmentLimitStaffMessage() {
        Date futureDate = getFutureDate();
        
        //this test would verify that staff gets detailed error message
        //when appointment limit is exceeded
        //would require access to appointment service to set up existing appointments
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        //appointmentService.addAppointment("APPT2", "Second appointment", futureDate);
        //appointmentService.addAppointment("APPT3", "Third appointment", futureDate);
        
        //IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        //    appointment.validateAppointmentLimit(futureDate, 3, true);
        //});
        
        //assertTrue(exception.getMessage().contains("Cannot schedule more than 3 appointments"));
        //assertTrue(exception.getMessage().contains("Current appointments: 3"));
    }
    */

    /*
    //test appointment limit validation for customer users
    @Test
    public void testAppointmentLimitCustomerMessage() {
        Date futureDate = getFutureDate();
        
        //this test would verify that customers get simple error message
        //when appointment limit is exceeded
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        //appointmentService.addAppointment("APPT2", "Second appointment", futureDate);
        
        //IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        //    appointment.validateAppointmentLimit(futureDate, 2, false);
        //});
        
        //assertEquals("This time slot is not available. Please select a different time.", 
        //    exception.getMessage());
    }
    */

    /*
    //test appointment limit allows appointments within limit
    @Test
    public void testAppointmentLimitWithinLimit() {
        Date futureDate = getFutureDate();
        
        //this test would verify that appointments are allowed when under the limit
        
        //example test implementation (commented out):
        //appointmentService.addAppointment("APPT1", "First appointment", futureDate);
        
        //assertDoesNotThrow(() -> {
        //    appointment.validateAppointmentLimit(futureDate, 5, true);
        //});
        
        //assertDoesNotThrow(() -> {
        //    appointment.validateAppointmentLimit(futureDate, 5, false);
        //});
    }
    */
}
