package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.NotificationMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;

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
}
