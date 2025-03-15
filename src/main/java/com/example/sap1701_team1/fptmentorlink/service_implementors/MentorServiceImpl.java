package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.MentorMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.MentorAvailability;
import com.example.sap1701_team1.fptmentorlink.models.request_models.MentorRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.MentorRepo;
import com.example.sap1701_team1.fptmentorlink.services.MentorService;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepo mentorRepo;
    private final MentorMapper mentorMapper;

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

    public Specification<Mentor> getMentorSpecification(MentorRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getExpertise() != null && !request.getExpertise().isEmpty()) {
                List<Predicate> expertisePredicates = request.getExpertise().stream()
                        .map(exp -> criteriaBuilder.like(root.get("expertise"), "%" + exp + "%")) // 🔥 Dùng LIKE để tìm từng giá trị trong List<String>
                        .toList();
                predicates.add(criteriaBuilder.or(expertisePredicates.toArray(new Predicate[0])));
            }

            if (request.getMinRating() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), request.getMinRating()));
            }

            if (request.getTerm() != null || request.getDayOfWeek() != null) {
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<MentorAvailability> availabilityRoot = subquery.from(MentorAvailability.class);

                List<Predicate> subPredicates = new ArrayList<>();
                subPredicates.add(criteriaBuilder.equal(availabilityRoot.get("mentor"), root)); // Join với Mentor

                if (request.getTerm() != null) {
                    subPredicates.add(criteriaBuilder.equal(availabilityRoot.get("term"), request.getTerm()));
                }
                if (request.getDayOfWeek() != null) {
                    subPredicates.add(criteriaBuilder.equal(availabilityRoot.get("dayOfWeek"), request.getDayOfWeek()));
                }

                subquery.select(availabilityRoot.get("mentor").get("id"))
                        .where(subPredicates.toArray(new Predicate[0]));

                predicates.add(criteriaBuilder.exists(subquery));
            }
            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
