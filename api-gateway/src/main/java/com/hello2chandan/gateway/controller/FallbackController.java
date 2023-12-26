package com.hello2chandan.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/systemFailure")
    public ResponseEntity<String> getSystemFailure() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Please try after sometime");
    }

}
