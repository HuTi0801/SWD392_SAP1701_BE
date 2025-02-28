package com.example.sap1701_team1.fptmentorlink.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static com.example.sap1701_team1.fptmentorlink.enums.Permission.*;


@RequiredArgsConstructor
public enum Role {
    GUEST(Collections.emptySet()),

    LEADER(
        Set.of(
            LEADER_CREATE_GROUP,
            LEADER_MANAGE_MEMBERS,
            LEADER_PROPOSE_TOPIC,
            MEMBER_READ_GROUP,
            MEMBER_UPDATE_PROFILE
        )
    ),
    MEMBER(
        Set.of(
            MEMBER_READ_GROUP,
            MEMBER_UPDATE_PROFILE
        )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    ),
    LECTURE(
            Set.of(
                    LECTURE_CREATE,
                    LECTURE_READ,
                    LECTURE_UPDATE,
                    LECTURE_DELETE
            )
    ),
    MENTOR(
            Set.of(
                    MENTOR_CREATE,
                    MENTOR_READ,
                    MENTOR_UPDATE,
                    MENTOR_DELETE
            )
    );
    @Getter
    private final Set<Permission> permissions;
}
