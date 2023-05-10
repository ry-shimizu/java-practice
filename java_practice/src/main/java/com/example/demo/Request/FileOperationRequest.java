package com.example.demo.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class FileOperationRequest {

    @NotNull
    private String input;

    private String output;

    // テストのため
    // 文字列でリクエストされるが、受け取り時にLocalDateTimeに変換する
    // 求める形式形式以外でリクエストされるとエラー返却
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
}
