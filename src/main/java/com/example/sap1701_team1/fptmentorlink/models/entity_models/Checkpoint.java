package com.example.sap1701_team1.fptmentorlink.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`checkpoint`")
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpName;
    private String deadline;
    private String document;

    @OneToMany(mappedBy = "checkpoint")
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "checkpoint")
    private List<CheckpointReview> checkpointReviewList;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
