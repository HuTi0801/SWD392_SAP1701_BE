package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface LecturerService {
    Response getAllLecturers();
    Response getReportDetailForLecture(Integer lectureId, Integer reportId);
}
