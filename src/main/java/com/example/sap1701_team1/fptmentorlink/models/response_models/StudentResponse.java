package com.example.sap1701_team1.fptmentorlink.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponse {
    private String id;
    private String fullname;
    private String email;
    private Float jpa;
    private String majorName;
    private Integer groupId;
    private String groupName;
}
