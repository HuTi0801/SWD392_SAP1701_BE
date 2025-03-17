package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.mappers.AppointmentMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AppointmentResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.*;
import com.example.sap1701_team1.fptmentorlink.services.AppointmentService;
import com.example.sap1701_team1.fptmentorlink.services.NotificationService;
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
    private final MentorRepo mentorRepo;
    private final StudentRepo studentRepo;
    private final AvailabilitySlotRepo availabilitySlotRepo;
    private final NotificationService notificationService;

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
        try{
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
            if (appointment1.getAppointmentStatus() == status){
                response.setSuccess(false);
                response.setMessage("Appointment is already in the requested status!");
                response.setStatusCode(404);
                return response;
            }

            //Nếu trạng thi mới là REJECTED thì phải nhập lý do
            if (status == AppointmentStatus.REJECTED && (reason == null || reason.trim().isEmpty())){
                response.setSuccess(false);
                response.setMessage("Rejection reason is required when rejecting an appointment!");
                response.setStatusCode(404);
                return response;
            }

            //Cập nhật trạng thái mới
            appointment1.setAppointmentStatus(status);

            //Nếu bị REJECTED thì lưu lý do
            if(status == AppointmentStatus.REJECTED){
                appointment1.setRejectionReason(reason);
            }

            //Tạo thông báo trong Notification
            String notificationContent = "Appointment about ' " + appointment1.getDescription() + " ' of " +appointment1.getStudent().getAccount().getFullname() +  " has been " + status.name().toLowerCase();
            if(status == AppointmentStatus.REJECTED){
                notificationContent += ". Reason: " + reason;
            }

            Notification notification = Notification.builder()
                    .type("Appointment Status Update")
                    .content(notificationContent)
                    .notificationStatus(NotificationStatus.UNREAD) // Mặc định chưa đọc
                    .appointment(appointment1) // Liên kết với appointment
                    .build();

            notificationRepo.save(notification);

            // Trả về Response thành công
            response.setSuccess(true);
            response.setMessage("Appointment status updated successfully!");
            response.setStatusCode(200);
            response.setResult(appointmentMapper.toAppointmentResponse(appointment1)); // Chuyển thành response model

        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Error updating project status: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response requestAppointment(Integer mentorId, String studentId, Date startTime, Date endTime, String description) {
        try {
            Optional<Mentor> mentorOpt = mentorRepo.findById(mentorId);
            if (mentorOpt.isEmpty()) {
                return Response.builder()
                        .isSuccess(false)
                        .message("Mentor not found!")
                        .statusCode(404)
                        .build();
            }
            Mentor mentor = mentorOpt.get();

            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return Response.builder()
                        .isSuccess(false)
                        .message("Student not found!")
                        .statusCode(404)
                        .build();
            }
            Student student = studentOpt.get();

            Optional<AvailabilitySlot> slotOpt = availabilitySlotRepo.findByMentorAvailabilityMentorIdAndStartTimeAndEndTime(
                    mentorId, startTime, endTime);

            if (slotOpt.isEmpty() || slotOpt.get().isBooked()) {
                return Response.builder()
                        .isSuccess(false)
                        .message("No available slots for this time!")
                        .statusCode(400)
                        .build();
            }

            AvailabilitySlot slot = slotOpt.get();
            slot.setBooked(true);
            availabilitySlotRepo.save(slot);

            Appointment appointment = Appointment.builder()
                    .date(new Date())
                    .description(description)
                    .appointmentStatus(AppointmentStatus.PENDING)
                    .mentor(mentor)
                    .student(student)
                    .mentorAvailability(slot.getMentorAvailability())
                    .notificationList(new ArrayList<>())
                    .build();

            appointment = appointmentRepo.save(appointment);

            notificationService.sendAppointmentRequestNotification(appointment);

            return Response.builder()
                    .isSuccess(true)
                    .message("Appointment request submitted successfully!")
                    .statusCode(200)
                    .result(appointmentMapper.toAppointmentResponse(appointment))
                    .build();

        } catch (Exception e) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Error requesting appointment: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
    }
}
