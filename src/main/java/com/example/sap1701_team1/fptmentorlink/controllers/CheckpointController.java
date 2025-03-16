package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.CheckpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/checkpoints")
@RequiredArgsConstructor
public class CheckpointController {
    private final CheckpointService checkpointService;

    @GetMapping("/group-tasks")
    public Response getCheckpointsForStudent(@RequestParam String studentId) {
        return checkpointService.getCheckpointsForStudent(studentId);
    }
}
