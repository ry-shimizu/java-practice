package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExampleException extends RuntimeException {

    private int id;

    public ExampleException(String message, int id) {
        super(message);
        this.id = id;
    }
}
