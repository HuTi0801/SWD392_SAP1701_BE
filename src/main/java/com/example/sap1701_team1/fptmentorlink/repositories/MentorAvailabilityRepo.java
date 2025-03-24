package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.MentorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface MentorAvailabilityRepo extends JpaRepository<MentorAvailability, Integer> {
    Optional<MentorAvailability> findByMentorAndStartTimeAndEndTimeAndIsBookedFalse(Mentor mentor, Date startTime, Date endTime);

}
