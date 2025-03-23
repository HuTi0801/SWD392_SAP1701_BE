package com.example.sap1701_team1.fptmentorlink.controllers;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/student/create-report")
    public Response createReport(@RequestParam String studentId,
                                 @RequestParam Integer groupId,
                                 @RequestParam Integer projectId,
                                 @RequestParam Integer receiverId,
                                 @RequestParam String receiverType,
                                 @RequestParam String title,
                                 @RequestParam String content) {
        return reportService.createReport(studentId, groupId, projectId, receiverId, receiverType, title, content);
    }

    //Get all report
    @GetMapping("get-all-report")
    public Response getAllReport() {
        return reportService.getAllReport();
    }
}
