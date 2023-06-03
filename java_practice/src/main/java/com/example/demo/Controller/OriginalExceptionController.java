package com.example.demo.Controller;

import com.example.demo.Request.AsyncRequest;
import com.example.demo.Service.OriginalExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OriginalExceptionController {
    private final OriginalExceptionService originalExceptionService;
    @PostMapping("/originalException")
    public ResponseEntity<Void> originalException(@Validated @RequestBody AsyncRequest request) {
        return originalExceptionService.originalException(request.getActiveNum());
    }
}
