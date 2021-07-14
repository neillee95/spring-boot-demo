package com.example.springbootdemo.nacosconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.UUID;

@SpringBootTest
public class NacosProviderFeignTest {

    @Autowired
    public NacosProviderFeign feign;

    @Test
    public void echoTest() {
        String message = UUID.randomUUID().toString();
        String result = feign.echo(message);
        Assert.isTrue(result.endsWith(message),
                String.format("Result [%s] is not match message [%s]", result, message));
    }

    @Test
    public void userTest() {
        String user = feign.user();
        Assert.isNull(user, "User is not null: " + user);
    }

}
