package com.example.springbootdemo.nacosconsumer;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provider", fallbackFactory = NacosProviderFeign.Factory.class)
public interface NacosProviderFeign {

    @GetMapping("/open/echo/{message}")
    String echo(@PathVariable("message") String message);

    @GetMapping("/user")
    String user();

    class Impl implements NacosProviderFeign {

        private final static Logger LOGGER = LoggerFactory.getLogger(Impl.class);

        Throwable throwable;

        Impl(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public String echo(String message) {
            LOGGER.error("echo fallback", throwable);
            return null;
        }

        @Override
        public String user() {
            LOGGER.error("user fallback", throwable);
            return null;
        }
    }

    @Component
    class Factory implements FallbackFactory<NacosProviderFeign> {

        @Override
        public NacosProviderFeign create(Throwable throwable) {
            return new Impl(throwable);
        }

    }

}
