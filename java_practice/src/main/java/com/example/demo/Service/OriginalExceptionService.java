package com.example.demo.Service;

import com.example.demo.Exception.CommonErrorResponse;
import com.example.demo.Exception.ExampleException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OriginalExceptionService {

    public ResponseEntity<Void> originalException(int id) {
        if (id > 10) {
            throw new ExampleException("設定されたIDの値が大きすぎます", id);
        } else {

        }
        return ResponseEntity.ok().build();
    }
}
