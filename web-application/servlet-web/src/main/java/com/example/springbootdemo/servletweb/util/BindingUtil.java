package com.example.springbootdemo.servletweb.util;

import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;

import java.util.StringJoiner;

public final class BindingUtil {

    private static final String DEFAULT_ERROR_MESSAGE_DELIMITER = ",";

    public static String errorMessage(@NonNull BindingResult result) {
        return errorMessage(result, DEFAULT_ERROR_MESSAGE_DELIMITER);
    }

    public static String errorMessage(@NonNull BindingResult result, String delimiter) {
        final StringJoiner sj = new StringJoiner(delimiter);
        result.getAllErrors().forEach(objectError -> sj.add(objectError.getDefaultMessage()));
        return sj.toString();
    }

}
