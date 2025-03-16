package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckpointRepo extends JpaRepository<Checkpoint, Integer> {
    List<Checkpoint> findByProjectId(Integer projectId);
}
