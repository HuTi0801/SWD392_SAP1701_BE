package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.ProjectMapper;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.ProjectRepo;
import com.example.sap1701_team1.fptmentorlink.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;

    //Get all project
    @Override
    public Response getAllProject() {
        Response response = new Response();
        try{
            var projectlist = projectRepo.findAll();

            if(projectlist.isEmpty()){
                response.setSuccess(false);
                response.setMessage("There are no projects!");
                response.setStatusCode(404);
                response.setResult(null);
            }else {
                response.setMessage("Get all project successfully!");
                response.setResult(projectMapper.toListProjectResponse(projectlist));
                response.setSuccess(true);
                response.setStatusCode(200);
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setResult(null);
        }
        return response;
    }
}
