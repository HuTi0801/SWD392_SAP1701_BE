package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.request_models.GroupRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.GroupService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @PostMapping("/create")
    public Response createGroup(
            @Parameter(required = false) @RequestBody GroupRequest groupRequest) {
        return groupService.createGroup(groupRequest);
    }

    @PatchMapping("/add-member")
    public Response addMemberToGroup(@RequestParam Integer groupId,
                                     @RequestParam String memberUserCode,
                                     @RequestParam String requesterUserCode) {
        return groupService.addMemberToGroup(groupId, memberUserCode, requesterUserCode);
    }

    @PatchMapping("/remove-member")
    public Response removeMemberFromGroup(@RequestParam Integer groupId,
                                          @RequestParam String memberUserCode,
                                          @RequestParam String requesterUserCode,
                                          @RequestParam String newLeaderUserCode) {
        return groupService.removeMemberFromGroup(groupId, memberUserCode, requesterUserCode, newLeaderUserCode);
    }
}
