package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.request_models.NotificationRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //get ALl notification
    @GetMapping("/get-all-notification")
    public Response getAllNotification() {
        return notificationService.getAllNotifications();
    }

    // Gửi thông báo đến nhóm
    @PostMapping("/group/{groupId}")
    public Response sendNotificationToGroup(@PathVariable Integer groupId) {
        return notificationService.sendNotificationToGroup(groupId);
    }

    // API Lấy thông báo của một sinh viên
    @GetMapping("/student/{studentId}")
    public Response getNotificationsForStudent(@PathVariable String studentId) {
        return notificationService.sendNotificationToStudent(studentId);
    }
}
