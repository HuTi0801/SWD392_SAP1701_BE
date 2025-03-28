package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.response_models.NotificationResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ProjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProjectMapper {

    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .topic(project.getTopic())
                .description(project.getDescription())
                .document(project.getDocument())
                .rejectionReason(project.getRejectionReason())
                .projectStatus(ProjectStatus.valueOf(project.getProjectStatus().name())) // Nếu Enum lưu dạng String
                .group(project.getGroup() != null ? project.getGroup().getId() : null)
                .lecturer(project.getLecturer() != null ? project.getLecturer().getId() : null)
                .recentNotifications(
                        (project.getNotificationList() != null && !project.getNotificationList().isEmpty())
                                ? project.getNotificationList().stream()
                                .filter(notification -> notification.getProject() != null) // 🔹 Chỉ lấy thông báo có project
                                .sorted(Comparator.comparing(Notification::getId).reversed()) // 🔹 Sắp xếp theo ID mới nhất
                                .limit(5)
                                .map(notification -> new NotificationResponse(
                                        notification.getId(),
                                        notification.getType(),
                                        notification.getContent(),
                                        notification.getNotificationStatus(),
                                        notification.getProject().getId(),
                                        notification.getGroup() != null ? notification.getGroup().getId() : null, // 🔹 Kiểm tra null cho group
                                        notification.getAppointment() != null ? notification.getAppointment().getId() : null,
                                        notification.getAccount() != null ? notification.getAccount().getId() : null,
                                        null
                                ))
                                .collect(Collectors.toList()) : new ArrayList<>()
                )
                .build();
    }

    public List<ProjectResponse> toListProjectResponse(List<Project> projects) {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(toProjectResponse(project));
        }
        return projectResponses;
    }
}
