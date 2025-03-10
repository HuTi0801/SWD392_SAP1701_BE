package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.mappers.NotificationMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import com.example.sap1701_team1.fptmentorlink.models.request_models.NotificationRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.GroupRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.StudentRepo;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;
    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;

    @Override
    public Response getAllNotifications() {
        Response response = new Response();
        try {
            List<Notification> notifications = notificationRepo.findAll();
            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setSuccess(true);
                response.setMessage("Notifications retrieved successfully!");
                response.setStatusCode(200);
                response.setResult(notificationMapper.toListResponse(notifications));
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving notifications: " + e.getMessage());
            response.setStatusCode(500);
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response sendNotificationToGroup(Integer groupId) {
        Response response = new Response();
        try {
            List<Notification> notifications = notificationRepo.findByGroupId(groupId);

            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found for this group!");
                response.setStatusCode(404);
                return response;
            }

            response.setSuccess(true);
            response.setMessage("Notifications retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(notifications);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving notifications: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response sendNotificationToStudent(String studentId) {
        Response response = new Response();
        try {
            Student student = studentRepo.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Group group = student.getGroup();
            if (group == null) {
                response.setSuccess(false);
                response.setMessage("Student is not in any group!");
                response.setStatusCode(404);
                return response;
            }

            List<Notification> notifications = notificationRepo.findByGroupId(group.getId());
            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found for this student's group!");
                response.setStatusCode(404);
                return response;
            }

            response.setSuccess(true);
            response.setMessage("Notifications retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(notifications);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving notifications: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
