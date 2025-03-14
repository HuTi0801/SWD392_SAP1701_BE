package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.response_models.GroupResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GroupMapper {
    public GroupResponse toGroupResponse(Group group) {
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .leaderName(group.getLeader().getAccount().getFullname())
                .members(group.getStudentList().stream()
                        .map(student -> GroupResponse.MemberInfo.builder()
                                .userCode(student.getAccount().getUserCode())
                                .fullName(student.getAccount().getFullname())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
