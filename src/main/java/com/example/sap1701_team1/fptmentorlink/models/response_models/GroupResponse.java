package com.example.sap1701_team1.fptmentorlink.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private Integer id;
    private String name;
    private String leaderName;
    private List<MemberInfo> members;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberInfo {
        private String userCode;
        private String fullName;
    }
}
