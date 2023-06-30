package com.example.demo.Controller;

import com.example.demo.Request.OriginalValidateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class OriginalValidateController {

    @PostMapping("/originalValidate")
    public ResponseEntity<LocalDateTime> originalValidate(@RequestBody @Validated OriginalValidateRequest request) {

        return ResponseEntity.ok(LocalDateTime.parse(request.getTime(), DateTimeFormatter.ISO_DATE_TIME));
    }
}
