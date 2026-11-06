import java.util.HashMap;
import java.util.Map;

/**
 * Contact Service - ContactService Class
 * 
 * Student: Allan Torres
 * Course: CS-320-11659-M01
 * Date: July 28, 2025
 * 
 * Developed with assistance from Cascade AI on Windsurf Platform
 * 
 * ContactService class for managing contacts in memory
 * 
 * Requirements:
 * - Add contacts with unique ID
 * - Delete contacts by contact ID
 * - Update contact fields by contact ID (firstName, lastName, phone, address)
 */
public class ContactService {
    
    private final Map<String, Contact> contacts;
    
    /**
     * Constructor initializes the in-memory contact storage
     */
    public ContactService() {
        this.contacts = new HashMap<>();
    }
    
    /**
     * Adds a new contact to the service
     * 
     * @param contact The contact to add
     * @throws IllegalArgumentException if contact is null or ID already exists
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact with ID " + contact.getContactId() + " already exists");
        }
        
        contacts.put(contact.getContactId(), contact);
    }
    
    /**
     * Deletes a contact by contact ID
     * 
     * @param contactId The ID of the contact to delete
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
        
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact with ID " + contactId + " does not exist");
        }
        
        contacts.remove(contactId);
    }
    
    /**
     * Updates the first name of a contact
     * 
     * @param contactId The ID of the contact to update
     * @param firstName The new first name
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getContact(contactId);
        contact.setFirstName(firstName);
    }
    
    /**
     * Updates the last name of a contact
     * 
     * @param contactId The ID of the contact to update
     * @param lastName The new last name
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public void updateLastName(String contactId, String lastName) {
        Contact contact = getContact(contactId);
        contact.setLastName(lastName);
    }
    
    /**
     * Updates the phone number of a contact
     * 
     * @param contactId The ID of the contact to update
     * @param phone The new phone number
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public void updatePhone(String contactId, String phone) {
        Contact contact = getContact(contactId);
        contact.setPhone(phone);
    }
    
    /**
     * Updates the address of a contact
     * 
     * @param contactId The ID of the contact to update
     * @param address The new address
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public void updateAddress(String contactId, String address) {
        Contact contact = getContact(contactId);
        contact.setAddress(address);
    }
    
    /**
     * Retrieves a contact by ID
     * 
     * @param contactId The ID of the contact to retrieve
     * @return The contact with the specified ID
     * @throws IllegalArgumentException if contactId is null or contact doesn't exist
     */
    public Contact getContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
        
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact with ID " + contactId + " does not exist");
        }
        
        return contact;
    }
    
    /**
     * Gets the total number of contacts
     * 
     * @return The number of contacts in the service
     */
    public int getContactCount() {
        return contacts.size();
    }
    
    /**
     * Checks if a contact exists by ID
     * 
     * @param contactId The ID to check
     * @return true if contact exists, false otherwise
     */
    public boolean contactExists(String contactId) {
        return contactId != null && contacts.containsKey(contactId);
    }
    
    /**
     * Clears all contacts from the service (useful for testing)
     */
    public void clearAllContacts() {
        contacts.clear();
    }
}
