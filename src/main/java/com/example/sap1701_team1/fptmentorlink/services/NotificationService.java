package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.request_models.NotificationRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationService{
    //Get all notification
    Response getAllNotifications();

    //Send message to Group
    Response sendNotificationToGroup(Integer groupId);

    //Send message to student
    Response sendNotificationToStudent(String studentId);
}
