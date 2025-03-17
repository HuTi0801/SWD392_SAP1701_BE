package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.ReportStatus;
import com.example.sap1701_team1.fptmentorlink.enums.Role;
import com.example.sap1701_team1.fptmentorlink.mappers.NotificationMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.*;
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
    private final AppointmentRepo appointmentRepo;
    private final ProjectRepo projectRepo;
    private final ReportRepo reportRepo;
    private final AccountRepo accountRepo;

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

    //Gửi thông báo project đến group
    @Override
    public Response sendNotificationProjectToGroup(Integer groupId) {
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

    //Gửi thông báo Project đến student
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

    //Gửi thông báo appointment đến student
    @Override
    public Response sendNotificationAppointment(String studentId, Integer appointmentId) {
        Response response = new Response();
        try {
            Student student = studentRepo.findById(studentId).orElse(null);
            if (student == null) {
                response.setSuccess(false);
                response.setMessage("Student not found!");
                response.setStatusCode(404);
                return response;
            }

            // Tìm cuộc hẹn theo ID
            Appointment appointment = appointmentRepo.findById(appointmentId).orElse(null);
            if (appointment == null) {
                response.setSuccess(false);
                response.setMessage("Appointment not found!");
                response.setStatusCode(404);
                return response;
            }

            // Kiểm tra xem sinh viên có phải là người đặt cuộc hẹn này không
            if (!appointment.getStudent().getId().equals(studentId)) {
                response.setSuccess(false);
                response.setMessage("Student does not have permission to view this appointment's notifications!");
                response.setStatusCode(403);
                return response;
            }

            // Lấy danh sách thông báo liên quan đến cuộc hẹn
            List<Notification> notifications = notificationRepo.findByAppointmentId(appointmentId);

            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found for this appointment!");
                response.setStatusCode(404);
                return response;
            }

            // Trả về danh sách Notification
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

    //Search notification theo project Id
    @Override
    public Response searchNotiticationByProjectId(Integer projectId) {
        Response response = new Response();
        try {
            if (!projectRepo.existsById(projectId)) {
                response.setSuccess(false);
                response.setMessage("Project not found!");
                response.setStatusCode(404);
                return response;
            }

            List<Notification> notifications = notificationRepo.findByProjectId(projectId);
            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found for this project!");
                response.setStatusCode(404);
                return response;
            }

            response.setSuccess(true);
            response.setMessage("Notifications retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(notificationMapper.toListResponse(notifications));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving notifications: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    //Search notification by appointment id
    @Override
    public Response searchNotificationByAppointmentId(Integer appointmentId) {
        Response response = new Response();
        try {
            if (!appointmentRepo.existsById(appointmentId)) {
                response.setSuccess(false);
                response.setMessage("Appointment not found!");
                response.setStatusCode(404);
                return response;
            }

            List<Notification> notifications = notificationRepo.findByAppointmentId(appointmentId);
            if (notifications.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No notifications found for this appointment!");
                response.setStatusCode(404);
                return response;
            }

            response.setSuccess(true);
            response.setMessage("Notifications retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(notificationMapper.toListResponse(notifications));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving notifications: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response sendNotificationReportForMentorLecture(Integer reportId, Integer receiverId) {
        Response response = new Response();
        try {
            // Tìm report
            Report report = reportRepo.findById(reportId).orElse(null);
            if (report == null) {
                response.setSuccess(false);
                response.setMessage("Report not found!");
                response.setStatusCode(404);
                return response;
            }

            // Tìm người nhận
            Account receiver = accountRepo.findById(receiverId).orElse(null);
            if (receiver == null) {
                response.setSuccess(false);
                response.setMessage("Receiver account not found!");
                response.setStatusCode(404);
                return response;
            }

            // Người gửi (chính là người tạo report)
            Account sender = report.getAccount();

            // Kiểm tra người nhận có đúng trong danh sách recipient không (dựa vào role)
            ReportStatus reportStatus = report.getReportStatus();
            boolean isValidRecipient = false;

            if (reportStatus == ReportStatus.REQUEST_FOR_SUPPORTING && receiver.getRole() == Role.MENTOR) {
                isValidRecipient = true;
            } else if ((reportStatus == ReportStatus.PENDING
                    || reportStatus == ReportStatus.IN_PROGRESS
                    || reportStatus == ReportStatus.COMPLETED)
                    && receiver.getRole() == Role.LECTURE) {
                isValidRecipient = true;
            }

            if (!isValidRecipient) {
                response.setSuccess(false);
                response.setMessage("Receiver does not match the report status requirement!");
                response.setStatusCode(403);
                return response;
            }

            // Gửi notification
            Notification notification = Notification.builder()
                    .type("New Report Notification")
                    .content("Sender: '" + sender.getFullname()
                            + "' has sent the report '" + report.getTitle()
                            + "' to '" + receiver.getFullname()
                            + "' - Reason: " + reportStatus.name())
                    .notificationStatus(NotificationStatus.UNREAD)
                    .account(receiver)
                    .report(report)
                    .build();

            notificationRepo.save(notification);

            response.setSuccess(true);
            response.setMessage("Notification sent successfully!");
            response.setStatusCode(200);
            response.setResult(notification);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error sending notification: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response sendProjectProposalNotification(Project project) {
        String content = "New topic proposed: " + project.getTopic() +
                " from group " + project.getGroup().getName() + " - Pending approval.";

        List<Notification> notifications = new ArrayList<>();

        Notification lecturerNotification = Notification.builder()
                .type("Project Proposal")
                .content(content)
                .notificationStatus(NotificationStatus.UNREAD)
                .project(project)
                .group(project.getGroup())
                .account(project.getLecturer().getAccount())
                .build();
        notifications.add(notificationRepo.save(lecturerNotification));

        List<Account> adminAccounts = accountRepo.findByRole(Role.ADMIN);
        for (Account admin : adminAccounts) {
            Notification adminNotification = Notification.builder()
                    .type("Project Proposal")
                    .content(content)
                    .notificationStatus(NotificationStatus.UNREAD)
                    .group(project.getGroup())
                    .project(project)
                    .account(admin)
                    .build();
            notifications.add(notificationRepo.save(adminNotification));
        }

        return Response.builder()
                .isSuccess(true)
                .message("Project proposal notifications sent successfully!")
                .statusCode(200)
                .result(notificationMapper.toListResponse(notifications))
                .build();
    }

    @Override
    public Response sendAppointmentRequestNotification(Appointment appointment) {
        String content = "You have a new appointment request from student: " + appointment.getStudent().getAccount().getFullname() + " and student code is " + appointment.getStudent().getAccount().getUserCode();

        Notification mentorNotification = Notification.builder()
                .type("Appointment Request")
                .content(content)
                .notificationStatus(NotificationStatus.UNREAD)
                .appointment(appointment)
                .account(appointment.getMentor().getAccount())
                .build();
        mentorNotification = notificationRepo.save(mentorNotification);

        return Response.builder()
                .isSuccess(true)
                .message("Appointment request notification sent to Mentor!")
                .statusCode(200)
                .result(notificationMapper.toResponse(mentorNotification))
                .build();
    }

    @Override
    public Response getNotificationsByAccount(Integer accountId) {
        List<Notification> notifications = notificationRepo.findByAccountId(accountId);
        return Response.builder()
                .isSuccess(true)
                .message("Notifications retrieved successfully!")
                .statusCode(200)
                .result(notificationMapper.toListResponse(notifications))
                .build();
    }
}

