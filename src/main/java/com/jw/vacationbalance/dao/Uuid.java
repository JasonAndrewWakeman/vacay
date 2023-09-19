package com.jw.vacationbalance.dao;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Pseudo "uniform" unique id generator simulating db auto increment
 */
@Component
public final class Uuid {

    private static final AtomicLong counter = new AtomicLong(1);

    public long nextId() {
        return counter.getAndIncrement();
    }
}