package com.example.springbootdemo.nacosconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignClientsConfiguration.class)
public class NacosConsumerApplication {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters();
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

}
