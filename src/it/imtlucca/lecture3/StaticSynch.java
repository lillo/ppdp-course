package it.imtlucca.lecture3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StaticSynch {
    static int counter;

    static synchronized void increment() throws InterruptedException {
        System.out.printf("Before sleep In increment: %d\n", counter);
        ++counter;
        Thread.sleep(1000);
        System.out.printf("After sleep in decrement: %d\n", counter);
    }

    static synchronized void decrement() throws InterruptedException {
        System.out.printf("Before sleep in decrement: %d\n", counter);
        --counter;
        Thread.sleep(1000);
        System.out.printf("After sleep in decrement: %d\n", counter);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i) {
                try {
                    increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i){
                try {
                    decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
