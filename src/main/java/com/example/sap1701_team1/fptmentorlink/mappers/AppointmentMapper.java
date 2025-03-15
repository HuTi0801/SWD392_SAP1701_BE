package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Appointment;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AppointmentResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
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
                .rejectionReason(appointment.getRejectionReason())
                .appointmentStatus(appointment.getAppointmentStatus().name())
                .mentorName(appointment.getMentor() != null ? appointment.getMentor().getAccount().getFullname() : "No mentor")
                .studentName(appointment.getStudent() != null ? appointment.getStudent().getAccount().getFullname() : "No student")
                .recentNotifications(
                        appointment.getNotificationList().stream()
                                .sorted(Comparator.comparing(Notification::getId).reversed()) // Lấy thông báo mới nhất trước
                                .limit(5) // Chỉ lấy tối đa 5 thông báo gần nhất
                                .map(notification -> new NotificationResponse(
                                        notification.getId(),
                                        notification.getType(),
                                        notification.getContent(),
                                        notification.getNotificationStatus(),
                                        notification.getProject() != null ? notification.getProject().getId() : null,
                                        notification.getGroup() != null ? notification.getGroup().getId() : null,
                                        notification.getAppointment().getId(),
                                        null,
                                        null
                                ))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<AppointmentResponse> toListAppointmentResponse(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }
}
