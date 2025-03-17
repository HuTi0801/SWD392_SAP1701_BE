package com.example.sap1701_team1.fptmentorlink.models.response_models;

import com.example.sap1701_team1.fptmentorlink.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportResponse {
    private Integer id;
    private String title;
    private String content;
    private ReportStatus reportStatus;
    private Date submittedTime;
    private String feedback;
    private Date feedbackTime;

    private Integer accountId; //lấy id trong bảng account
    private String accountName;

    private Integer groupId;
    private String groupName;

    private Integer projectId;
    private String projectTopic;

    private List<NotificationResponse> recentNotifications;
}
