package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface CheckpointService {
    Response getCheckpointsForStudent(String studentId);
}
