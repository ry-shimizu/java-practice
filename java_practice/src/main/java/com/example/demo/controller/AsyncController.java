package com.example.demo.controller;

import com.example.demo.Request.AsyncRequest;
import com.example.demo.Response.AsyncResponse;
import com.example.demo.Service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @PostMapping("/normalAsync")
    public AsyncResponse normalAsync(@RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.normalAsync(request, LocalDateTime.now()));
    }
}
