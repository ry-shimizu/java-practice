package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetryService {

    private static int count = 0;

    private final RetryTemplate retryTemplate;

    @Retryable(retryFor= Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public ResponseEntity<String> retryTest() {
        count = count + 1;
        log.info("試行回数：" + count + "、時間：" + LocalDateTime.now());
        throw new RuntimeException("処理失敗してます");
    }

    @Recover
    public ResponseEntity<String> recover(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.ok("リトライ後のリカバリー");
    }

    @Async("Thread1")
    public void retryTemplate() {
        retryTemplate.execute(
                retryContext -> {
            count = count + 1;
            log.info("試行回数：" + count + "、時間：" + LocalDateTime.now());
            throw new RuntimeException("処理の失敗でエラー返却");
        }, retryContext -> {log.info("リカバリーします" + retryContext.getLastThrowable());
                    throw new RuntimeException("リカバリーします");});
    }
}
