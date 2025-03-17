package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/lecturer")
@RequiredArgsConstructor
public class LecturerController {
    private final LecturerService lecturerService;

    //Xem report được nhận
    @GetMapping("/view-info-report")
    public Response viewStudentReportToLecturer(Integer lecturerId, Integer reportId) {
        return lecturerService.getReportDetailForLecture(lecturerId, reportId);
    }

    //Update feedback cho report
    @PostMapping("/update-feedback-report")
    public Response updateLecturerFeedbackReport(Integer lecturerId, Integer reportId, String feedback) {
        return lecturerService.updateFeedbackForLecture(lecturerId, reportId, feedback);
    }
}
