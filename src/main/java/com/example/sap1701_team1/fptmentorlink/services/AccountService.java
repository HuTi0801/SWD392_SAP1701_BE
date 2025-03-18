package com.example.sap1701_team1.fptmentorlink.services;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import org.springframework.security.core.AuthenticationException;

public interface AccountService {
    Response login(String username, String password) throws AuthenticationException;

    Response logout(String token);
}
