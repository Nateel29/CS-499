package com.grandstrand.domain;

/**
 * Contact domain entity with validation.
 * Enhanced from original CS 320 version with proper package structure.
 */
public class Contact {
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > 10 || contactId.isEmpty()) {
            throw new IllegalArgumentException("Contact ID cannot be null, empty, or longer than 10 characters");
        }
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null or longer than 10 characters");
        }
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null or longer than 10 characters");
        }
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits and not null");
        }
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null or longer than 30 characters");
        }
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null or longer than 10 characters");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null or longer than 10 characters");
        }
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits and not null");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null or longer than 30 characters");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return "Contact{contactId='" + contactId + "', firstName='" + firstName + 
               "', lastName='" + lastName + "', phone='" + phone + "', address='" + address + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return contactId.equals(contact.contactId);
    }

    @Override
    public int hashCode() {
        return contactId.hashCode();
    }
}
