package com.example.demo.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OriginalExceptionHandler {

    @ExceptionHandler(ExampleException.class)
    public ResponseEntity<Object> exampleExceptionHandler(ExampleException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonErrorResponse(e.getMessage(), e.getId()));
    }

    @ExceptionHandler(ChildExampleException.class)
    public ResponseEntity<Object> childExampleExceptionHandler(ChildExampleException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new CommonErrorResponse(e.getMessage(), e.getId()));
    }
}
