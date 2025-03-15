package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`project`")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String topic;
    private String description;
    private String document;
    private String rejectionReason;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project")
    private List<Checkpoint> checkpointList;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Report> reportList;
}
