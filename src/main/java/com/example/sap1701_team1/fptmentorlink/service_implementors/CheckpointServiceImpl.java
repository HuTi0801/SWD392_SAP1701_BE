package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.CheckpointMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Checkpoint;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.CheckpointRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ProjectRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.StudentRepo;
import com.example.sap1701_team1.fptmentorlink.services.CheckpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckpointServiceImpl implements CheckpointService {

    private final StudentRepo studentRepo;
    private final CheckpointRepo checkpointRepo;
    private final CheckpointMapper checkpointMapper;
    private final ProjectRepo projectRepo;

    @Override
    public Response getCheckpointsForStudent(String studentId) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isEmpty()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Student not found!")
                    .statusCode(404)
                    .build();
        }

        Student student = studentOpt.get();
        if (student.getGroup() == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Student is not in any group!")
                    .statusCode(400)
                    .build();
        }

        Project project = projectRepo.findByGroupId(student.getGroup().getId()).orElse(null);
        if (project == null) {
            return Response.builder()
                    .isSuccess(false)
                    .message("No project assigned to this group!")
                    .statusCode(400)
                    .build();
        }

        List<Checkpoint> checkpoints = checkpointRepo.findByProjectId(project.getId());
        return Response.builder()
                .isSuccess(true)
                .message("Checkpoints retrieved successfully!")
                .statusCode(200)
                .result(checkpointMapper.toCheckpointResponseList(checkpoints))
                .build();
    }
}
