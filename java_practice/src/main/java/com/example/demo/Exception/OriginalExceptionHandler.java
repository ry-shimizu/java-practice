package com.example.demo.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

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

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Object> restClientExceptionHandler(RestClientException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CommonErrorResponse(e.getMessage(), 0), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        return  ResponseEntity.ok("バリデーションエラー");
    }
}
