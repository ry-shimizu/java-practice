package com.example.demo.Controller;

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

    @PostMapping("/normalTemplate")
    public ResponseEntity<Void> normalTemplate() {
        retryService.normalTemplate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customTemplate")
    public ResponseEntity<Void> customTemplate() {
        retryService.customTemplate();
        return ResponseEntity.ok().build();
    }
}
