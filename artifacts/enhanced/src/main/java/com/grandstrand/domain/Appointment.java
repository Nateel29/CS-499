package com.grandstrand.domain;

import java.util.Date;

/**
 * Appointment domain entity with validation.
 * Enhanced from original CS 320 version with proper package structure.
 */
public class Appointment {
    public static final int ID_MAX_LENGTH = 10;
    public static final int DESC_MAX_LENGTH = 50;

    private final String appoId;
    private Date appoDate;
    private String appoDescription;

    public Appointment(String appoId, String appoDescription, Date appoDate) {
        validateAppointmentId(appoId);
        this.appoId = appoId;
        validateAppointmentDate(appoDate);
        this.appoDate = appoDate;
        validateAppointmentDescription(appoDescription);
        this.appoDescription = appoDescription;
    }

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

    private void validateAppointmentDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (date.before(new Date())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }

    private void validateAppointmentDescription(String appoDescription) {
        if (appoDescription == null || appoDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null nor empty");
        }
        if (appoDescription.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Description cannot be longer than %d characters", DESC_MAX_LENGTH));
        }
    }

    public String getAppoId() { return appoId; }
    public Date getAppoDate() { return appoDate; }
    public String getAppoDescription() { return appoDescription; }

    public void setAppoDate(Date appoDate) {
        validateAppointmentDate(appoDate);
        this.appoDate = appoDate;
    }

    public void setAppoDescription(String appoDescription) {
        validateAppointmentDescription(appoDescription);
        this.appoDescription = appoDescription;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + appoId + ", date=" + appoDate + ", description=" + appoDescription + "]";
    }
}
