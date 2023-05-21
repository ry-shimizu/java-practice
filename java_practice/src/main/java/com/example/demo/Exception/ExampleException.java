package com.example.demo.Exception;

public class ExampleException extends RuntimeException{

    public String message;

    public String id;

    public ExampleException(String message, String id) {
        super(id + "：" + message);
        this.message = message;
        this.id = id;
    }
}
