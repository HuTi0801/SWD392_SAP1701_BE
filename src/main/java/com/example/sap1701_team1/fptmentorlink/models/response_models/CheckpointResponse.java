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
public class CheckpointResponse {
    private Integer id;
    private String cpName;
    private String deadline;
    private String document;
    private List<CheckpointReviewResponse> checkpointReviews;
}
