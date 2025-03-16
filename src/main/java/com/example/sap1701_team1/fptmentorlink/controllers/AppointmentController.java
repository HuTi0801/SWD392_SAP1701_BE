package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    //Get all appointment
    @GetMapping("/get-all-appointments")
    public Response getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    //Get a appointment
    @GetMapping("/get-a-appointment-by-id")
    public Response getAppointmentById(Integer id) {
        return appointmentService.getAppointmentById(id);
    }

    //Update status for Appointment
    @PatchMapping("/update-status-appointment")
    public Response updateAppointmentStatus(Integer id, AppointmentStatus status, @RequestParam(required = false) String reason) {
        return appointmentService.updateStatusAppointment(id, status, reason);
    }

    @PostMapping("/request-appointment")
    public Response requestAppointment(
            @RequestParam Integer mentorId,
            @RequestParam String studentId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam String description
    ) {
        return appointmentService.requestAppointment(mentorId, studentId, startTime, endTime, description);
    }
}
