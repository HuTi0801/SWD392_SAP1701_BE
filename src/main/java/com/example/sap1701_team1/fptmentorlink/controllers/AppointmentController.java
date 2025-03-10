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
    public Response updateAppointmentStatus(Integer id, AppointmentStatus status, @RequestParam(required = fString reason) {
        return appointmentService.updateStatusAppointment(id, status, reason);
    }
}
