package com.example.demo.controller;

import com.example.demo.Request.AsyncRequest;
import com.example.demo.Response.AsyncResponse;
import com.example.demo.Service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @PostMapping("/normalAsync")
    public AsyncResponse normalAsync(@Validated @RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.normalAsync(request, LocalDateTime.now()));
    }

    @PostMapping("/useExecutors")
    public AsyncResponse useExecutors(@Validated @RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.useExecutors(request, LocalDateTime.now()));
    }

    @PostMapping("/useThreadPool")
    public AsyncResponse useThreadPool(@Validated @RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.useThreadPool(request, LocalDateTime.now()));
    }
}
