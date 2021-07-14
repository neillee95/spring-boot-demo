package com.example.springbootdemo.servletweb.http;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface NoResponse {
}
