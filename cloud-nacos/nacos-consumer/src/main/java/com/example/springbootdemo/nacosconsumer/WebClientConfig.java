package com.example.springbootdemo.nacosconsumer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("nacosProviderWebClient")
    public WebClient nacosProviderWebClient(ReactorLoadBalancer.Factory<ServiceInstance> factory) {
        return WebClient.builder()
                .baseUrl("http://nacos-provider")
                .filter(new ReactorLoadBalancerExchangeFilterFunction(factory))
                .build();
    }

}
