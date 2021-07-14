package com.example.springbootdemo.servletweb.interceptor;

import com.example.springbootdemo.servletweb.support.AsyncServletRequestContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHolderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        AsyncServletRequestContextHolder.set(request);
        return super.preHandle(request, response, handler);
    }

}
