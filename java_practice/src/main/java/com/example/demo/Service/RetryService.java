package com.example.demo.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class RetryService {

    private static int count = 0;
    @Retryable(retryFor= Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    public ResponseEntity<String> retryTest() {
        log.info("試行回数：" + (count + 1) + "、時間：" + LocalDateTime.now());
        throw new RuntimeException();
    }

    @Recover
    public ResponseEntity<String> recover(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.ok("リトライ後のリカバリー");
    }
}
