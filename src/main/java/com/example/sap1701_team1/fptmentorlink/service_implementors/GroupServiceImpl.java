package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.mappers.GroupMapper;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Project;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import com.example.sap1701_team1.fptmentorlink.models.request_models.GroupRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.GroupRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.ProjectRepo;
import com.example.sap1701_team1.fptmentorlink.repositories.StudentRepo;
import com.example.sap1701_team1.fptmentorlink.services.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepository;
    private final StudentRepo studentRepository;
    private final GroupMapper groupMapper;
    private final ProjectRepo projectRepository;

    @Override
    public Response createGroup(GroupRequest groupRequest) {
        if (groupRequest.getMemberUserCodes().size() < 3 || groupRequest.getMemberUserCodes().size() > 4) {
            return Response.builder()
                    .isSuccess(false)
                    .message("The group must have 4 to 5 members!")
                    .statusCode(400)
                    .build();
        }

        Optional<Student> optLeader = studentRepository.findLeaderByUserCode(groupRequest.getLeaderUserCode());
        if (optLeader.isEmpty()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Leader does not exist!")
                    .statusCode(404)
                    .build();
        }
        Student leader = optLeader.get();

        List<Student> members = studentRepository.findByAccountUserCodeIn(groupRequest.getMemberUserCodes());
        if (members.size() != groupRequest.getMemberUserCodes().size()) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Some members do not exist!")
                    .statusCode(404)
                    .build();
        }

        if (!members.contains(leader)) {
            members.add(leader);
        }

        if (members.stream().anyMatch(student -> student.getGroup() != null)) {
            return Response.builder()
                    .isSuccess(false)
                    .message("Some members are already in other groups!")
                    .statusCode(400)
                    .build();
        }

        String majorId = leader.getMajor().getId().toString();
        if (members.stream().anyMatch(student -> !student.getMajor().getId().toString().equals(majorId))) {
            return Response.builder()
                    .isSuccess(false)
                    .message("All members must have the same major!")
                    .statusCode(400)
                    .build();
        }

        Group group = Group.builder()
                .name(groupRequest.getName())
                .leader(leader)
                .studentList(members)
                .build();
        group = groupRepository.save(group);

        for (Student student : members) {
            student.setGroup(group);
            studentRepository.save(student);
        }

        studentRepository.updateGroupIdForStudent(leader.getId(), group.getId());

        return Response.builder()
                .isSuccess(true)
                .message("Group creation successful!")
                .statusCode(200)
                .result(groupMapper.toGroupResponse(group))
                .build();
    }

    @Override
    @Transactional
    public Response addMemberToGroup(Integer groupId, String memberUserCode, String requesterUserCode) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (optGroup.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("Group not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "GROUP_NOT_FOUND", "groupId", groupId))
                    .build();
        }
        Group group = optGroup.get();

        List<Student> members = studentRepository.findByGroupId(groupId);
        if (members.size() >= 5) {
            return Response.builder().isSuccess(false)
                    .message("Group already has 5 members!")
                    .statusCode(400)
                    .result(Map.of("errorType", "MAX_MEMBERS_REACHED", "groupId", groupId))
                    .build();
        }

        Optional<Project> optProject = projectRepository.findByGroupId(groupId);
        boolean hasProject = optProject.isPresent() && optProject.get().getProjectStatus() != null;

        Optional<Student> optRequester = studentRepository.findByAccountUserCode(requesterUserCode);
        if (optRequester.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("Requester not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "REQUESTER_NOT_FOUND", "requesterUserCode", requesterUserCode))
                    .build();
        }
        Student requester = optRequester.get();

        if (hasProject && !group.getLeader().equals(requester)) {
            return Response.builder().isSuccess(false)
                    .message("Only the leader can add members!")
                    .statusCode(403)
                    .result(Map.of("errorType", "PERMISSION_DENIED", "requesterUserCode", requesterUserCode))
                    .build();
        }

        Optional<Student> optNewMember = studentRepository.findByAccountUserCode(memberUserCode);
        if (optNewMember.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("New member not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "MEMBER_NOT_FOUND", "memberUserCode", memberUserCode))
                    .build();
        }
        Student newMember = optNewMember.get();

        if (members.contains(newMember)) {
            return Response.builder().isSuccess(false)
                    .message("Member is already in the group!")
                    .statusCode(400)
                    .result(Map.of("errorType", "DUPLICATE_MEMBER", "memberUserCode", memberUserCode))
                    .build();
        }

        if (newMember.getGroup() != null) {
            return Response.builder().isSuccess(false)
                    .message("Student is already in another group!")
                    .statusCode(400)
                    .result(Map.of("errorType", "ALREADY_IN_ANOTHER_GROUP", "memberUserCode", memberUserCode))
                    .build();
        }

        String groupMajor = members.get(0).getMajor().getId().toString();
        if (!newMember.getMajor().getId().toString().equals(groupMajor)) {
            return Response.builder().isSuccess(false)
                    .message("Member does not belong to the same major as the group!")
                    .statusCode(400)
                    .result(Map.of("errorType", "DIFFERENT_MAJOR", "memberUserCode", memberUserCode, "expectedMajor", groupMajor, "actualMajor", newMember.getMajor().getId().toString()))
                    .build();
        }

        newMember.setGroup(group);
        studentRepository.save(newMember);

        return Response.builder().isSuccess(true)
                .message("Member added successfully!")
                .statusCode(200)
                .build();
    }


    @Override
    @Transactional
    public Response removeMemberFromGroup(Integer groupId, String memberUserCode, String requesterUserCode, String newLeaderUserCode) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (optGroup.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("Group not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "GROUP_NOT_FOUND", "groupId", groupId))
                    .build();
        }
        Group group = optGroup.get();

        List<Student> members = studentRepository.findByGroupId(groupId);
        if (members.size() <= 4) {
            return Response.builder().isSuccess(false)
                    .message("Cannot remove member! Group must have at least 4 members.")
                    .statusCode(400)
                    .result(Map.of("errorType", "MIN_MEMBERS_REQUIRED", "groupId", groupId))
                    .build();
        }

        Optional<Project> optProject = projectRepository.findByGroupId(groupId);
        boolean hasProject = optProject.isPresent() && optProject.get().getProjectStatus() != null;

        Optional<Student> optRequester = studentRepository.findByAccountUserCode(requesterUserCode);
        if (optRequester.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("Requester not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "REQUESTER_NOT_FOUND", "requesterUserCode", requesterUserCode))
                    .build();
        }
        Student requester = optRequester.get();

        Optional<Student> optMember = studentRepository.findByAccountUserCode(memberUserCode);
        if (optMember.isEmpty()) {
            return Response.builder().isSuccess(false)
                    .message("Member not found!")
                    .statusCode(404)
                    .result(Map.of("errorType", "MEMBER_NOT_FOUND", "memberUserCode", memberUserCode))
                    .build();
        }
        Student member = optMember.get();

        if (hasProject && !group.getLeader().equals(requester)) {
            return Response.builder().isSuccess(false)
                    .message("Only the leader can remove members!")
                    .statusCode(403)
                    .result(Map.of("errorType", "PERMISSION_DENIED", "requesterUserCode", requesterUserCode))
                    .build();
        }

        if (group.getLeader().equals(member)) {
            if (newLeaderUserCode == null) {
                return Response.builder().isSuccess(false)
                        .message("Leader must provide a new leader before leaving!")
                        .statusCode(400)
                        .result(Map.of("errorType", "NEW_LEADER_REQUIRED", "leaderUserCode", memberUserCode))
                        .build();
            }
            Optional<Student> optNewLeader = studentRepository.findByAccountUserCode(newLeaderUserCode);
            if (optNewLeader.isEmpty() || !members.contains(optNewLeader.get())) {
                return Response.builder().isSuccess(false)
                        .message("New leader is not in the group!")
                        .statusCode(400)
                        .result(Map.of("errorType", "INVALID_NEW_LEADER", "newLeaderUserCode", newLeaderUserCode))
                        .build();
            }
            group.setLeader(optNewLeader.get());
            groupRepository.save(group);
        }

        member.setGroup(null);
        studentRepository.save(member);

        return Response.builder().isSuccess(true)
                .message("Member removed successfully!")
                .statusCode(200)
                .build();
    }
}
