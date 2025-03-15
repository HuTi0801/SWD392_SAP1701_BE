package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.MentorMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Mentor;
import com.example.sap1701_team1.fptmentorlink.models.response_models.MentorResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.MentorRepo;
import com.example.sap1701_team1.fptmentorlink.services.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorRepo mentorRepo;
    private final MentorMapper mentorMapper;

    @Override
    public Response getAllMentors() {
        Response response = new Response();
        try {
            List<Mentor> mentorList = mentorRepo.findAll();

            if (mentorList.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("There are no mentors!");
                response.setStatusCode(404);
            } else {
                List<MentorResponse> mentorResponses = mentorMapper.toResponseList(mentorList);
                response.setMessage("Get all mentors successfully!");
                response.setResult(mentorResponses);
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving mentors: " + e.getMessage());
        }
        return response;
    }
}
