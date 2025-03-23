package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface ReportService {
    //Create report
    Response createReport(String studentId, Integer groupId, Integer projectId, Integer receiverId, String receiverType, String title, String content);

    //Get all report
    Response getAllReport();
}
