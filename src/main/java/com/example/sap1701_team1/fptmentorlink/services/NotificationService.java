package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Appointment;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface NotificationService{
    //Get all notification
    Response getAllNotifications();

    //Send message Project to Group
    Response sendNotificationProjectToGroup(Integer groupId);

    //hàm gửi thoông báo đến cho student trong group
    Response sendNotificationToStudent(String studentId);

    //Send messgae Appointment to student
    Response sendNotificationAppointment(String studentId, Integer appointmentId);

    //Search notification by project Id
    Response searchNotiticationByProjectId(Integer projectId);

    //Search notification by appoinment Id
    Response searchNotificationByAppointmentId(Integer appointmentId);

    //Send message report to Lecturer - mentor
    Response sendNotificationReportForMentorLecture(Integer reportId, Integer receiverId);

    //Update status unRead -> read
    Response updateStautsNotification(Integer notificationId);

    Response sendProjectProposalNotification(Project project);

    Response sendAppointmentRequestNotification(Appointment appointment);
    Response getNotificationsByAccount(Integer accountId);

    //Gửi thông báo feedback từ mentor đến student
    Response sendNotificationLectureFeedback(Integer reportId);

    //Gửi thông báo feedback từ lecturer đến student
    Response sendNotificationMentorFeedback(Integer reportId);
}
