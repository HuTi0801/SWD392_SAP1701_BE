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
    @GetMapping("/get-all-project")
    public Response getAllProject() {
        return projectService.getAllProject();
    }

    //Get A Project
    @GetMapping("/get-a-project")
    public Response getAProject(Integer id) {
        return projectService.getProjectById(id);
    }

    //Search Project
    @GetMapping("/search-project")
    public Response searchProject(@Parameter(required = false) @RequestParam(required = false) String projectName,
                                  @Parameter(required = false) @RequestParam(required = false) ProjectStatus status) {
        return projectService.searchProject(projectName, status);
    }

    @PatchMapping("/update-status-project-{id}")
    public Response updateProjectStatus(Integer id, ProjectStatus status) {
        return projectService.updateStatusProjectById(id, status);
    }
}
