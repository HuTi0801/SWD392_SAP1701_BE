package com.example.sap1701_team1.fptmentorlink.service_implementors;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Account;
import com.example.sap1701_team1.fptmentorlink.models.response_models.AccountResponse;
import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.repositories.AccountRepo;
import com.example.sap1701_team1.fptmentorlink.services.AccountService;
import com.example.sap1701_team1.fptmentorlink.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AccountRepo accountRepo;
    @Override
    public Response login(String username, String password) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            Account account = accountRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return Response.builder()
                    .result(new AccountResponse(token, account.getUsername(), account.getAuthorities().toString(), account.getId(), account.getUserCode()))
                    .isSuccess(true)
                    .message("Login successful")
                    .statusCode(200)
                    .build();
        } catch (AuthenticationException e) {
            return Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("User not found")
                    .statusCode(401)
                    .build();
        }
    }
    @Override
    public Response logout(String token) {
        try {
            if (jwtService.checkTokenIsValid(token)) {
                jwtService.invalidateToken(token);
                return Response.builder()
                        .result(null)
                        .isSuccess(true)
                        .message("Logout successful")
                        .statusCode(200)
                        .build();
            } else {
                return Response.builder()
                        .result(null)
                        .isSuccess(false)
                        .message("Invalid token")
                        .statusCode(400)
                        .build();
            }
        } catch (Exception e) {
            return Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("Logout failed")
                    .statusCode(500)
                    .build();
        }
    }
}
