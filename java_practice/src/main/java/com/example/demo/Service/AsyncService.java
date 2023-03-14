package com.example.demo.Service;

import com.example.demo.Request.AsyncRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService {

    public AsyncDto normalAsync(AsyncRequest request, LocalDateTime now) {
        return AsyncDto.builder()
                .status("success")
                .result(true)
                .build();
    }


    @Data
    @Builder
    public static class AsyncDto {
        public String status;
        public  boolean result;
    }
}
