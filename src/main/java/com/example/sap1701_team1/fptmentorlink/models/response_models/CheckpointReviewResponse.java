package com.example.sap1701_team1.fptmentorlink.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckpointReviewResponse {
    private Integer id;
    private float score;
    private String feedback;
    private Date reviewedAt;
    private Integer lecturerId;
}
