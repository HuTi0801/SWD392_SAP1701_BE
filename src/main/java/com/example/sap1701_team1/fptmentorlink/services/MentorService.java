package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Feedback;
import com.example.sap1701_team1.fptmentorlink.models.request_models.MentorRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface MentorService {
    Response getAllMentors();
    Response getAllMentorFromAccountTable();
    Response searchMentors(MentorRequest request);
    Response getMentorById(Integer mentorId);

    //Xem report
    Response getReportDetailForMentor(Integer mentorId, Integer reportId);

    //Xem tất cả report của mentor
    Response getAllReportsForMentor(Integer mentorId);

    //Feedback report
    Response updateFeedbackForMentor(Integer mentorId, Integer reportId, String feedback);
}
