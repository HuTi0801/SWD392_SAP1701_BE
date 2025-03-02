package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.request_models.ProjectRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ProjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                .build();
    }

    public List<ProjectResponse> toListProjectResponse(List<Project> projects) {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(toProjectResponse(project));
        }
        return projectResponses;
    }

    public Project toProject(ProjectRequest projectRequest) {
        return Project.builder()
                .id(projectRequest.getId())
                .topic(projectRequest.getTopic())
                .description(projectRequest.getDescription())
                .document(projectRequest.getDocument())
                .projectStatus(ProjectStatus.valueOf(String.valueOf(projectRequest.getProjectStatus()))) // Chuyển từ String sang Enum
                .build();
    }
}
