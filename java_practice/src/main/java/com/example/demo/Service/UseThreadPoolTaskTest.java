package com.example.demo.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UseThreadPoolTaskTest {

    @Async("Thread1")
    public void useThreadPoolTaskAsync() {
        log.info("非同期start→" + Thread.currentThread().getId() + "、" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("割り込み", e);
        }
        log.info("非同期finish→" + Thread.currentThread().getId() + "、"+ Thread.currentThread().getName());
    }
}
