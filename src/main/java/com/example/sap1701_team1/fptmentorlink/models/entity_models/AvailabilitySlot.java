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
@Table(name = "`availability_slot`")
public class AvailabilitySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date startTime;
    private Date endTime;
    private boolean isBooked;
    @ManyToOne
    @JoinColumn(name = "mentor_availability_id")
    private MentorAvailability mentorAvailability;
}
