package com.example.sap1701_team1.fptmentorlink.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    STUDENT_CREATE("student:create"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),

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

    private final String permission;
}
