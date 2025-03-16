package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.MentorAvailability;
import com.example.sap1701_team1.fptmentorlink.models.response_models.MentorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MentorMapper {
    public List<MentorResponse> toListMentorResponse(List<Mentor> mentors) {
        return mentors.stream().map(this::toMentorResponse).collect(Collectors.toList());
    }

    public MentorResponse toMentorResponse(Mentor mentor) {

        return MentorResponse.builder()
                .id(mentor.getId())
                .fullName(mentor.getAccount().getFullname())
                .email(mentor.getAccount().getEmail())
                .expertise(mentor.getExpertise())
                .rating(mentor.getRating())
                .year(getYearsFromMentorAvailability(mentor))
                .availableTimes(mentor.getMentorAvailabilityList().stream()
                        .flatMap(availability -> availability.getAvailabilitySlots().stream()
                                .filter(slot -> !slot.isBooked())
                                .map(slot -> slot.getStartTime() + " - " + slot.getEndTime()))
                        .collect(Collectors.toList()))
                .build();
    }

    private int getYearsFromMentorAvailability(Mentor mentor) {
        return mentor.getMentorAvailabilityList().stream()
                .map(MentorAvailability::getYear)
                .findFirst()
                .orElse(0);
    }
}
