package com.grandstrand.dto;

import java.util.Date;

/**
 * Data Transfer Object for Appointment API responses.
 */
public class AppointmentDto {
    private String appoId;
    private String appoDescription;
    private Date appoDate;

    public AppointmentDto() {}

    public AppointmentDto(String appoId, String appoDescription, Date appoDate) {
        this.appoId = appoId;
        this.appoDescription = appoDescription;
        this.appoDate = appoDate;
    }

    public String getAppoId() { return appoId; }
    public void setAppoId(String appoId) { this.appoId = appoId; }
    public String getAppoDescription() { return appoDescription; }
    public void setAppoDescription(String appoDescription) { this.appoDescription = appoDescription; }
    public Date getAppoDate() { return appoDate; }
    public void setAppoDate(Date appoDate) { this.appoDate = appoDate; }
}
