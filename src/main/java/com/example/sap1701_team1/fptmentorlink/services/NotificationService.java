package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.request_models.NotificationRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationService{
    //Get all notification
    Response getAllNotifications();

    //Send message Project to Group
    Response sendNotificationProjectToGroup(Integer groupId);

    //Send message to student
    Response sendNotificationToStudent(String studentId);

    //Send messgae Appointment to student
    Response sendNotificationAppointment(String studentId, Integer appointmentId);

    //Search notification by project Id
    Response searchNotiticationByProjectId(Integer projectId);

    //Search notification by appoinment Id
    Response searchNotificationByAppointmentId(Integer appointmentId);
}
