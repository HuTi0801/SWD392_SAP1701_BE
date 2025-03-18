package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.Response;
import com.example.sap1701_team1.fptmentorlink.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        Response response = accountService.login(username, password);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    //Bearer <token>
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('student:read') or hasAuthority('lecture:read') or hasAuthority('mentor:create') or hasAuthority('admin:read')")
    public ResponseEntity<Response> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            Response response = Response.builder()
                    .result(null)
                    .isSuccess(false)
                    .message("Missing or empty Authorization header")
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Response response = accountService.logout(token);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
