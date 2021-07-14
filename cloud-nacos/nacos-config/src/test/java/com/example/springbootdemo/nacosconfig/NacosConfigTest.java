package com.example.springbootdemo.nacosconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest
class NacosConfigTest {

    @Value("${spring.cloud.nacos.server-addr}")
    private String nacosServerAddr;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${spring.cloud.nacos.config.file-extension}")
    private String fileExtension;

    @Value("${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String group;

    @Test
    void refreshConfigTest() {
        String random = UUID.randomUUID().toString();
        String content = "cloud-config=" + random;

        String nacosUrl = "/nacos/v1/cs/configs?dataId={application}{active}.{extension}&group={group}&content={content}";

        Boolean success = WebClient.create("http://" + nacosServerAddr)
            .post()
            .uri(nacosUrl, applicationName, active(), fileExtension, group, content)
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();
        Assert.isTrue(success != null && success, "Change failed.");

        String configUrl = "/config";
        // delay 2 seconds.
        String result = Mono.delay(Duration.ofSeconds(2))
            .flatMap(v -> WebClient.create("http://127.0.0.1:9000")
                .get()
                .uri(configUrl)
                .retrieve()
                .bodyToMono(String.class)
            )
            .block();
        Assert.isTrue(Objects.equals(result, random), "Not equals. Result: [" + result + "]");
    }

    private String active() {
        if (activeProfile == null || "".equals(activeProfile)) {
            return "";
        }
        return "-" + activeProfile;
    }

}
