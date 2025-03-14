package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    //Get all appointment
    @GetMapping("/mentor/get-all-appointments")
    public Response getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    //Get a appointment
    @GetMapping("/mentor/get-a-appointment-by-id")
    public Response getAppointmentById(Integer appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    //Update status for Appointment
    @PatchMapping("/mentor/update-status-appointment")
    public Response updateAppointmentStatus(Integer appointmentId, AppointmentStatus status, @RequestParam(required = false) String reasonReject) {
        return appointmentService.updateStatusAppointment(appointmentId, status, reasonReject);
    }
}
