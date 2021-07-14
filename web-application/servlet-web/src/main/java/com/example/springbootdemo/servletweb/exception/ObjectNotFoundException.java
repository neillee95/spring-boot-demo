package com.example.springbootdemo.servletweb.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {}

    public ObjectNotFoundException(String message) {
        super(message);
    }

}
