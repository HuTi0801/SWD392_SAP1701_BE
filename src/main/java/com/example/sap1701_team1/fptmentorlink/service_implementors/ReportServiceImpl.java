package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.ReportStatus;
import com.example.sap1701_team1.fptmentorlink.mappers.ReportMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.*;
import com.example.sap1701_team1.fptmentorlink.services.ReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;
    private final ProjectRepo projectRepo;
    private final ReportRepo reportRepo;
    private final NotificationRepo notificationRepo;
    private final ReportMapper reportMapper;
    private final AccountRepo accountRepo;

    @Transactional
    @Override
    public Response createReport(String studentId, Integer groupId, Integer projectId, Integer receiverId, String receiverType, String title, String content) {
        Response response = new Response();
        try {
            // 🏫 Tìm Student
            Optional<Student> optionalStudent = studentRepo.findById(studentId);
            if (optionalStudent.isEmpty()) {
                return buildErrorResponse("Student not found!", 404);
            }
            Student student = optionalStudent.get();

            // 🔍 Tìm Group
            Optional<Group> optionalGroup = groupRepo.findById(groupId);
            if (optionalGroup.isEmpty()) {
                return buildErrorResponse("Group not found!", 404);
            }
            Group group = optionalGroup.get();

            // 📌 Tìm Project
            Optional<Project> optionalProject = projectRepo.findById(projectId);
            if (optionalProject.isEmpty()) {
                return buildErrorResponse("Project not found!", 404);
            }
            Project project = optionalProject.get();

            // 🔗 Kiểm tra Student có nằm trong Group không?
            if (!group.getStudentList().contains(student)) {
                return buildErrorResponse("Student does not belong to this group!", 403);
            }

            // 🔗 Kiểm tra Group có liên kết đúng với Project không?
            if (!project.getGroup().getId().equals(groupId)) {
                return buildErrorResponse("Group does not belong to this project!", 403);
            }

            // ✅ Kiểm tra Student có phải là leader của Group không
            if (!group.getLeader().getId().equals(student.getId())) {
                return buildErrorResponse("Only the group leader can submit a report!", 403);
            }

            // 🎯 Xử lý người nhận: Mentor hoặc Lecturer
            Account receiverAccount = null;
            String receiverName = "";

            if ("MENTOR".equalsIgnoreCase(receiverType)) {
                Optional<Account> mentorAcc = accountRepo.findById(receiverId);
                if (mentorAcc.isEmpty() || !"MENTOR".equalsIgnoreCase(mentorAcc.get().getRole().name())) {
                    return buildErrorResponse("Account mentor is not found!", 404);
                }
                receiverAccount = mentorAcc.get();
                receiverName = mentorAcc.get().getFullname();
            } else if ("LECTURE".equalsIgnoreCase(receiverType)) {
                Optional<Account> lecturerAcc = accountRepo.findById(receiverId);
                if (lecturerAcc.isEmpty() || !"LECTURE".equalsIgnoreCase(lecturerAcc.get().getRole().name())) {
                    return buildErrorResponse("Account lecturer is not found!", 404);
                }
                receiverAccount = lecturerAcc.get();
                receiverName = lecturerAcc.get().getFullname();
            } else {
                return buildErrorResponse("Invalid receiver type! Must be 'MENTOR' or 'LECTURER'", 400);
            }

            Report report = new Report();

            // 📜 Tạo Report với trạng thái mặc định là PENDING - role LECTURE
            if (receiverType.equalsIgnoreCase("LECTURE")) {
                report = Report.builder()
                        .title(title)
                        .content(content)
                        .submittedTime(new Date())
                        .reportStatus(ReportStatus.PENDING)
                        .account(student.getAccount()) // Gán người gửi là student
                        .group(group)
                        .project(project)
                        .build();
                reportRepo.save(report); // Lưu Report vào DB
            } else if (receiverType.equalsIgnoreCase("MENTOR")) {
                report = Report.builder()
                        .title(title)
                        .content(content)
                        .submittedTime(new Date())
                        .reportStatus(ReportStatus.REQUEST_FOR_SUPPORTING)
                        .account(student.getAccount()) // Gán người gửi là student
                        .group(group)
                        .project(project)
                        .build();
                reportRepo.save(report); // Lưu Report vào DB
            }

            // 📩 Gửi Notification đến người nhận
            Notification notification = Notification.builder()
                    .type("New Report Submission")
                    .content("New report submitted by " + student.getAccount().getFullname() +
                            " for project '" + project.getTopic() + "' to " + receiverName)
                    .notificationStatus(NotificationStatus.UNREAD)
                    .account(receiverAccount) // Gửi đến người nhận
                    .report(report)
                    .build();

            notificationRepo.save(notification); // Lưu thông báo vào DB

            List<Notification> notifications = report.getNotificationList();
            if (notifications == null) {
                notifications = new ArrayList<>();
            }
            notifications.add(notification);
            report.setNotificationList(notifications);

            // 🔥 Trả về Response thành công
            response.setSuccess(true);
            response.setMessage("Report submitted successfully!");
            response.setStatusCode(200);
            response.setResult(reportMapper.toReportResponse(report));

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error submitting report: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    // 🚨 Hàm build response lỗi
    private Response buildErrorResponse(String message, int statusCode) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        return response;
    }
}
