package com.example.sap1701_team1.fptmentorlink.repositories;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.group.id = :groupId")
    List<Notification> findByGroupId(@Param("groupId") Integer groupId);

    List<Notification> findByAppointmentId(Integer appointmentId);

    List<Notification> findByProjectId(Integer projectId);

    List<Notification> findByAccountId(Integer accountId);

    List<Notification> findByReportIdAndType(Integer projectId, String titleFeedback);

    List<Notification> findByReportId(Integer reportId);
}