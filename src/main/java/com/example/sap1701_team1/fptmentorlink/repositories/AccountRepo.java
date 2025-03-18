package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.enums.Role;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    List<Account> findByRole(Role role);

    Optional<Account> findByUsername(String username);
}
