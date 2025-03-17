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
@RequestMapping("/v1/mentor")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

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

    @GetMapping("/{mentorId}")
    public Response getMentorById(@PathVariable Integer mentorId) {
        return mentorService.getMentorById(mentorId);
    }

    @GetMapping("/view-info-report")
    public Response viewStudentReportToMentor(Integer mentorId, Integer reportId) {
        return mentorService.getReportDetailForMentor(mentorId, reportId);
    }
}
