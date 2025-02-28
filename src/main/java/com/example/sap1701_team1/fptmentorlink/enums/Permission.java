package com.example.sap1701_team1.fptmentorlink.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    LEADER_CREATE_GROUP("leader:create_group"),
    LEADER_MANAGE_MEMBERS("leader:manage_members"),
    LEADER_PROPOSE_TOPIC("leader:propose_topic"),

    MEMBER_READ_GROUP("member:read_group"),
    MEMBER_UPDATE_PROFILE("member:update_profile"),

    LECTURE_CREATE("lecture:create"),
    LECTURE_READ("lecture:read"),
    LECTURE_UPDATE("lecture:update"),
    LECTURE_DELETE("lecture:delete"),

    MENTOR_CREATE("mentor:create"),
    MENTOR_READ("mentor:read"),
    MENTOR_UPDATE("mentor:update"),
    MENTOR_DELETE("mentor:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;
}
