package com.example.springbootdemo.nacosconsumer;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

import java.util.Objects;

@SpringBootTest
@Import(FeignClientsConfiguration.class)
class NoFeignClientProviderFeignTest {

    @Autowired
    private Client client;
    @Autowired
    private Encoder encoder;
    @Autowired
    private Decoder decoder;
    @Autowired
    private Contract contract;

    @Test
    void userTest() {
        NoFeignClientProviderFeign feign;
        feign = Feign.builder()
            .client(client).encoder(encoder).decoder(decoder).contract(contract)
            .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
            .target(NoFeignClientProviderFeign.class, "http://nacos-provider/");

        String result = feign.user();
        Assert.isTrue(Objects.equals(result, "user"), "[ " + result + " ] is not match [ user ].");
    }

}