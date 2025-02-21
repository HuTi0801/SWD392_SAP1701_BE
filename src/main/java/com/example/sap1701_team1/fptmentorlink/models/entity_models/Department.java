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
@Table(name = "`department`")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String depName;

    @OneToMany(mappedBy = "department")
    private List<Major> majorList;

    @OneToMany(mappedBy = "department")
    private List<Lecturer> lecturerList;
}
