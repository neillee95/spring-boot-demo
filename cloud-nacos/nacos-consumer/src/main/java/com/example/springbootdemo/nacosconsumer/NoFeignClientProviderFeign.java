package com.example.springbootdemo.nacosconsumer;

import org.springframework.web.bind.annotation.GetMapping;

public interface NoFeignClientProviderFeign {

    @GetMapping("/user")
    String user();

}
