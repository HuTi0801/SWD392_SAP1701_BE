package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //get ALl notification
    @GetMapping("/get-all-notification")
    public Response getAllNotification() {
        return notificationService.getAllNotifications();
    }
}
