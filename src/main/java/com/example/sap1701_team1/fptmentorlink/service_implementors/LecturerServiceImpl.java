package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.LecturerMapper;
import com.example.sap1701_team1.fptmentorlink.mappers.ReportMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Lecturer;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Report;
import com.example.sap1701_team1.fptmentorlink.models.response_models.LecturerResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AccountRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.LecturerRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ReportRepo;
import com.example.sap1701_team1.fptmentorlink.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepo lecturerRepo;
    private final LecturerMapper lecturerMapper;
    private final ReportRepo reportRepo;
    private final AccountRepo accountRepo;
    private final ReportMapper reportMapper;

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
}
