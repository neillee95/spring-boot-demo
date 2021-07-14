package com.example.springbootdemo.servletweb.exception.handler;

import com.example.springbootdemo.servletweb.exception.ObjectNotFoundException;
import com.example.springbootdemo.servletweb.exception.RequestParamsException;
import com.example.springbootdemo.servletweb.http.Response;
import com.example.springbootdemo.servletweb.http.ResponseStatus;
import com.example.springbootdemo.servletweb.util.BindingUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ConditionalOnProperty(value = "web.use-custom-response", havingValue = "true")
@ControllerAdvice
@ResponseBody
public class CustomResponseExceptionHandler implements GlobalExceptionHandler {

    @ExceptionHandler
    @Override
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = BindingUtil.errorMessage(e.getBindingResult());
        return Response.fail(ResponseStatus.INVALID_PARAMS, errorMessage);
    }

    @ExceptionHandler
    @Override
    public Object handleObjectNotFoundException(ObjectNotFoundException e) {
        return Response.fail(ResponseStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    @Override
    public Object handleRequestParamsException(RequestParamsException e) {
        return Response.fail(ResponseStatus.INVALID_PARAMS, e.getMessage());
    }

}
