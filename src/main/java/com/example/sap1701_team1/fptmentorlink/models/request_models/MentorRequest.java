package com.example.sap1701_team1.fptmentorlink.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorRequest {
    private List<String> expertise;
    private Integer minRating;
    private String term;
    private Integer year;
    private Date startTime;
    private Date endTime;
}