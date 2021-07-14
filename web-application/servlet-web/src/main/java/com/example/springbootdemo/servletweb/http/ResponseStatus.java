package com.example.springbootdemo.servletweb.http;

public enum ResponseStatus {

    /**
     * success
     */
    SUCCESS(10000),
    /**
     * invalid params
     */
    INVALID_PARAMS(20000),
    /**
     * not found
     */
    NOT_FOUND(21000),
    // ...
    /**
     * server error
     */
    ERROR(50000)
    ;

    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    int code() {
        return this.code;
    }

    public static boolean isSuccessStatus(ResponseStatus status) {
        return status.code == SUCCESS.code;
    }

}
