/**
 * Contact Service - Contact Class
 * 
 * Student: Allan Torres
 * Course: CS-320-11659-M01
 * Date: July 28, 2025
 * 
 * Developed with assistance from Cascade AI on Windsurf Platform
 * 
 * Contact class representing a contact with required fields and validation
 * 
 * This class models a contact in the mobile application with strict validation rules.
 * All fields have specific constraints that must be met during creation and updates.
 * 
 * Requirements:
 * - Unique contact ID (max 10 chars, not null, not updatable)
 * - firstName (max 10 chars, not null)
 * - lastName (max 10 chars, not null)
 * - phone (exactly 10 digits, not null)
 * - address (max 30 chars, not null)
 */
public class Contact {
    
    // Contact ID is final (immutable) - cannot be changed after object creation
    // This ensures unique identification throughout the contact's lifecycle
    private final String contactId;
    
    // First name of the contact - can be updated but must meet validation rules
    private String firstName;
    
    // Last name of the contact - can be updated but must meet validation rules
    private String lastName;
    
    // Phone number - must be exactly 10 digits, can be updated
    private String phone;
    
    // Address of the contact - can be updated but must meet length requirements
    private String address;
    
    /**
     * Constructor for Contact object
     * 
     * Creates a new contact with all required fields. All parameters are validated
     * against their respective constraints before the object is created.
     * 
     * @param contactId Unique identifier (max 10 chars, not null, not empty)
     * @param firstName First name (max 10 chars, not null)
     * @param lastName Last name (max 10 chars, not null)
     * @param phone Phone number (exactly 10 digits, not null)
     * @param address Address (max 30 chars, not null)
     * @throws IllegalArgumentException if any parameter violates constraints
     */
    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        // Validate contactId - must not be null, empty, or exceed 10 characters
        // This ensures each contact has a valid, unique identifier
        if (contactId == null || contactId.length() > 10 || contactId.isEmpty()) {
            throw new IllegalArgumentException("Contact ID cannot be null, empty, or longer than 10 characters");
        }
        
        // Validate firstName - must not be null and cannot exceed 10 characters
        // Empty string is allowed, but null is not
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null or longer than 10 characters");
        }
        
        // Validate lastName - must not be null and cannot exceed 10 characters
        // Empty string is allowed, but null is not
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null or longer than 10 characters");
        }
        
        // Validate phone - must be exactly 10 digits (no more, no less)
        // Uses regex \\d{10} to ensure only numeric characters
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits and not null");
        }
        
        // Validate address - must not be null and cannot exceed 30 characters
        // This allows for reasonable address lengths while preventing excessive data
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null or longer than 30 characters");
        }
        
        // Assign validated values to instance variables
        // contactId is final, so this is the only place it can be set
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }
    
    // Getter methods - provide read-only access to all contact fields
    // These methods allow other classes to retrieve contact information safely
    
    /**
     * Gets the contact ID
     * @return The unique contact identifier (immutable)
     */
    public String getContactId() {
        return contactId;
    }
    
    /**
     * Gets the first name
     * @return The contact's first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Gets the last name
     * @return The contact's last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Gets the phone number
     * @return The contact's phone number (10 digits)
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Gets the address
     * @return The contact's address
     */
    public String getAddress() {
        return address;
    }
    
    // Setter methods - allow updating of mutable fields with validation
    // Note: contactId is not included as it's immutable (final field)
    // Each setter performs the same validation as the constructor
    
    /**
     * Updates the first name with validation
     * @param firstName New first name (max 10 chars, not null)
     * @throws IllegalArgumentException if firstName violates constraints
     */
    public void setFirstName(String firstName) {
        // Same validation as constructor - ensures data integrity
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null or longer than 10 characters");
        }
        this.firstName = firstName;
    }
    
    /**
     * Updates the last name with validation
     * @param lastName New last name (max 10 chars, not null)
     * @throws IllegalArgumentException if lastName violates constraints
     */
    public void setLastName(String lastName) {
        // Same validation as constructor - ensures data integrity
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null or longer than 10 characters");
        }
        this.lastName = lastName;
    }
    
    /**
     * Updates the phone number with validation
     * @param phone New phone number (exactly 10 digits, not null)
     * @throws IllegalArgumentException if phone violates constraints
     */
    public void setPhone(String phone) {
        // Validates that phone is exactly 10 digits using regex
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits and not null");
        }
        this.phone = phone;
    }
    
    /**
     * Updates the address with validation
     * @param address New address (max 30 chars, not null)
     * @throws IllegalArgumentException if address violates constraints
     */
    public void setAddress(String address) {
        // Same validation as constructor - ensures data integrity
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null or longer than 30 characters");
        }
        this.address = address;
    }
    
    /**
     * Returns a string representation of the contact
     * Useful for debugging and logging purposes
     * @return Formatted string containing all contact fields
     */
    @Override
    public String toString() {
        return "Contact{" +
                "contactId='" + contactId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
    /**
     * Determines equality based on contact ID only
     * Two contacts are considered equal if they have the same contactId,
     * regardless of other field values. This supports the unique ID requirement.
     * @param obj Object to compare with
     * @return true if objects have the same contactId, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Check if comparing with itself (same memory reference)
        if (this == obj) return true;
        
        // Check if object is null or different class
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // Cast to Contact and compare contactId only
        Contact contact = (Contact) obj;
        return contactId.equals(contact.contactId);
    }
    
    /**
     * Generates hash code based on contact ID
     * Consistent with equals() method - objects that are equal
     * must have the same hash code
     * @return Hash code based on contactId
     */
    @Override
    public int hashCode() {
        // Use contactId's hash code since equality is based on contactId
        return contactId.hashCode();
    }
}
