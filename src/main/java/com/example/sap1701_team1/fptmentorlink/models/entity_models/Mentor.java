package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`mentor`")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String expertise;
    private int rating;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "mentor")
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "mentor")
    private List<MentorAvailability> mentorAvailabilityList;
}
