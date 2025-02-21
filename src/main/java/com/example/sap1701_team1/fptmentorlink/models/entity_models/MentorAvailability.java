package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`mentor_availability`")
public class MentorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String term;
    private int year;
    private int weekNumber;
    private String dayOfWeek;
    private Date startTime;
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
}
