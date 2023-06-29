package com.example.demo.Exception;

public class ChildExampleException extends ExampleException{
    public ChildExampleException(String message, int id, Throwable e) {
        super(message, id);
        e.printStackTrace();
    }
}
