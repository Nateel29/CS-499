package com.grandstrand.dto;

/**
 * Data Transfer Object for Contact API responses.
 * Decouples the internal domain model from the external API contract.
 */
public class ContactDto {
    private String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public ContactDto() {}

    public ContactDto(String contactId, String firstName, String lastName, String phone, String address) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getContactId() { return contactId; }
    public void setContactId(String contactId) { this.contactId = contactId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
