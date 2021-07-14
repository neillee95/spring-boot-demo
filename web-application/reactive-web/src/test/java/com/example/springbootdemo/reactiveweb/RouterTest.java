package com.example.springbootdemo.reactiveweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouterTest {

    @Autowired
    private WebTestClient client;

    @Test
    void webTest() {
        client.get()
            .uri("/")
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(String.class)
            .consumeWith(it -> System.out.println(it.getResponseBody()));
    }

}
