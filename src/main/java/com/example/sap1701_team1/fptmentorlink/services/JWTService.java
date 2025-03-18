package com.example.sap1701_team1.fptmentorlink.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean checkTokenIsValid(String token);
    void invalidateToken(String token);
}
