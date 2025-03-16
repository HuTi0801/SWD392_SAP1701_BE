package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

import java.util.Date;

public interface AppointmentService {
    //Get all appointment
    Response getAllAppointments();

    //Get a appointment
    Response getAppointmentById(Integer id);

    //Update status
    Response updateStatusAppointment(Integer id, AppointmentStatus status, String reason);

    // Create Appointment
    Response requestAppointment(Integer mentorId, String studentId, Date startTime, Date endTime, String description);

}
