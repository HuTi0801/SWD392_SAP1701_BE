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
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "leader_id", unique = true) // Trỏ đến một Student duy nhất
    private Student leader;

    @OneToMany(mappedBy = "group")
    private List<Chat> chatList;

    @OneToMany(mappedBy = "group")
    private List<Report> reportList;

    @OneToMany(mappedBy = "group")
    private List<Student> studentList;
}
