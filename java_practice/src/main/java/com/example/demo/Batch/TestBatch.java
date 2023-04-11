package com.example.demo.Batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class TestBatch {

    final static LocalDateTime now = LocalDateTime.now();

    @Scheduled(fixedDelay = 2000)
    public void fixedDelay() {
        log.info(String.valueOf(ChronoUnit.MILLIS.between(now, LocalDateTime.now())));
    }

    @Scheduled(fixedRate = 2000)
    public void fixedRate() {
        log.info(String.valueOf(ChronoUnit.MILLIS.between(now, LocalDateTime.now())));
    }

    @Scheduled(initialDelay = 3000, fixedRate = 2000)
    public void initialDelay() {
        log.info(String.valueOf(ChronoUnit.MILLIS.between(now, LocalDateTime.now())));
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo" )
    public void cron() {
        log.info(String.valueOf(LocalDateTime.now()));
    }
}
