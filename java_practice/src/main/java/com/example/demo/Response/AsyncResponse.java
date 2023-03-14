package com.example.demo.Response;

import com.example.demo.Service.AsyncService;
import lombok.Data;
import lombok.Getter;

@Getter
public class AsyncResponse {
    // ステータス
    private String status;

    // 非同期処理結果
    private boolean result;

    public AsyncResponse (AsyncService.AsyncDto dto) {
        this.status = dto.getStatus();
        this.result = dto.isResult();
    }

}
