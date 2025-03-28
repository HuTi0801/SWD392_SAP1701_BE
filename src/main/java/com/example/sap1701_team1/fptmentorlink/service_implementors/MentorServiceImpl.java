package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.Role;
import com.example.sap1701_team1.fptmentorlink.mappers.MentorMapper;
import com.example.sap1701_team1.fptmentorlink.mappers.ReportMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.models.request_models.MentorRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.MentorResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.ReportResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AccountRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.MentorRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ReportRepo;
import com.example.sap1701_team1.fptmentorlink.services.MentorService;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorRepo mentorRepo;
    private final MentorMapper mentorMapper;
    private final ReportMapper reportMapper;
    private final ReportRepo reportRepo;
    private final AccountRepo accountRepo;
    private final NotificationRepo notificationRepo;

    @Override
    public Response getAllMentors() {
        Response response = new Response();
        try {
            List<Mentor> mentorList = mentorRepo.findAll();

            if (mentorList.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no mentors!");
                response.setStatusCode(404);
            } else {
                List<MentorResponse> mentorResponses = mentorMapper.toListMentorResponse(mentorList);
                response.setMessage("Get all mentors successfully!");
                response.setResult(mentorResponses);
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving mentors: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllMentorFromAccountTable() {
        Response response = new Response();
        try {
            // Lấy tất cả Account có role = MENTOR
            List<Account> mentors = accountRepo.findByRole(Role.MENTOR);

            if (mentors.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No mentors found!");
                response.setStatusCode(404);
                return response;
            }

            // Map sang DTO đơn giản
            List<MentorResponse> mentorResponses = mentorMapper.toResponseListFromAccounts(mentors);

            response.setSuccess(true);
            response.setMessage("Mentors retrieved successfully!");
            response.setStatusCode(200);
            response.setResult(mentorResponses);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving mentors: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response searchMentors(MentorRequest request) {
        try {
            Specification<Mentor> spec = getMentorSpecification(request);
            List<Mentor> mentors = mentorRepo.findAll(spec);

            if (mentors.isEmpty()) {
                return Response.builder()
                        .isSuccess(false)
                        .message("No mentors found!")
                        .statusCode(404)
                        .result(Collections.emptyList())
                        .build();
            }

            return Response.builder()
                    .isSuccess(true)
                    .message("Mentors retrieved successfully!")
                    .statusCode(200)
                    .result(mentorMapper.toListMentorResponse(mentors))
                    .build();

        } catch (Exception e) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Error retrieving mentors: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public Response getMentorById(Integer mentorId) {
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
            MentorResponse mentorResponse = mentorMapper.toMentorResponse(mentor);

            return Response.builder()
                    .isSuccess(true)
                    .message("Mentor details retrieved successfully!")
                    .statusCode(200)
                    .result(mentorResponse)
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Error retrieving mentor: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public Response getReportDetailForMentor(Integer mentorId, Integer reportId) {
        Response response = new Response();
        try {
            Optional<Account> optionalMentor = accountRepo.findById(mentorId);
            if (optionalMentor.isEmpty() || !optionalMentor.get().getRole().name().equals("MENTOR")) {
                response.setSuccess(false);
                response.setMessage("Mentor not found or role mismatch!");
                response.setStatusCode(403);
                return response;
            }

            Optional<Report> optionalReport = reportRepo.findById(reportId);
            if (optionalReport.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Report not found!");
                response.setStatusCode(404);
                return response;
            }

            Report report = optionalReport.get();

            boolean isReceiver = report.getNotificationList()
                    .stream()
                    .anyMatch(noti -> noti.getAccount().getId().equals(mentorId));

            if (!isReceiver) {
                response.setSuccess(false);
                response.setMessage("You are not authorized to view this report!");
                response.setStatusCode(403);
                return response;
            }

            response.setMessage("Report fetched successfully");
            response.setResult(reportMapper.toReportResponse(report));
            response.setSuccess(true);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving report: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllReportsForMentor(Integer mentorId) {
        Response response = new Response();
        try {
            Optional<Account> optionalMentor = accountRepo.findById(mentorId);
            if (optionalMentor.isEmpty() || !optionalMentor.get().getRole().name().equals("MENTOR")) {
                response.setSuccess(false);
                response.setMessage("Mentor not found or role mismatch!");
                response.setStatusCode(403);
                return response;
            }

            // Lấy tất cả các report mà mentor này là người nhận notification
            List<Report> reports = reportRepo.findAll()
                    .stream()
                    .filter(report -> report.getNotificationList()
                            .stream()
                            .anyMatch(noti -> noti.getAccount().getId().equals(mentorId))
                    ).collect(Collectors.toList());

            if (reports.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No reports found for this mentor!");
                response.setStatusCode(404);
                return response;
            }

            // Convert sang ReportResponse
            List<ReportResponse> reportResponses = reports.stream()
                    .map(reportMapper::toReportResponse)
                    .collect(Collectors.toList());

            response.setMessage("Reports fetched successfully");
            response.setResult(reportResponses);
            response.setSuccess(true);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error retrieving reports: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateFeedbackForMentor(Integer mentorId, Integer reportId, String feedback) {
        Response response = new Response();
        try {
            Optional<Account> optionalMentor = accountRepo.findById(mentorId);
            if (optionalMentor.isEmpty() || !optionalMentor.get().getRole().name().equals("MENTOR")) {
                response.setSuccess(false);
                response.setMessage("Mentor not found or role mismatch!");
                response.setStatusCode(403);
                return response;
            }

            Optional<Report> optionalReport = reportRepo.findById(reportId);
            if (optionalReport.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Report not found!");
                response.setStatusCode(404);
                return response;
            }

            Report report = optionalReport.get();

            boolean isReceiver = report.getNotificationList()
                    .stream()
                    .anyMatch(noti -> noti.getAccount().getId().equals(mentorId));

            if (!isReceiver) {
                response.setSuccess(false);
                response.setMessage("You are not authorized to update feedback for this report!");
                response.setStatusCode(403);
                return response;
            }

            report.setFeedback(feedback);
            report.setFeedbackTime(new Date());
            reportRepo.save(report);


            Account student = report.getAccount();
            Notification notification = Notification.builder()
                    .type("Feedback Notification")
                    .content("Mentor '" + optionalMentor.get().getFullname() + "' has given feedback on your report: '" + report.getTitle() + "'")
                    .notificationStatus(NotificationStatus.UNREAD)
                    .account(student)
                    .report(report)
                    .build();
            notificationRepo.save(notification);

            response.setMessage("Feedback updated successfully");
            response.setResult(reportMapper.toReportResponse(report));
            response.setSuccess(true);
            response.setStatusCode(200);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error updating feedback: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    public Specification<Mentor> getMentorSpecification(MentorRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getExpertise() != null && !request.getExpertise().isEmpty()) {
                List<Predicate> expertisePredicates = request.getExpertise().stream()
                        .map(exp -> criteriaBuilder.like(root.get("expertise"), "%" + exp + "%"))
                        .toList();
                predicates.add(criteriaBuilder.or(expertisePredicates.toArray(new Predicate[0])));
            }

            if (request.getMinRating() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), request.getMinRating()));
            }

            if (request.getYear() != null || request.getStartTime() != null || request.getEndTime() != null) {
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<MentorAvailability> availabilityRoot = subquery.from(MentorAvailability.class);

                List<Predicate> subPredicates = new ArrayList<>();
                subPredicates.add(criteriaBuilder.equal(availabilityRoot.get("mentor"), root));

                if (request.getYear() != null) {
                    subPredicates.add(criteriaBuilder.equal(availabilityRoot.get("year"), request.getYear()));
                }

                if (request.getStartTime() != null && request.getEndTime() != null) {
                    subPredicates.add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(availabilityRoot.get("startTime"), request.getStartTime()),
                            criteriaBuilder.lessThanOrEqualTo(availabilityRoot.get("endTime"), request.getEndTime()),
                            criteriaBuilder.isFalse(availabilityRoot.get("isBooked"))
                    ));
                }

                subquery.select(availabilityRoot.get("mentor").get("id"))
                        .where(subPredicates.toArray(new Predicate[0]));

                predicates.add(criteriaBuilder.exists(subquery));
            }

            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
