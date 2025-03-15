package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MentorRepo extends JpaRepository<Mentor, Integer>, JpaSpecificationExecutor<Mentor> {
}
