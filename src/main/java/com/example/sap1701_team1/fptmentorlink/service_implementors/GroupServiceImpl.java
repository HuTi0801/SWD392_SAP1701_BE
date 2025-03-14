package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.GroupMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import com.example.sap1701_team1.fptmentorlink.models.request_models.GroupRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.GroupRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.StudentRepo;
import com.example.sap1701_team1.fptmentorlink.services.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepository;
    private final StudentRepo studentRepository;
    private final GroupMapper groupMapper;

    @Override
    public Response createGroup(GroupRequest groupRequest) {
        if (groupRequest.getMemberUserCodes().size() < 3 || groupRequest.getMemberUserCodes().size() > 4) {
            return Response.builder()
                    .isSuccess(false)
                    .message("The group must have 4 to 5 members!")
                    .statusCode(400)
                    .build();
        }

        // ✅ Fetch Leader
        Optional<Student> optLeader = studentRepository.findLeaderByUserCode(groupRequest.getLeaderUserCode());
        if (optLeader.isEmpty()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Leader does not exist!")
                    .statusCode(404)
                    .build();
        }
        Student leader = optLeader.get();

        // ✅ Fetch Members
        List<Student> members = studentRepository.findByAccountUserCodeIn(groupRequest.getMemberUserCodes());
        if (members.size() != groupRequest.getMemberUserCodes().size()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Some members do not exist!")
                    .statusCode(404)
                    .build();
        }

        // ✅ Ensure Leader is in the Group
        if (!members.contains(leader)) {
            members.add(leader);
        }

        // ✅ Check if any members are already in a group
        if (members.stream().anyMatch(student -> student.getGroup() != null)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Some members are already in other groups!")
                    .statusCode(400)
                    .build();
        }

        // ✅ Ensure all members have the same major
        String majorId = leader.getMajor().getId().toString();
        if (members.stream().anyMatch(student -> !student.getMajor().getId().toString().equals(majorId))) {
            return Response.builder()
                    .isSuccess(false)
                    .message("All members must have the same major!")
                    .statusCode(400)
                    .build();
        }

        // ✅ Create and Save Group
        Group group = Group.builder()
                .name(groupRequest.getName())
                .leader(leader)
                .studentList(members)
                .build();
        group = groupRepository.save(group);

        // ✅ Update `group_id` cho tất cả thành viên trong nhóm
        for (Student student : members) {
            student.setGroup(group);
            studentRepository.save(student);
        }

        // ✅ Cập nhật `group_id` của Leader TRỰC TIẾP trong Database
        studentRepository.updateGroupIdForStudent(leader.getId(), group.getId());

        return Response.builder()
                .isSuccess(true)
                .message("Group creation successful!")
                .statusCode(200)
                .result(groupMapper.toGroupResponse(group))
                .build();
    }

}
