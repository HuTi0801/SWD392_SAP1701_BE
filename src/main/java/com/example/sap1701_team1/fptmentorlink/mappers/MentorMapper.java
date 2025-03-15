package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import com.example.sap1701_team1.fptmentorlink.models.response_models.MentorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MentorMapper {
    public MentorResponse toResponse(Mentor mentor) {
        if (mentor == null || mentor.getAccount() == null) return null;

        return MentorResponse.builder()
                .id(mentor.getId())
                .fullName(mentor.getAccount().getFullname())
                .email(mentor.getAccount().getEmail())
                .expertise(mentor.getExpertise())
                .rating(mentor.getRating())
                .build();
    }

    public List<MentorResponse> toResponseList(List<Mentor> mentors) {
        return mentors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
