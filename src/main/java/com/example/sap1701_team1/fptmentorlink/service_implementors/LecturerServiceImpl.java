package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.Role;
import com.example.sap1701_team1.fptmentorlink.mappers.LecturerMapper;
import com.example.sap1701_team1.fptmentorlink.mappers.ReportMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Lecturer;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Report;
import com.example.sap1701_team1.fptmentorlink.models.response_models.LecturerResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ReportResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AccountRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.LecturerRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ReportRepo;
import com.example.sap1701_team1.fptmentorlink.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepo lecturerRepo;
    private final LecturerMapper lecturerMapper;
    private final ReportRepo reportRepo;
    private final AccountRepo accountRepo;
    private final ReportMapper reportMapper;
    private final NotificationRepo notificationRepo;

    @Override
    public Response getAllLecturers() {
        Response response = new Response();
        try {
            List<Lecturer> lecturerList = lecturerRepo.findAll();

            if (lecturerList.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no lecturers!");
                response.setStatusCode(404);
            } else {
                List<LecturerResponse> lecturerResponses = lecturerMapper.toResponseList(lecturerList);
                response.setMessage("Get all lecturers successfully!");
                response.setResult(lecturerResponses);
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving lecturers: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getReportDetailForLecture(Integer lectureId, Integer reportId) {
            Response response = new Response();
            try {
                Optional<Account> optionalLecture = accountRepo.findById(lectureId);

                if (optionalLecture.isEmpty() || !optionalLecture.get().getRole().name().equalsIgnoreCase("LECTURE")) {
                    response.setStatusCode(404);
                    response.setSuccess(false);
                    response.setMessage("Lecture not found!");
                    return response;
                }

                Optional<Report> optionalReport = reportRepo.findById(reportId);

                if (optionalReport.isEmpty()) {
                    response.setStatusCode(404);
                    response.setSuccess(false);
                    response.setMessage("Report not found!");
                    return response;
                }

                Report report = optionalReport.get();

                // Kiểm tra report có đúng là gửi cho lecture này không
                boolean isReceiver = report.getNotificationList().stream()
                        .anyMatch(notification -> notification.getAccount().getId().equals(lectureId));

                if (!isReceiver) {
                    response.setStatusCode(403);
                    response.setSuccess(false);
                    response.setMessage("You do not have permission to view this report!");
                    return response;
                }

                // Thành công
                response.setStatusCode(200);
                response.setSuccess(true);
                response.setMessage("Get report detail successfully!");
                response.setResult(reportMapper.toReportResponse(report));
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setSuccess(false);
                response.setMessage("Error retrieving report detail: " + e.getMessage());
            }
            return response;
    }

    @Override
    public Response getAllReportsForLecture(Integer lectureId) {
        Response response = new Response();
        try {
            Optional<Account> optionalLecture = accountRepo.findById(lectureId);

            if (optionalLecture.isEmpty() || !optionalLecture.get().getRole().name().equalsIgnoreCase("LECTURE")) {
                response.setStatusCode(404);
                response.setSuccess(false);
                response.setMessage("Lecture not found!");
                return response;
            }

            // Lọc report mà lecture nhận notification
            List<Report> reports = reportRepo.findAll()
                    .stream()
                    .filter(report -> report.getNotificationList()
                            .stream()
                            .anyMatch(notification -> notification.getAccount().getId().equals(lectureId))
                    ).collect(Collectors.toList());

            if (reports.isEmpty()) {
                response.setStatusCode(404);
                response.setSuccess(false);
                response.setMessage("No reports found for this lecture!");
                return response;
            }

            // Convert list sang ReportResponse
            List<ReportResponse> reportResponses = reports.stream()
                    .map(reportMapper::toReportResponse)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Get all reports successfully!");
            response.setResult(reportResponses);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving reports: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateFeedbackForLecture(Integer lectureId, Integer reportId, String feedback) {
        Response response = new Response();
        try {
            // 1. Kiểm tra lecture
            Optional<Account> optionalLecture = accountRepo.findById(lectureId);
            if (optionalLecture.isEmpty() || !optionalLecture.get().getRole().name().equals("LECTURE")) {
                response.setSuccess(false);
                response.setMessage("Lecture not found or role mismatch!");
                response.setStatusCode(403);
                return response;
            }

            // 2. Kiểm tra report
            Optional<Report> optionalReport = reportRepo.findById(reportId);
            if (optionalReport.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Report not found!");
                response.setStatusCode(404);
                return response;
            }

            Report report = optionalReport.get();

            // 3. Kiểm tra lecture có nhận được report qua notification không
            boolean isReceiver = report.getNotificationList()
                    .stream()
                    .anyMatch(noti -> noti.getAccount().getId().equals(lectureId));

            if (!isReceiver) {
                response.setSuccess(false);
                response.setMessage("You are not authorized to feedback this report!");
                response.setStatusCode(403);
                return response;
            }

            // 4. Cập nhật feedback
            report.setFeedback(feedback);
            report.setFeedbackTime(new Date());
            reportRepo.save(report);

            // Gửi notification cho student (người tạo report)
            Account student = report.getAccount();
            Notification notification = Notification.builder()
                    .type("Feedback Notification")
                    .content("Lecture '" + optionalLecture.get().getFullname() + "' has given feedback on your report: '" + report.getTitle() + "'")
                    .notificationStatus(NotificationStatus.UNREAD)
                    .account(student)
                    .report(report)
                    .build();
            notificationRepo.save(notification);

            response.setSuccess(true);
            response.setMessage("Lecture feedback submitted successfully!");
            response.setStatusCode(200);
            response.setResult(reportMapper.toReportResponse(report));

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error submitting feedback: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllLectureInTableAccount() {
        Response response = new Response();
        try {
            // Lấy tất cả account có role là LECTURE
            List<Account> lectures = accountRepo.findByRole(Role.LECTURE);

            if (lectures.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No lecturers found!");
                response.setStatusCode(404);
                return response;
            }

            // Dùng mapper chuẩn đã tạo
            List<LecturerResponse> lecturerResponses = lecturerMapper.toResponseListFromAccounts(lectures);

            response.setSuccess(true);
            response.setMessage("Lecturers retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(lecturerResponses);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving lecturers: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
