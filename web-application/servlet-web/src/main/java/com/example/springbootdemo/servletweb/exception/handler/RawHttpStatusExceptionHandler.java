package com.example.springbootdemo.servletweb.exception.handler;

import com.example.springbootdemo.servletweb.exception.ObjectNotFoundException;
import com.example.springbootdemo.servletweb.exception.RequestParamsException;
import com.example.springbootdemo.servletweb.util.BindingUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ConditionalOnMissingBean(CustomResponseExceptionHandler.class)
@ControllerAdvice
@RestControllerAdvice
@ResponseBody
public class RawHttpStatusExceptionHandler implements GlobalExceptionHandler {

    @ExceptionHandler
    @Override
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = BindingUtil.errorMessage(e.getBindingResult());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    @Override
    public Object handleObjectNotFoundException(ObjectNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler
    @Override
    public Object handleRequestParamsException(RequestParamsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
