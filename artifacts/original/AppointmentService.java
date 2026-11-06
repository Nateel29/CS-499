import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * appointment service manages a collection of appointments and handles
 * business logic including duplication checking and appointment operations.
 * 
 * @author Allan Torres
 * @version 1.0
 * @since 2025-08-05
 */
public class AppointmentService {
    
    //store appointments using ID as key for fast lookup
    private Map<String, Appointment> appointments;
    
    //commented constant for appointment availability limit
    //uncomment when customer specifies maximum appointments per time slot
    //public static final int MAX_APPOINTMENTS_PER_SLOT = TBD; //to be determined by customer
    
    /**
     * constructor initializes the appointment collection
     */
    public AppointmentService() {
        this.appointments = new HashMap<>();
    }
    
    /**
     * Adds a new appointment to the service
     * 
     * @param appoId The unique identifier for the appointment
     * @param appoDescription The description of the appointment
     * @param appoDate The date of the appointment
     * @return The created Appointment object
     * @throws IllegalArgumentException if appointment ID already exists or parameters are invalid
     */
    public Appointment addAppointment(String appoId, String appoDescription, Date appoDate) {
        //check for duplicate ID first
        if (appointments.containsKey(appoId)) {
            throw new IllegalArgumentException("Appointment with ID '" + appoId + "' already exists");
        }
        
        //create new appointment (this will validate all parameters)
        Appointment newAppointment = new Appointment(appoId, appoDescription, appoDate);
        
        //add to collection
        appointments.put(appoId, newAppointment);
        
        return newAppointment;
    }
    
    /**
     * Retrieves an appointment by ID
     * 
     * @param appoId The ID of the appointment to retrieve
     * @return The Appointment object, or null if not found
     */
    public Appointment getAppointment(String appoId) {
        return appointments.get(appoId);
    }
    
    /**
     * Updates the date of an existing appointment
     * 
     * @param appoId The ID of the appointment to update
     * @param newDate The new date for the appointment
     * @return true if update was successful, false if appointment not found
     * @throws IllegalArgumentException if the new date is invalid
     */
    public boolean updateAppointmentDate(String appoId, Date newDate) {
        Appointment appointment = appointments.get(appoId);
        if (appointment == null) {
            return false;
        }
        
        appointment.setAppoDate(newDate);
        return true;
    }
    
    /**
     * Updates the description of an existing appointment
     * 
     * @param appoId The ID of the appointment to update
     * @param newDescription The new description for the appointment
     * @return true if update was successful, false if appointment not found
     * @throws IllegalArgumentException if the new description is invalid
     */
    public boolean updateAppointmentDescription(String appoId, String newDescription) {
        Appointment appointment = appointments.get(appoId);
        if (appointment == null) {
            return false;
        }
        
        appointment.setAppoDescription(newDescription);
        return true;
    }
    
    /**
     * Deletes an appointment by ID
     * 
     * @param appoId The ID of the appointment to delete
     * @return true if deletion was successful, false if appointment not found
     */
    public boolean deleteAppointment(String appoId) {
        return appointments.remove(appoId) != null;
    }
    
    /**
     * Checks if an appointment with the given ID exists
     * 
     * @param appoId The ID to check
     * @return true if appointment exists, false otherwise
     */
    public boolean appointmentExists(String appoId) {
        return appointments.containsKey(appoId);
    }
    
    /**
     * Gets all appointments as a list
     * 
     * @return List of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }
    
    /**
     * Gets the total number of appointments
     * 
     * @return The count of appointments
     */
    public int getAppointmentCount() {
        return appointments.size();
    }
    
    /**
     * Finds appointments by date
     * 
     * @param date The date to search for
     * @return List of appointments on the specified date
     */
    public List<Appointment> getAppointmentsByDate(Date date) {
        List<Appointment> result = new ArrayList<>();
        
        for (Appointment appointment : appointments.values()) {
            //compare dates (ignoring time if needed)
            if (appointment.getAppoDate().equals(date)) {
                result.add(appointment);
            }
        }
        
        return result;
    }
    
    /**
     * Clears all appointments from the service
     */
    public void clearAllAppointments() {
        appointments.clear();
    }
    
    /**
     * Returns a string representation of all appointments
     * 
     * @return String representation of the appointment service
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AppointmentService [").append(appointments.size()).append(" appointments]:\n");
        
        for (Appointment appointment : appointments.values()) {
            sb.append("  ").append(appointment.toString()).append("\n");
        }
        
        return sb.toString();
    }
    
    /*
     * commented methods for appointment availability/limit functionality
     * these methods are commented out because customer did not specify
     * appointment limit requirements - uncomment when feature is activated
     */
    
    /*
    //check if appointment can be scheduled based on availability limit
    private boolean checkAppointmentAvailability(Date appoDate, boolean isStaffUser) {
        //count existing appointments for the same date/time
        int existingAppointments = getAppointmentsByDate(appoDate).size();
        
        //check against maximum limit (would use MAX_APPOINTMENTS_PER_SLOT constant)
        //if (existingAppointments >= MAX_APPOINTMENTS_PER_SLOT) {
        //    if (isStaffUser) {
        //        //detailed message for staff
        //        throw new IllegalArgumentException(
        //            String.format("Cannot schedule more than %d appointments at the same time. Current appointments: %d", 
        //                MAX_APPOINTMENTS_PER_SLOT, existingAppointments)
        //        );
        //    } else {
        //        //simple message for customers
        //        throw new IllegalArgumentException("This time slot is not available. Please select a different time.");
        //    }
        //}
        //return true;
        
        //placeholder return for now
        return true;
    }
    */
    
    /*
    //enhanced add appointment method with availability checking
    public Appointment addAppointmentWithAvailabilityCheck(String appoId, String appoDescription, Date appoDate, boolean isStaffUser) {
        //check for duplicate ID first
        if (appointments.containsKey(appoId)) {
            throw new IllegalArgumentException("Appointment with ID '" + appoId + "' already exists");
        }
        
        //check appointment availability before creating
        //checkAppointmentAvailability(appoDate, isStaffUser);
        
        //create new appointment (this will validate all parameters)
        Appointment newAppointment = new Appointment(appoId, appoDescription, appoDate);
        
        //add to collection
        appointments.put(appoId, newAppointment);
        
        return newAppointment;
    }
    */
    
    /*
    //get available time slots for a given date
    public boolean isTimeSlotAvailable(Date appoDate) {
        //count existing appointments for the date
        int existingAppointments = getAppointmentsByDate(appoDate).size();
        
        //return true if under the limit
        //return existingAppointments < MAX_APPOINTMENTS_PER_SLOT;
        
        //placeholder return for now
        return true;
    }
    */
}
