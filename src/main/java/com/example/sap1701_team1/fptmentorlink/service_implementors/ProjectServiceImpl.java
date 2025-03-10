package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.enums.NotificationStatus;
import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.mappers.ProjectMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Notification;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.NotificationRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ProjectRepo;
import com.example.sap1701_team1.fptmentorlink.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final NotificationRepo notificationRepo;

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

    //Get a project by id
    @Override
    public Response getProjectById(Integer id) {
        Response response = new Response();
        try{
            Optional<Project> project = projectRepo.findById(id);
            if(project.isEmpty()){
                response.setSuccess(false);
                response.setMessage("There are no project!");
                response.setStatusCode(404);
                response.setResult(null);
            }else{
                response.setMessage("Get a project successfully!");
                response.setResult(projectMapper.toProjectResponse(project.get()));
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

    @Override
    public Response searchProject(String projectName, ProjectStatus status) {
        Response response = new Response();
        try {
            Specification<Project> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (projectName != null && !projectName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("topic")),
                            "%" + projectName.toLowerCase() + "%"
                    ));
                }

                if (status != null) {
                    predicates.add(criteriaBuilder.equal(root.get("projectStatus"), status));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            // 🛠 FIX lỗi Sort bằng cách kiểm tra có spec không, nếu null thì chỉ dùng Sort
            List<Project> projects = projectRepo.findAll(spec, Sort.by(Sort.Direction.ASC, "topic"));

            if (projects.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("No projects found!");
                response.setStatusCode(404);
                response.setResult(null);
            } else {
                response.setMessage("Projects retrieved successfully!");
                response.setResult(projectMapper.toListProjectResponse(projects)); // ✅ Đảm bảo trả về List<ProjectResponse>
                response.setSuccess(true);
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Error retrieving projects: " + e.getMessage());
            response.setResult(null);
        }
        return response;
    }

    //Approve or reject project
    @Override
    public Response updateStatusProjectById(Integer id, ProjectStatus status, String rejectionReason) {
        Response response = new Response();
        try {
            // Kiểm tra trạng thái hợp lệ (chỉ cho phép APPROVED hoặc REJECTED)
            if (status != ProjectStatus.APPROVED && status != ProjectStatus.REJECTED) {
                response.setSuccess(false);
                response.setMessage("Invalid status! Only APPROVED or REJECTED are allowed.");
                response.setStatusCode(400);
                return response;
            }

            // Tìm Project theo ID
            Optional<Project> optionalProject = projectRepo.findById(id);
            if (optionalProject.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Project not found!");
                response.setStatusCode(404);
                return response;
            }

            Project project = optionalProject.get();

            // Không cho phép cập nhật nếu trạng thái hiện tại đã là APPROVED hoặc REJECTED
            if (project.getProjectStatus() == ProjectStatus.APPROVED || project.getProjectStatus() == ProjectStatus.REJECTED) {
                response.setSuccess(false);
                response.setMessage("Cannot change status once it has been APPROVED or REJECTED.");
                response.setStatusCode(400);
                return response;
            }

            // Nếu trạng thái là REJECTED thì phải có lý do từ người dùng
            if (status == ProjectStatus.REJECTED && (rejectionReason == null || rejectionReason.trim().isEmpty())) {
                response.setSuccess(false);
                response.setMessage("Rejection reason is required when rejecting a project.");
                response.setStatusCode(400);
                return response;
            }

            // Cập nhật trạng thái mới cho Project
            project.setProjectStatus(status);

            // Nếu trạng thái là REJECTED, lưu lý do từ chối
            if (status == ProjectStatus.REJECTED) {
                project.setRejectionReason(rejectionReason); // Cần đảm bảo Project có trường `rejectionReason`
            }

            projectRepo.save(project);

            // Lấy Group từ Project (nếu có)
            Group group = project.getGroup();

            // Nội dung thông báo
            String notificationContent = "Project '" + project.getTopic() + "' has been " + status.name().toLowerCase();
            if (status == ProjectStatus.REJECTED) {
                notificationContent += ". Reason: " + rejectionReason;
            }

            // Tạo thông báo mới trong Notification
            Notification notification = Notification.builder()
                    .type("Project Status Update")
                    .content(notificationContent)
                    .notificationStatus(NotificationStatus.UNREAD) // Mặc định là chưa đọc
                    .project(project) // Gán project liên quan
                    .group(group)
                    .build();

            notificationRepo.save(notification); // Lưu Notification vào DB

            // Trả về Response thành công
            response.setSuccess(true);
            response.setMessage("Project status updated successfully!");
            response.setStatusCode(200);
            response.setResult(projectMapper.toProjectResponse(project)); // Trả về ProjectResponse
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error updating project status: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
