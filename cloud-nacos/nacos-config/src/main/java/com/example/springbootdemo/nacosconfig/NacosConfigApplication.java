package com.example.springbootdemo.nacosconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RefreshScope
@RestController
public class NacosConfigApplication {

    @Value("${cloud-config:}")
    private String cloudConfig;

    @GetMapping("/config")
    public Mono<String> config() {
        return Mono.just(cloudConfig);
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

}
