package it.imtlucca.lecture1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger counter;

    public AtomicCounter(int initializator){

        counter = new AtomicInteger(initializator);
    }

    public int getValue(){
        return counter.get();
    }

    public void increment(){
        while(true){ // T1 n ; T2 n = n + 1
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if(counter.compareAndSet(existingValue, newValue))
                return;
        }
    }

    public void increment1(){
        counter.incrementAndGet();
    }
}
