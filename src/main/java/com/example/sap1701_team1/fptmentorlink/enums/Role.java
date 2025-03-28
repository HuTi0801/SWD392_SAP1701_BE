package com.example.sap1701_team1.fptmentorlink.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.sap1701_team1.fptmentorlink.enums.Permission.*;


@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST(Collections.emptySet()),

    STUDENT(
        Set.of(
                STUDENT_CREATE,
                STUDENT_READ,
                STUDENT_UPDATE,
                STUDENT_DELETE
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
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var author = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());

        author.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return author;
    }
}
