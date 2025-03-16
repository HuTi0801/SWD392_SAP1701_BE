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
    @GetMapping("/system/get-all-notification")
    public Response getAllNotification() {
        return notificationService.getAllNotifications();
    }

    // Gửi thông báo project đến nhóm
    @PostMapping("/system/send-notifi-project-to-group")
    public Response sendNotificationProjectToGroup(Integer groupId) {
        return notificationService.sendNotificationProjectToGroup(groupId);
    }

    // Gửi thông báo project đến từng student trong group
    @PostMapping("/system/send-notifi-project-to-student")
    public Response sendNotificationsToStudent(String studentId) {
        return notificationService.sendNotificationToStudent(studentId);
    }

    //Gửi thông báo cuộc hẹn đến student
    @PostMapping("/system/send-notifi-appointment-to-student")
    public Response sendNotificationAppointment(@RequestParam Integer appointmentId, @RequestParam String studentId) {
        return notificationService.sendNotificationAppointment(studentId, appointmentId);
    }

    //Search theo project Id
    @GetMapping("/system/search-notifi-by-project-Id")
    public Response searchNotificationByProjectId(@RequestParam Integer projectId) {
        return notificationService.searchNotiticationByProjectId(projectId);
    }

    //Search theo appointment Id
    @GetMapping("/system/search-notifi-by-appointment-Id")
    public Response searchNotificationByAppointmentId(@RequestParam Integer appointmentId) {
        return notificationService.searchNotificationByAppointmentId(appointmentId);
    }

    //
    @PostMapping("/system/send-notifi-report-to-mentor-or-lecturer")
    public Response sendNotificationForNewReport(Integer reportId, Integer receiverId) {
        return notificationService.sendNotificationReportForMentorLecture(reportId, receiverId);
    }
}
