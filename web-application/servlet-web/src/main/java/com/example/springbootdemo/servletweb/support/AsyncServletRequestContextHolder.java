package com.example.springbootdemo.servletweb.support;

import com.alibaba.ttl.TransmittableThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * Async support for {@link org.springframework.web.context.request.RequestContextHolder}.
 *
 * @author lee
 */
public final class AsyncServletRequestContextHolder {

    private static final ThreadLocal<HttpServletRequest> HTTP_SERVLET_REQUEST_THREAD_LOCAL =
            new TransmittableThreadLocal<>();

    public static void set(HttpServletRequest request) {
        HTTP_SERVLET_REQUEST_THREAD_LOCAL.set(request);
    }

    public static HttpServletRequest get() {
        return HTTP_SERVLET_REQUEST_THREAD_LOCAL.get();
    }

    public static void remove() {
        HTTP_SERVLET_REQUEST_THREAD_LOCAL.remove();
    }

}
