package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.ProjectService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    //Get All Project
    @GetMapping("/lecturer/get-all-project")
    public Response getAllProject() {
        return projectService.getAllProject();
    }

    //Get A Project
    @GetMapping("/lecturer/get-a-project")
    public Response getAProject(Integer projectId) {
        return projectService.getProjectById(projectId);
    }

    //Search Project
    @GetMapping("/search-project")
    public Response searchProject(@RequestParam(required = false) String projectName,
                                  @RequestParam(required = false) ProjectStatus status) {
        return projectService.searchProject(projectName, status);
    }

    @PatchMapping("/lecturer/update-status-project")
    public Response updateProjectStatus(Integer projectId, ProjectStatus status, @RequestParam(required = false) String reasonReject) {
        return projectService.updateStatusProjectById(projectId, status, reasonReject);
    }

    @PostMapping("/create")
    public Response createProject(
            @RequestParam String groupId,
            @RequestParam String topicName,
            @RequestParam String description,
            @RequestParam String lecturerId,
            @RequestParam String requesterUserCode) {
        return projectService.createProject(groupId, topicName, description, lecturerId, requesterUserCode);
    }
}
