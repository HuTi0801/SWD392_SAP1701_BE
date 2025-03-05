package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
}
