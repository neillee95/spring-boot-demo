package com.example.springbootdemo.servletweb.util;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public interface IdGenerator {

    AtomicLong COUNTER = new AtomicLong(0);

    static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ROOT);
    }

    static String sequenceId() {
        return String.valueOf(COUNTER.incrementAndGet());
    }

}
