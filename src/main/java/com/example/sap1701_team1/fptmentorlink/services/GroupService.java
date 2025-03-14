package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.request_models.GroupRequest;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;

public interface GroupService {
    Response createGroup(GroupRequest groupRequest);
}
