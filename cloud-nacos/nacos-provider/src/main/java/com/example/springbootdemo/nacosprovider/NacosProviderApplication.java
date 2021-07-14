package com.example.springbootdemo.nacosprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routers() {
        return route()
            .GET("/open/now", request ->
                ok().bodyValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("Now is yyyy-MM-dd HH:mm:ss")))
            )
            .GET("/user", request ->
                ok().body(ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .map(authentication -> ((UserDetails) authentication.getPrincipal()).getUsername()),
                    String.class)
            )
            .build();
    }

}
