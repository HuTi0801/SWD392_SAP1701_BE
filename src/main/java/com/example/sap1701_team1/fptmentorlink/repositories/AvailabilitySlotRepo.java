package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Date;

public interface AvailabilitySlotRepo extends JpaRepository<AvailabilitySlot, Integer> {
    Optional<AvailabilitySlot> findByMentorAvailabilityMentorIdAndStartTimeAndEndTime(Integer mentorId, Date startTime, Date endTime);
}
