package com.example.demo.Service;

import com.example.demo.Exception.ChildExampleException;
import com.example.demo.Exception.ExampleException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OriginalExceptionService {

    public ResponseEntity<Void> originalException(int id) throws ExampleException{
        if (id > 10) {
            throw new ChildExampleException("設定されたIDの値が大きすぎます", id);
        } else {

        }
        return ResponseEntity.ok().build();
    }
}
