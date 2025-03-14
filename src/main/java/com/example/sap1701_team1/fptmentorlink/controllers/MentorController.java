package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.request_models.MentorRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.MentorService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @Parameter @RequestParam(required = false) String term,
            @Parameter @RequestParam(required = false) Integer year,
            @Parameter @RequestParam(required = false) Integer weekNumber,
            @Parameter @RequestParam(required = false) String dayOfWeek) {

        MentorRequest request = MentorRequest.builder()
                .expertise(expertise)
                .minRating(minRating)
                .term(term)
                .year(year)
                .weekNumber(weekNumber)
                .dayOfWeek(dayOfWeek)
                .build();

        return mentorService.searchMentors(request);
    }
}
