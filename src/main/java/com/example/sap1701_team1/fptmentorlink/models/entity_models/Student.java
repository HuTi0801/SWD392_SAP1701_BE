package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "group")
@Table(name = "`student`")
public class Student {
    @Id
    private String id;

    private float jpa;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Appointment> appointmentList;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn(name = "major_id")
    @JsonIgnore
    private Major major;
}
