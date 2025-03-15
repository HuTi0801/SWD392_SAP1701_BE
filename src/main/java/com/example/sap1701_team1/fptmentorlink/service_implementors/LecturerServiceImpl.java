package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.LecturerMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Lecturer;
import com.example.sap1701_team1.fptmentorlink.models.response_models.LecturerResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.LecturerRepo;
import com.example.sap1701_team1.fptmentorlink.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepo lecturerRepo;
    private final LecturerMapper lecturerMapper;

    @Override
    public Response getAllLecturers() {
        Response response = new Response();
        try {
            List<Lecturer> lecturerList = lecturerRepo.findAll();

            if (lecturerList.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no lecturers!");
                response.setStatusCode(404);
            } else {
                List<LecturerResponse> lecturerResponses = lecturerMapper.toResponseList(lecturerList);
                response.setMessage("Get all lecturers successfully!");
                response.setResult(lecturerResponses);
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving lecturers: " + e.getMessage());
        }
        return response;
    }
}
