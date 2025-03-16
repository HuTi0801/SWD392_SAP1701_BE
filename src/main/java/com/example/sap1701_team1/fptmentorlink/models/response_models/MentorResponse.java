package com.example.sap1701_team1.fptmentorlink.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorResponse {
    private Integer id;
    private String fullName;
    private String email;
    private List<String> expertise;
    private int rating;
    private int year;
    private List<String> availableTimes;
}
