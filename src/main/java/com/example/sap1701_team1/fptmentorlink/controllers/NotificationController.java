package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.mappers.NotificationMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;

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

    //Send notification of report to mentor/lecturer
    @PostMapping("/system/send-notifi-report-to-mentor-or-lecturer")
    public Response sendNotificationReportForMentorLecture(Integer reportId, Integer receiverId) {
        return notificationService.sendNotificationReportForMentorLecture(reportId, receiverId);
    }

    @GetMapping("/get-by-account/{accountId}")
    public Response getNotificationsByAccount(@PathVariable Integer accountId) {
        List<Notification> notifications = notificationRepo.findByAccountId(accountId);
        return Response.builder()
                .isSuccess(true)
                .message("Notifications retrieved successfully!")
                .statusCode(200)
                .result(notificationMapper.toListResponse(notifications))
                .build();
    }

    @GetMapping("/get-mentor-notification")
    public Response getMentorNotifications(
            @Parameter(required = true) @RequestParam Integer accountId) {
        return notificationService.getNotificationsByAccount(accountId);
    }

    //Gửi thông báo feedback từ mentor đến student
    @GetMapping("/system/send-notif-feedback-from-mentor-to-student")
    public Response sendNotificationMentorFeedbackToStudent(Integer reportId) {
        return notificationService.sendNotificationMentorFeedback(reportId);
    }

    //Gửi thông báo feedback từ lecturer đến student
    @GetMapping("/system/send-notif-feedback-from-lecturer-to-student")
    public Response sendNotificationLecturerFeedbackToStudent(Integer reportId) {
        return notificationService.sendNotificationLectureFeedback(reportId);
    }
}
