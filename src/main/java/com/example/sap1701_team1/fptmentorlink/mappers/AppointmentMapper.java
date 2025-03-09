package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Appointment;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AppointmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AppointmentMapper {
    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .description(appointment.getDescription())
                .appointmentStatus(appointment.getAppointmentStatus().name())
                .mentorName(appointment.getMentor() != null ? appointment.getMentor().getAccount().getFullname(): "No mentor")
                .studentName(appointment.getStudent() != null ? appointment.getStudent().getAccount().getFullname() : "No student")
                .build();
    }

    public List<AppointmentResponse> toListAppointmentResponse(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }
}
