package com.example.sap1701_team1.fptmentorlink.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
    private Integer id;
    private Date date;
    private String description;
    private String rejectionReason;
    private String appointmentStatus;
    private String mentorName;
    private String studentName;
    private List<NotificationResponse> recentNotifications;
}
