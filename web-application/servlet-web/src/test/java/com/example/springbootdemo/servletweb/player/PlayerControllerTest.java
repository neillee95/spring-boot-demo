package com.example.springbootdemo.servletweb.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String baseUrl;

    @BeforeAll
    public static void init(@Value("${web.use-custom-response:false}") boolean useCustomResponse,
                            @LocalServerPort int port) {
        if (useCustomResponse) {
            throw new RuntimeException("set 'web.use-custom-response=false'");
        }
        baseUrl = "http://127.0.0.1:" + port;
    }

    @BeforeEach
    public void create() {
        Player player = new Player();
        player.setName("Tom");
        player.setAge((short) 25);

        ResponseEntity<Player> responseEntity =
            restTemplate.exchange(
                URI.create(createUri("/players")),
                HttpMethod.POST,
                new HttpEntity<>(player),
                Player.class
            );

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getId());
    }

    @Test
    void deleteTest() {
        ResponseEntity<Player> responseEntity =
            restTemplate.exchange(
                URI.create(createUri("/players/2")),
                HttpMethod.DELETE,
                null,
                Player.class
            );
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateTest() {
        Player player = new Player();
        player.setId("1");
        player.setAge((short) 24);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            URI.create(createUri("/players")),
            HttpMethod.PUT,
            new HttpEntity<>(player),
            String.class
        );
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void findByIdTest() {
        Player returnValue = restTemplate.getForObject(baseUrl + "/players/{1}", Player.class, "1");
        Assertions.assertNotNull(returnValue);
    }

    @Test
    void findAllTest() {
        ExecutorService executor = Executors.newCachedThreadPool();
        int count = 10;
        CountDownLatch counter = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int order = i;
            executor.execute(() -> {
                Player player = new Player();
                player.setName("Tom - " + order);
                player.setAge((short) (25 + order));
                restTemplate.postForEntity(baseUrl + "/players", player, Player.class);
                counter.countDown();
            });
        }
        try {
            counter.await();

            Collection<?> players = restTemplate.getForObject(baseUrl + "/players", Collection.class);
            Assertions.assertNotNull(players);
            Assertions.assertEquals(players.size(), count + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private String createUri(String url) {
        return baseUrl + url;
    }

}
