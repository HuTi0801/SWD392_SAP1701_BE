package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import com.example.sap1701_team1.fptmentorlink.models.response_models.StudentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentMapper {
    public StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .fullname(student.getAccount() != null ? student.getAccount().getFullname() : null)
                .email(student.getAccount() != null ? student.getAccount().getEmail() : null)
                .jpa(student.getJpa())
                .majorName(student.getMajor() != null ? student.getMajor().getName() : null)
                .groupId(student.getGroup() != null ? student.getGroup().getId() : null)
                .groupName(student.getGroup() != null ? student.getGroup().getName() : null)
                .build();
    }

    public List<StudentResponse> toStudentResponseList(List<Student> students) {
        return students.stream()
                .map(this::toStudentResponse)
                .collect(Collectors.toList());
    }
}
