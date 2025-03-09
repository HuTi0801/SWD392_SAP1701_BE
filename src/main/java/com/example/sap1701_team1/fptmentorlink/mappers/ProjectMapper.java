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
                .projectStatus(ProjectStatus.valueOf(project.getProjectStatus().name())) // Nếu Enum lưu dạng String
                .group(project.getGroup() != null ? project.getGroup().getId() : null)
                .lecturer(project.getLecturer() != null ? project.getLecturer().getId() : null)
                .recentNotifications(
                        project.getNotificationList().stream()
                                .filter(notification -> notification.getProject() != null) // 🔹 Chỉ lấy thông báo có project
                                .sorted(Comparator.comparing(Notification::getId).reversed()) // 🔹 Sắp xếp theo ID mới nhất
                                .limit(5) // 🔹 Giới hạn 5 thông báo gần nhất
                                .map(notification -> new NotificationResponse(
                                        notification.getId(),
                                        notification.getType(),
                                        notification.getContent(),
                                        notification.getNotificationStatus(),
                                        notification.getProject().getId(),
                                        notification.getGroup().getId()
                                ))
                                .collect(Collectors.toList())
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
