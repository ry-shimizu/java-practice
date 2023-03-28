package com.example.demo.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AsyncRequest {
    @NotNull
    private String message;

    private int activeNum;

}
