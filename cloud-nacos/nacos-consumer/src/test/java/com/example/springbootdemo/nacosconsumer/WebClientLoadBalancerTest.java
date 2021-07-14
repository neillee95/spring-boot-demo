package com.example.springbootdemo.nacosconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@SpringBootTest
public class WebClientLoadBalancerTest {

    @Test
    public void restTemplateConsumeTest(@Autowired RestTemplate restTemplate) {
        String message = UUID.randomUUID().toString();
        String result = restTemplate.getForObject("http://nacos-provider/open/echo/" + message, String.class);
        Assert.isTrue(result != null && result.endsWith(message), "Not match, result [" + result + "]");
    }

    @Test
    public void webCientConsumeTest(@Autowired @Qualifier("nacosProviderWebClient") WebClient client) {
        String message = UUID.randomUUID().toString();
        String result = client
                .get()
                .uri("/open/echo/{message}", message)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Assert.isTrue(result != null && result.endsWith(message), "Not match, result [" + result + "]");
    }

}
