package com.example.sap1701_team1.fptmentorlink.mappers;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
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

    //lấy từ bảng account
    public LecturerResponse toResponseFromAccount(Account account) {
        if (account == null) return null;

        return LecturerResponse.builder()
                .id(account.getId())
                .fullName(account.getFullname())
                .email(account.getEmail())
                .academicRank("N/A") // Vì Account không có rank
                .departmentName("N/A") // Vì Account không có department
                .build();
    }

    public List<LecturerResponse> toResponseListFromAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(this::toResponseFromAccount)
                .collect(Collectors.toList());
    }
}
