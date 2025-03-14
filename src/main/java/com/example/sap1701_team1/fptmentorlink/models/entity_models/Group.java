package com.example.sap1701_team1.fptmentorlink.models.entity_models;

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
@EqualsAndHashCode(exclude = "studentList")
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "leader_id", unique = true) // Trỏ đến một Student duy nhất
    @JsonIgnore
    private Student leader;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Chat> chatList;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Report> reportList;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Student> studentList;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Notification> notificationList;
}
