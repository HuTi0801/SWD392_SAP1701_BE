package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.response_models.NotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class NotificationMapper {
    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .content(notification.getContent())
                .notificationStatus(notification.getNotificationStatus())
                .projectId(notification.getProject() != null ? notification.getProject().getId() : null)
                .appointmentId(notification.getAppointment() != null ? notification.getAppointment().getId() : null)
                .groupId(notification.getGroup() != null ? notification.getGroup().getId() : null)
                .accountId(notification.getAccount() != null ? notification.getAccount().getId() : null)
                .reportId(notification.getReport() != null ? notification.getReport().getId() : null)
                .build();
    }

    public List<NotificationResponse> toListResponse(List<Notification> notifications) {
        return notifications.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
