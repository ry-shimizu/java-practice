package com.example.demo.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FileOperationRequest {

    @NotNull
    private String input;

    private String output;
}
