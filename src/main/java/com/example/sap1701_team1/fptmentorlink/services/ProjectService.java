package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface ProjectService {
    //Get all project
    Response getAllProject();

    //Get a project
    Response getProjectById(Integer id);

    //Search project by team name
    Response searchProject(String projectName, ProjectStatus status);

    //Approve or Reject Project
    Response updateStatusProjectById(Integer id, ProjectStatus status, String rejectReason);
}
