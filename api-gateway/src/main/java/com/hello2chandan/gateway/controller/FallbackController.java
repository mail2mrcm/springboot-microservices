package com.hello2chandan.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class FallbackController {

    @RequestMapping(value = "/systemFailure", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> getSystemFailure() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Please try after sometime");
    }
}
