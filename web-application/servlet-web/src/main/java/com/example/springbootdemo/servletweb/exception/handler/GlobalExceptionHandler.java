package com.example.springbootdemo.servletweb.exception.handler;

import com.example.springbootdemo.servletweb.exception.ObjectNotFoundException;
import com.example.springbootdemo.servletweb.exception.RequestParamsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface GlobalExceptionHandler {

    Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e);

    Object handleObjectNotFoundException(ObjectNotFoundException e);

    Object handleRequestParamsException(RequestParamsException e);

}
