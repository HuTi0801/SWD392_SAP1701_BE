package com.example.sap1701_team1.fptmentorlink.models.entity_models;
import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`notification`")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    @JsonIgnore
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    @JsonIgnore
    private Checkpoint checkpoint;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    @JsonIgnore
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonIgnore
    private Report report;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;
}
