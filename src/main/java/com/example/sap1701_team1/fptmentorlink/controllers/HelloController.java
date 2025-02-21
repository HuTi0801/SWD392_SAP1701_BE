package com.example.sap1701_team1.fptmentorlink.controllers;

import com.example.sap1701_team1.fptmentorlink.models.response_models.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
@RequiredArgsConstructor
public class HelloController {
    @GetMapping("/say")
    ResponseEntity<ResponseObject> sayHello(){
        return ResponseEntity
                .ok()
                .body(
                        ResponseObject
                                .builder()
                                .message("Hello World!")
                                .build()
                );
    }
}
