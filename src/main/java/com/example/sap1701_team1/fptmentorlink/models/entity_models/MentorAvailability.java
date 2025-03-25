package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`mentor_availability`")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int year;
    private Date startTime;
    private Date endTime;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;
}

