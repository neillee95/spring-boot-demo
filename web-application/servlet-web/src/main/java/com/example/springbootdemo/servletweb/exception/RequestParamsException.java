package com.example.springbootdemo.servletweb.exception;

public class RequestParamsException extends RuntimeException {

    public RequestParamsException() {}

    public RequestParamsException(String message) {
        super(message);
    }

}
