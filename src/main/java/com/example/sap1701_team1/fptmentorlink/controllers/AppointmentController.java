package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("get-a-appointment-by-id")
    public Response getAppointmentById(Integer id) {
        return appointmentService.getAppointmentById(id);
    }
}
