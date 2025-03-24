package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.mappers.AppointmentMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AppointmentResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.*;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppoimentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    private final NotificationRepo notificationRepo;
    private final MentorRepo mentorRepository;
    private final StudentRepo studentRepository;
    private final MentorAvailabilityRepo mentorAvailabilityRepository;
    private final AppointmentRepo appointmentRepository;
    private final NotificationRepo notificationRepository;

    //Get all appointment
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

    //Get a appointment
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

    @Override
    public Response updateStatusAppointment(Integer id, AppointmentStatus status, String reason) {
        Response response = new Response();
        try {
            //Tìm appointment theo Id
            Optional<Appointment> appointment = appointmentRepo.findById(id);
            if (appointment.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Appointment not found!");
                response.setStatusCode(404);
                response.setResult(null);
                return response;
            }

            Appointment appointment1 = appointment.get();

            //Nếu trạng thái mới trùng với trạng thái cũ -> không cập nhật
            if (appointment1.getAppointmentStatus() == status) {
                response.setSuccess(false);
                response.setMessage("Appointment is already in the requested status!");
                response.setStatusCode(404);
                return response;
            }

            //Nếu trạng thi mới là REJECTED thì phải nhập lý do
            if (status == AppointmentStatus.REJECTED && (reason == null || reason.trim().isEmpty())) {
                response.setSuccess(false);
                response.setMessage("Rejection reason is required when rejecting an appointment!");
                response.setStatusCode(404);
                return response;
            }

            //Cập nhật trạng thái mới
            appointment1.setAppointmentStatus(status);

            //Nếu bị REJECTED thì lưu lý do
            if (status == AppointmentStatus.REJECTED) {
                appointment1.setRejectionReason(reason);
            }

            //Tạo thông báo trong Notification
            String notificationContent = "Appointment about ' " + appointment1.getDescription() + " ' of " + appointment1.getStudent().getAccount().getFullname() + " has been " + status.name().toLowerCase();
            if (status == AppointmentStatus.REJECTED) {
                notificationContent += ". Reason: " + reason;
            }

            Notification notification = Notification.builder()
                    .type("Appointment Status Update")
                    .content(notificationContent)
                    .notificationStatus(NotificationStatus.UNREAD) // Mặc định chưa đọc
                    .appointment(appointment1) // Liên kết với appointment
                    .account(appointment1.getMentor().getAccount())
                    .build();

            notificationRepo.save(notification);

            // Trả về Response thành công
            response.setSuccess(true);
            response.setMessage("Appointment status updated successfully!");
            response.setStatusCode(200);
            response.setResult(appointmentMapper.toAppointmentResponse(appointment1)); // Chuyển thành response model

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error updating project status: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    @Transactional
    public Response requestAppointment(Integer mentorId, String studentId, Date startTime, Date endTime, String description) {
        Mentor mentor = mentorRepository.findById(mentorId).orElse(null);
        if (mentor == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Mentor not found")
                    .statusCode(400)
                    .build();
        }

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Student not found")
                    .statusCode(400)
                    .build();
        }

        MentorAvailability availability = mentorAvailabilityRepository.findByMentorAndStartTimeAndEndTimeAndIsBookedFalse(
                mentor, startTime, endTime).orElse(null);
        if (availability == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("No available slot found")
                    .statusCode(400)
                    .build();
        }

        // Create appointment
        Appointment appointment = Appointment.builder()
                .mentor(mentor)
                .student(student)
                .description(description)
                .mentorAvailability(availability)
                .appointmentStatus(AppointmentStatus.PENDING)
                .date(new Date())
                .notificationList(new ArrayList<>())
                .build();
        appointmentRepository.save(appointment);

        // Mark availability as booked
        availability.setBooked(true);
        mentorAvailabilityRepository.save(availability);

        // Create notification
        Notification notification = Notification.builder()
                .type("Appointment Request")
                .content("You have a new appointment request from " + student.getAccount().getFullname())
                .notificationStatus(NotificationStatus.UNREAD)
                .appointment(appointment)
                .account(mentor.getAccount())
                .build();
        notificationRepository.save(notification);

        appointment.getNotificationList().add(notification);
        appointmentRepository.save(appointment);


        return Response.builder()
                .isSuccess(true)
                .message("Appointment request sent successfully.")
                .statusCode(200)
                .result(null)
                .build();
    }
}

