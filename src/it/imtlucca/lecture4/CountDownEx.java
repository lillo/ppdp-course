package it.imtlucca.lecture4;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CountDownEx {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        final int nthreads = 4;
        CountDownLatch counter = new CountDownLatch(nthreads);
        ExecutorService executor = Executors.newFixedThreadPool(nthreads);
        IntStream.range(0, nthreads)
                .forEach(i ->
                        executor.submit(
                                () -> {
                                    System.out.printf("Starting task: %d\n", i);
                                    try {
                                        Thread.sleep(rand.nextInt(4000));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.printf("Finishing task: %d\n", i);
                                    counter.countDown();
                                }));
        counter.await();
        System.out.printf("Main thread: after all other threads");
        executor.shutdown();
    }
}
