package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import com.example.sap1701_team1.fptmentorlink.converter.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Convert(converter = StringListConverter.class)
    @Column(name = "expertise")
    private List<String> expertise;

    private int rating;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "mentor")
    @JsonIgnore
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "mentor")
    @JsonIgnore
    private List<MentorAvailability> mentorAvailabilityList;
}
