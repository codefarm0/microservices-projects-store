package com.codefarm.idmaster.service.generator;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class SequentialIdGenerator {

    private final AtomicLong counter = new AtomicLong();

    public String generateSequentialId() {
        return Long.toString(counter.incrementAndGet());
    }
}
