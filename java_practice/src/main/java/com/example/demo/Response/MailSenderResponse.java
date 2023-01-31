package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailSenderResponse {

    // ステータス
    private String status;

    // メール送信結果
    private boolean result;
}
