package com.example.demo.Request;

import com.example.demo.Validate.DateTimeFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OriginalValidateRequest {

    @NotNull
    @DateTimeFormat
    private String time;
}
