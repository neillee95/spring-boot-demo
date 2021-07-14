package com.example.springbootdemo.servletweb.exception.handler;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class BasicErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes =
                super.getErrorAttributes(webRequest, includeStackTrace);
        Integer status = (Integer) errorAttributes.get("status");
        if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            errorAttributes.remove("error");
            errorAttributes.remove("message");
        }
        return errorAttributes;
    }

}
