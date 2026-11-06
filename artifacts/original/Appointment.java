import java.util.Date;

/**
 * appointment represents a task objesct with unique id, date and description.
 * 
 * -id: no null, no longer than 10 characters, not updatable. [string]
 * -date: cannot be in the past, should be required, no null, use java.util.date and use before(new date()) to validate past dates
 * -Description: no null, no longer than 50 characters, updatable. [string]
 *
 * //customer did not specified how many appointments can be taken at the same time
 * //depending on how many can be attend at each time, the system could verify for that.
 * //I will create a method to verify that, but will comment it to deactivate it, unless customer says otherwise
 * 
 * @author Allan Torres
 * @version 1.0
 * @since 2025-08-05
 */

public class Appointment {
    //constants for validation
    public static final int ID_MAX_LENGTH = 10; //max length for id
    public static final int DESC_MAX_LENGTH = 50; //max length for description
    
    //instance variables
    private String appoId;
    private Date appoDate;
    private String appoDescription;

    /**
     * @param appoId The unique identifier for the appoinment (max 10 characters, no null)
     * @param appoDescription The description of the appointment (max 50 characters, no null)
     * @param appoDate The Date of the appointment (future only, no null)
     * @throws IllegalArgumentException if any of the parameters are invalid
    */

    public Appointment(String appoId, String appoDescription, Date appoDate) {
        //Validate and set instance variables
        validateAppointmentId(appoId);
        this.appoId = appoId;

        validateAppointmentDate(appoDate);
        this.appoDate = appoDate;

        validateAppointmentDescription(appoDescription);
        this.appoDescription = appoDescription;
    }

    /*
     * method to verify maximum appointments allowed at the same time
     * this method is commented out because customer did not specify
     * how many appointments can be scheduled simultaneously
     * uncomment and implement if customer provides this requirement
     * 
     * @param appoDate the date to check for appointment conflicts
     * @param maxAppointments maximum number of appointments allowed at same time
     * @return true if appointment can be scheduled, false if limit exceeded
     * @throws IllegalArgumentException if appointment limit is exceeded
     */
    /*
    private boolean validateAppointmentLimit(Date appoDate, int maxAppointments, boolean isStaffUser) {
        //this would require access to appointment service or collection
        //to count existing appointments at the same date/time
        
        //example implementation (commented out):
        //int existingAppointments = appointmentService.getAppointmentsByDate(appoDate).size();
        //if (existingAppointments >= maxAppointments) {
        //    if (isStaffUser) {
        //        //message for secretary/staff with detailed information
        //        throw new IllegalArgumentException(
        //            String.format("Cannot schedule more than %d appointments at the same time. Current appointments: %d", 
        //                maxAppointments, existingAppointments)
        //        );
        //    } else {
        //        //simple message for customer - time not available
        //        throw new IllegalArgumentException("This time slot is not available. Please select a different time.");
        //    }
        //}
        //return true;
        
        //placeholder return for now
        return true;
    }
    */

    /**
     * Validates the ID according to requirements.
     * 
     * @param appoId The ID to validate
     * @throws IllegalArgumentException if the ID is invalid
     */
    private void validateAppointmentId(String appoId) {
        if (appoId == null || appoId.length() > ID_MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Id cannot be null nor longer than %d digits", ID_MAX_LENGTH)
            );
        }
        if (appoId.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
    }

    /**
     * Validate the date of the appointment
     * 
     * @param date The date to validate
     * @throws IllegalArgumentException if the date is invalid
     */
    private void validateAppointmentDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        if (date.before(new Date())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }

    /**
     * Validate the description of the appointment
     * 
     * @param appoDescription The description to validate
     * @throws IllegalArgumentException if description is invalid
     */
    private void validateAppointmentDescription(String appoDescription) {
        if (appoDescription == null || appoDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null nor empty");
        }
        if (appoDescription.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Description cannot be longer than %d characters", DESC_MAX_LENGTH));
        }
    }

    /**
     * Gets the id of the appointment (not updatable)
     * 
     * @return The id of the appointment
     */
    public String getAppoId() {
        return appoId;
    }

    /**
     * Gets the date of the appointment (updatable)
     * 
     * @return The date of the appointment
     */
    public Date getAppoDate() {
        return appoDate;
    }

    /**
     * Gets the description of the appointment (updatable)
     * 
     * @return The description of the appointment
     */
    public String getAppoDescription() {
        return appoDescription;
    }

    /**
     * Sets the date of the appointment (updatable)
     * 
     * @param appoDate The new date of the appointment
     * @throws IllegalArgumentException if the date is invalid
     */
    public void setAppoDate(Date appoDate) {
        validateAppointmentDate(appoDate);
        this.appoDate = appoDate;
    }

    /**
     * Sets the description of the appointment (updatable)
     * 
     * @param appoDescription The new description of the appointment
     * @throws IllegalArgumentException if the description is invalid
     */
    public void setAppoDescription(String appoDescription) {
        validateAppointmentDescription(appoDescription);
        this.appoDescription = appoDescription;
    }

    /**
     * Returns a string representation of the appointment
     * 
     * @return A string representation of the appointment
     */
    @Override
    public String toString() {
        return "Appointment [id=" + appoId + ", date=" + appoDate + ", description=" + appoDescription + "]";
    }
}
