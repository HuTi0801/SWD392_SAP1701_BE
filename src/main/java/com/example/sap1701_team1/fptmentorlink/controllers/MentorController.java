package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.request_models.MentorRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.MentorService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth/v1/mentor")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @GetMapping("/get-all")
    public Response getAllMentors(){
        return mentorService.getAllMentors();
    }

    @GetMapping("/search")
    public Response searchMentors(
            @Parameter @RequestParam(required = false) List<String> expertise,
            @Parameter @RequestParam(required = false) Integer minRating,
            @Parameter @RequestParam(required = false) Integer year,
            @Parameter @RequestParam(required = false) Date startTime,
            @Parameter @RequestParam(required = false) Date endTime) {

        MentorRequest request = MentorRequest.builder()
                .expertise(expertise)
                .minRating(minRating)
                .year(year)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return mentorService.searchMentors(request);
    }

    @GetMapping("/view_mentor_details/{mentorId}")
    public Response getMentorById(@PathVariable Integer mentorId) {
        return mentorService.getMentorById(mentorId);
    }

    @GetMapping("/view-info-report")
    public Response viewStudentReportToMentor(Integer mentorId, Integer reportId) {
        return mentorService.getReportDetailForMentor(mentorId, reportId);
    }

    @GetMapping("/view-all-report-of-mentor")
    public Response viewAllReportOfMentor(Integer mentorId) {
        return mentorService.getAllReportsForMentor(mentorId);
    }

    @PostMapping("/update-feedback-report")
    public Response updateMentorFeedbackReport(Integer mentorId, Integer reportId, String feedback) {
        return mentorService.updateFeedbackForMentor(mentorId, reportId, feedback);
    }
}
