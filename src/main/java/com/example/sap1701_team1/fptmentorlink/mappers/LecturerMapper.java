package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Lecturer;
import com.example.sap1701_team1.fptmentorlink.models.response_models.LecturerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LecturerMapper {
    public LecturerResponse toResponse(Lecturer lecturer) {
        if (lecturer == null || lecturer.getAccount() == null) return null;

        return LecturerResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getAccount().getFullname())
                .email(lecturer.getAccount().getEmail())
                .academicRank(lecturer.getAccademicRank())
                .departmentName(lecturer.getDepartment() != null ? lecturer.getDepartment().getDepName() : "N/A")
                .build();
    }

    public List<LecturerResponse> toResponseList(List<Lecturer> lecturers) {
        return lecturers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
