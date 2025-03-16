package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportRepo extends JpaRepository<Report, Integer> {
    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.notificationList WHERE r.id = :id")
    Optional<Report> findByIdWithNotifications(@Param("id") Integer id);
}
