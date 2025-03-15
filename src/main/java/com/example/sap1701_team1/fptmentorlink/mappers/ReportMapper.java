package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.response_models.NotificationResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ReportResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReportMapper {

    public ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .submittedTime(report.getSubmittedTime())
                .feedback(report.getFeedback())
                .feedbackTime(report.getFeedbackTime())
                .reportStatus(report.getReportStatus())
                .accountId(report.getAccount() != null ? report.getAccount().getId() : null)
                .accountName(report.getAccount() != null ? report.getAccount().getFullname() : null)
                .groupId(report.getGroup() != null ? report.getGroup().getId() : null)
                .groupName(report.getGroup() != null ? report.getGroup().getName() : null)
                .projectId(report.getProject() != null ? report.getProject().getId() : null)
                .projectTopic(report.getProject() != null ? report.getProject().getTopic() : null)
                .recentNotifications(
                        report.getNotificationList() != null ? report.getNotificationList().stream()
                                .sorted(Comparator.comparing(Notification::getId).reversed()) // 🔄 Sắp xếp theo ID giảm dần
                                .limit(5) // Lấy 5 thông báo gần nhất
                                .map(this::toNotificationResponse) // 🛠️ Chuyển đổi sang NotificationResponse
                                .collect(Collectors.toList())
                                : List.of()
                )
                .build();
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .content(notification.getContent())
                .notificationStatus(notification.getNotificationStatus())
//                .projectId(notification.getProject() != null ? notification.getProject().getId() : null)
//                .groupId(notification.getGroup() != null ? notification.getGroup().getId() : null)
                .accountId(notification.getAccount() != null ? notification.getAccount().getId() : null)
                .build();
    }

    public List<ReportResponse> toListReportResponse(List<Report> reports) {
        return reports.stream().map(this::toReportResponse).collect(Collectors.toList());
    }
}
