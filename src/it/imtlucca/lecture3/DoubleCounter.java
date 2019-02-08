package it.imtlucca.lecture3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DoubleCounter {
    private long c1 = 0;
    private long c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized (lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized (lock2) {
            c2++;
        }
    }

    public long value1(){
        return c1;
    }

    public long value2(){
        return c2;
    }

    public static void main(String[] args) throws InterruptedException {
        DoubleCounter counter = new DoubleCounter();
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i) {
                System.out.printf("The value of the counter: %d\n", counter.value1());
                counter.inc1();
            }
        });

        executor.execute(() -> {
            for(int i=1; i <= 5; ++i){
                System.out.printf("The value of the counter: %d\n", counter.value2());
                counter.inc2();
            }
        });

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}

