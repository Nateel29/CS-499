package com.grandstrand.api;

import com.grandstrand.domain.Appointment;
import com.grandstrand.dto.AppointmentDto;
import com.grandstrand.service.AppointmentService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API controller for Appointment operations.
 */
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        if (appointmentService == null) {
            throw new IllegalArgumentException("AppointmentService cannot be null");
        }
        this.appointmentService = appointmentService;
    }

    public AppointmentDto createAppointment(AppointmentDto dto) {
        Appointment appointment = appointmentService.addAppointment(
            dto.getAppoId(),
            dto.getAppoDescription(),
            dto.getAppoDate()
        );
        return toDto(appointment);
    }

    public AppointmentDto getAppointment(String appoId) {
        Appointment appointment = appointmentService.getAppointment(appoId);
        return toDto(appointment);
    }

    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointments().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDate(Date date) {
        return appointmentService.getAppointmentsByDate(date).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public AppointmentDto updateAppointment(String appoId, AppointmentDto dto) {
        if (dto.getAppoDate() != null) {
            appointmentService.updateAppointmentDate(appoId, dto.getAppoDate());
        }
        if (dto.getAppoDescription() != null) {
            appointmentService.updateAppointmentDescription(appoId, dto.getAppoDescription());
        }
        return toDto(appointmentService.getAppointment(appoId));
    }

    public boolean deleteAppointment(String appoId) {
        return appointmentService.deleteAppointment(appoId);
    }

    private AppointmentDto toDto(Appointment appointment) {
        return new AppointmentDto(
            appointment.getAppoId(),
            appointment.getAppoDescription(),
            appointment.getAppoDate()
        );
    }
}
