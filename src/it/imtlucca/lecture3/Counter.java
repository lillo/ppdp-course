package it.imtlucca.lecture3;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
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
