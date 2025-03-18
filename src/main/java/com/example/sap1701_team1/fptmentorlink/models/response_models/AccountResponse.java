package com.example.sap1701_team1.fptmentorlink.models.response_models;

import com.example.sap1701_team1.fptmentorlink.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String token;
    private String username;
    private String role;
    private Integer accountId;
    private String userCode;
}
