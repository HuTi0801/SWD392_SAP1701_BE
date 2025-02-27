package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
}
