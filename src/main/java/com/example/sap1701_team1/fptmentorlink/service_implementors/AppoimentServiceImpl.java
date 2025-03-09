package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.AppointmentMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Appointment;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AppointmentResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AppointmentRepo;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppoimentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    @Override
    public Response getAllAppointments() {
        Response response = new Response();
        try {
            var appointmentList = appointmentRepo.findAll();

            if (appointmentList.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no appointments!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                List<AppointmentResponse> appointmentResponses = appointmentMapper.toListAppointmentResponse(appointmentList);
                response.setMessage("Get all appointments successfully!");
                response.setResult(appointmentResponses);
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving appointments: " + e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    @Override
    public Response getAppointmentById(Integer id) {
        Response response = new Response();
        try {
            Optional<Appointment> appointment = appointmentRepo.findById(id);

            if (appointment.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Appointment not found!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Get appointment successfully!");
                response.setResult(appointmentMapper.toAppointmentResponse(appointment.get()));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }
}
