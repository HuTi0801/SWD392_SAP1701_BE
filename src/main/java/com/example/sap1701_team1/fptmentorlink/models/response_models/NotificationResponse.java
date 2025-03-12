package com.example.sap1701_team1.fptmentorlink.models.response_models;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Integer id;
    private String type;
    private String content;
    private NotificationStatus notificationStatus;
    private Integer projectId;
    private Integer groupId;
    private Integer appointmentId;
}
