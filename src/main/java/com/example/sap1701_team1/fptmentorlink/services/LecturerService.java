package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface LecturerService {
    Response getAllLecturers();
    Response getReportDetailForLecture(Integer lectureId, Integer reportId);
    Response getAllReportsForLecture(Integer lectureId);
    Response updateFeedbackForLecture(Integer lectureId, Integer reportId, String feedback);

    //Get all lecture từ bảng account
    Response getAllLectureInTableAccount();
}
