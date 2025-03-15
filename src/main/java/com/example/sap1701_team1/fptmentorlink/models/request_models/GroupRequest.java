package com.example.sap1701_team1.fptmentorlink.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequest {
    private String name;
    private String leaderUserCode;
    private List<String> memberUserCodes;
}
