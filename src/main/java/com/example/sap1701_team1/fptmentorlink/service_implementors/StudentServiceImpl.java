package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.ReportMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Report;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ReportResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AccountRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ReportRepo;
import com.example.sap1701_team1.fptmentorlink.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final AccountRepo accountRepo;
    private final ReportRepo reportRepo;
    private final ReportMapper reportMapper;

    @Override
    public Response getAllReportForStudent(Integer studentId) {
        Response response = new Response();
        try {
            Optional<Account> optionalStudent = accountRepo.findById(studentId);
            if (optionalStudent.isEmpty() || !optionalStudent.get().getRole().name().equals("STUDENT")) {
                response.setSuccess(false);
                response.setMessage("Student not found or role mismatch!");
                response.setStatusCode(403);
                return response;
            }

            // Lấy tất cả các report do student này tạo ra
            List<Report> reports = reportRepo.findAll()
                    .stream()
                    .filter(report -> report.getAccount() != null
                            && report.getAccount().getId().equals(studentId)
                    )
                    .collect(Collectors.toList());

            if (reports.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No reports created by this student!");
                response.setStatusCode(404);
                return response;
            }

            // Convert sang ReportResponse
            List<ReportResponse> reportResponses = reports.stream()
                    .map(reportMapper::toReportResponse)
                    .collect(Collectors.toList());

            response.setMessage("Reports created by student fetched successfully");
            response.setResult(reportResponses);
            response.setSuccess(true);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving reports: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
