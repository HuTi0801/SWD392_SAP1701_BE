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

    public MentorResponse toMentorResponse(Mentor mentor) {

        return MentorResponse.builder()
                .id(mentor.getId())
                .fullName(mentor.getAccount().getFullname())
                .email(mentor.getAccount().getEmail())
                .expertise(mentor.getExpertise())
                .rating(mentor.getRating())
                .availableTimes(
                        mentor.getMentorAvailabilityList().stream()
                                .map(this::formatAvailability)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private String formatAvailability(MentorAvailability availability) {
        return String.format("%s (Week %d, %s): %s - %s",
                availability.getTerm(), availability.getWeekNumber(),
                availability.getDayOfWeek(), availability.getStartTime(), availability.getEndTime());
    }

    public List<MentorResponse> toListMentorResponse(List<Mentor> mentors) {
        return mentors.stream().map(this::toMentorResponse).collect(Collectors.toList());
    }
}
