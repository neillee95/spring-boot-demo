package com.example.springbootdemo.servletweb.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@ConditionalOnProperty(value = "web.use-custom-response", havingValue = "true")
@ControllerAdvice
@RestControllerAdvice
public class CustomResponseAdvice implements ResponseBodyAdvice<Object> {

    public CustomResponseAdvice(@Autowired(required = false) ObjectMapper mapper) {
        if (!Objects.isNull(mapper)) {
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }
    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();
        return method != null &&
            !(method.isAnnotationPresent(NoResponse.class) ||
                method.getDeclaringClass().isAnnotationPresent(NoResponse.class));
    }

    @Override
    public Object beforeBodyWrite(Object resultBody,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (resultBody instanceof Response) {
            return resultBody;
        }
        return Response.success(resultBody);
    }

    @Bean
    public WebMvcConfigurer webConfiguration() {
        return new WebConfiguration();
    }

    static class WebConfiguration implements WebMvcConfigurer {

        /**
         * Configure message converters, to resolve the problem
         * <block>
         * class com.example.springbootdemo.servletweb.http.Response cannot be cast to class java.lang.String.
         * </block>
         */
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(0, new MappingJackson2HttpMessageConverter());
        }

    }

}
