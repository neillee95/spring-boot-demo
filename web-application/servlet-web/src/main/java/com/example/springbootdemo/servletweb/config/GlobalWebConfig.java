package com.example.springbootdemo.servletweb.config;

import com.example.springbootdemo.servletweb.interceptor.RequestHolderInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(CorsProperty.class)
public class GlobalWebConfig implements WebMvcConfigurer {

    private final CorsProperty corsProperty;

    public GlobalWebConfig(CorsProperty corsProperty) {
        this.corsProperty = corsProperty;
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (corsProperty != null && corsProperty.isEnabled()) {
            registry.addMapping(corsProperty.getPathPattern())
                .allowedOrigins(corsProperty.getOrigins())
                .allowedMethods(corsProperty.getMethods())
                .allowedHeaders(corsProperty.getHeaders())
                .allowCredentials(corsProperty.isAllowCredentials())
                .maxAge(corsProperty.getMaxAge().getSeconds());
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHolderInterceptor());
    }

}
