package com.example.demo.controller;

import com.example.demo.Service.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RetryController {
    private final RetryService retryService;

    @PostMapping("/retryTest")
    public ResponseEntity<String> retryTest() {
        return retryService.retryTest();
    }
}
