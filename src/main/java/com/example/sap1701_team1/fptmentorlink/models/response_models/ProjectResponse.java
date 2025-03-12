package com.example.sap1701_team1.fptmentorlink.models.response_models;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
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
    private String rejectionReason;
    private ProjectStatus projectStatus;
    private Integer group;
    private Integer lecturer;
    private List<NotificationResponse> recentNotifications;
//    private List<Report> reportList;
    //    private List<Checkpoint> checkpointList;
}
