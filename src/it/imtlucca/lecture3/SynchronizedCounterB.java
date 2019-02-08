package it.imtlucca.lecture3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedCounterB {
    private int c = 0;


    public void increment() {
        synchronized (this) {c++;}
    }

    public void decrement() {
        synchronized (this){c--;}
    }

    public int value() {
        return c;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounterB counter = new SynchronizedCounterB();
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i) {
                System.out.printf("The value of the counter: %d\n", counter.value());
                counter.increment();
            }
        });

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i){
                System.out.printf("The value of the counter: %d\n", counter.value());
                counter.decrement();
            }
        });

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
