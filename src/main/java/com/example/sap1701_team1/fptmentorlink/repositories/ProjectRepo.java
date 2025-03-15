package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    Optional<Project> findByGroupId(Integer groupId);
}
