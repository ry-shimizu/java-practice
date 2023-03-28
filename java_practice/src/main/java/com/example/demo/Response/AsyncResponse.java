package com.example.demo.Response;

import com.example.demo.Service.AsyncService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AsyncResponse {
    // ステータス
    private String status;

    // 非同期処理結果
    private boolean result;

    // 実行時刻
    private LocalDateTime time;

    public AsyncResponse (AsyncService.AsyncDto dto) {
        this.status = dto.getStatus();
        this.result = dto.isResult();
        this.time = dto.getTime();
    }

}
