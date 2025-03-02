package com.example.sap1701_team1.fptmentorlink.models.request_models;

import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {
    private Integer id;
    private String topic;
    private String description;
    private String document;
    private ProjectStatus projectStatus;
    private Group group;
    private Lecturer lecturer;
}
