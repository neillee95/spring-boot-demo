package com.example.springbootdemo.reactivegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class ReactiveGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveGatewayApplication.class, args);
    }

}
