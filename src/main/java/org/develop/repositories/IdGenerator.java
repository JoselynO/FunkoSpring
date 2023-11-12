package org.develop.repositories;


import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private final AtomicInteger counter = new AtomicInteger(0);

    int getAndIncrement(){
        counter.getAndIncrement();
        return counter.get();
    }
}