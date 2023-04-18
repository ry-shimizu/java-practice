package com.example.demo.controller;

import com.example.demo.Request.FileOperationRequest;
import com.example.demo.Response.FileOperationResponse;
import com.example.demo.Service.FileOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileOperationController {
    final FileOperationService fileOperationService;

    @PostMapping("/fileReader")
    public ResponseEntity<String> fileReader() {
        fileOperationService.fileReader();
        return ResponseEntity.ok("処理完了");
    }

    @PostMapping("/fileWriter")
    public ResponseEntity<String> fileWriter(@Validated @RequestBody FileOperationRequest request) {
        fileOperationService.fileWriter(request);
        return ResponseEntity.ok("処理完了");
    }

    @PostMapping("/bufferedReader")
    public ResponseEntity<FileOperationResponse> bufferedReader() {
        return ResponseEntity.ok(new FileOperationResponse(fileOperationService.bufferedReader()));
    }

    @PostMapping("/bufferedWriter")
    public ResponseEntity<String> bufferedWriter(@Validated @RequestBody FileOperationRequest request) {
        fileOperationService.bufferedWriter(request);
        return ResponseEntity.ok("処理完了");
    }

    @PostMapping("/zipStream")
    public ResponseEntity<String> zipStream(@Validated @RequestBody FileOperationRequest request) {
        fileOperationService.zipStream(request);
        return ResponseEntity.ok("処理完了");
    }

}
