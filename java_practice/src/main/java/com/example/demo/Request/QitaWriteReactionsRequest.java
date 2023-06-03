package com.example.demo.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QitaWriteReactionsRequest {
    @NotBlank
    private String itemId;
}
