package com.example.demo.controller;

import com.example.demo.Request.AsyncRequest;
import com.example.demo.Response.AsyncResponse;
import com.example.demo.Service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/useScheduleThreadPool")
    public AsyncResponse useScheduleThreadPool(@Validated @RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.useScheduleThreadPool(request, LocalDateTime.now()));
    }

    @PostMapping("/useThreadPoolTask")
    public AsyncResponse useThreadPoolTask(@Validated @RequestBody AsyncRequest request) {
        return new AsyncResponse(asyncService.useThreadPoolTask(request, LocalDateTime.now()));
    }

    @PostMapping("/useThreadPoolTaskAsync")
    public ResponseEntity<String> useThreadPoolTaskAsync() {
        asyncService.useThreadPoolTaskAsync();
        asyncService.useThreadPoolTaskAsync();
        asyncService.useThreadPoolTaskAsync();
        return ResponseEntity.ok("処理終了");
    }

    @PostMapping("/useThreadPoolTaskAsyncTest")
    public ResponseEntity<String> useThreadPoolTaskAsyncTest() {
        asyncService.useThreadPoolTaskAsyncTest();
        return ResponseEntity.ok("処理終了");
    }

    @PostMapping("/useCompletableFuture")
    public ResponseEntity<String> useCompletableFuture() {
        asyncService.useCompletableFuture();
        return ResponseEntity.ok("処理終了");
    }
}
