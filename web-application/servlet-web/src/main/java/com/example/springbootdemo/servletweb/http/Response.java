package com.example.springbootdemo.servletweb.http;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public final class Response<T> implements Serializable {

    private final int code;

    private final String message;

    private final T data;

    private final long timestamp = System.currentTimeMillis();

    private static final String EMPTY_MESSAGE = "";

    private Response(@NonNull ResponseStatus status, String message, T data) {
        this.code = status.code();
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 请求成功
     */
    public static <T> Response<T> success() {
        return success(EMPTY_MESSAGE, null);
    }

    /**
     * 请求成功并返回数据
     */
    public static <T> Response<T> success(T data) {
        return success(EMPTY_MESSAGE, data);
    }

    /**
     * 请求成功并返回消息和数据
     */
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(ResponseStatus.SUCCESS, message, data);
    }

    /**
     * 请求处理失败并返回消息
     */
    public static <T> Response<T> fail(@NonNull ResponseStatus status) {
        return fail(status, EMPTY_MESSAGE, null);
    }

    /**
     * 请求处理失败并返回消息
     */
    public static <T> Response<T> fail(ResponseStatus status, String message) {
        return fail(status, message, null);
    }

    /**
     * 请求处理失败并返回消息和数据
     */
    public static <T> Response<T> fail(ResponseStatus status, String message, T data) {
        if (ResponseStatus.isSuccessStatus(status)) {
            throw new IllegalArgumentException("It's not success response.");
        }
        return new Response<>(status, message, data);
    }

}
