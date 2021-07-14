package com.example.springbootdemo.servletweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "cors")
@ConstructorBinding
public class CorsProperty {

    private Boolean enabled = false;

    private String pathPattern = "/**";

    private String[] origins = new String[]{"*"};

    private String[] methods = new String[]{"*"};

    private String[] headers = new String[]{"*"};

    private Boolean allowCredentials = false;

    @DurationUnit(ChronoUnit.HOURS)
    private Duration maxAge = Duration.ofHours(2);

    public CorsProperty() {}

    public CorsProperty(Boolean enabled,
                        String pathPattern,
                        String[] origins,
                        String[] methods,
                        String[] headers,
                        Boolean allowCredentials,
                        Duration maxAge) {
        this.enabled = enabled;
        this.pathPattern = pathPattern;
        this.origins = origins;
        this.methods = methods;
        this.headers = headers;
        this.allowCredentials = allowCredentials;
        this.maxAge = maxAge;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public String[] getOrigins() {
        return origins;
    }

    public void setOrigins(String[] origins) {
        this.origins = origins;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public Boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Duration getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Duration maxAge) {
        this.maxAge = maxAge;
    }

}
