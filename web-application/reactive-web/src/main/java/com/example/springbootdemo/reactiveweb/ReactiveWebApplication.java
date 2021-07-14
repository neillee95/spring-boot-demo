package com.example.springbootdemo.reactiveweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class ReactiveWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return route()
            .GET("/", r -> ok().body(Mono.just("Welcome! From ")
                    .flatMap(s ->
                        ReactiveRequestContextHolder.getWebExchange()
                            .map(exchange -> s + exchange.getRequest().getPath().value() + ".")
                    ),
                String.class)
            )
            .build();
    }

}
