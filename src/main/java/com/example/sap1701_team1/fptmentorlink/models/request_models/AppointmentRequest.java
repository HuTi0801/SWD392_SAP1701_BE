package com.example.sap1701_team1.fptmentorlink.models.request_models;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {
    private Date date;
    private String description;
    private AppointmentStatus appointmentStatus;
    private Integer mentorId;
    private Integer studentId;
}
