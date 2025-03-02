package com.example.sap1701_team1.fptmentorlink.models.response_models;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectResponse {
    private Integer id;
    private String topic;
    private String description;
    private String document;
    private ProjectStatus projectStatus;
//    private List<Checkpoint> checkpointList;
    private Integer group;
    private Integer lecturer;
//    private List<Notification> notificationList;
//    private List<Report> reportList;
}
