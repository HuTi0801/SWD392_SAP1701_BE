package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationService{
    //Get all notification
    Response getAllNotifications();
}
