package it.imtlucca.lecture4;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class RendezvousPattern {
    private Integer max1 = null;
    private Integer max2 = null;
    private Object commonObject1 = new Object();
    private Object commonObject2 = new Object();
    private Random rand = new Random();

    // Exercise: Write in a better way without code duplication
    private Runnable task1 = () -> {
        synchronized (commonObject1) {
            max1 = IntStream.range(0, 10)
                    .map(i -> rand.nextInt(100))
                    .max()
                    .orElse(Integer.MIN_VALUE);
            System.out.printf("Task1 produces %d\n", max1);
            commonObject1.notify();
        }
        synchronized (commonObject2){
            try {
                System.out.printf("Task1 start waiting\n");
                while(max2 == null)
                    commonObject2.wait();
                System.out.printf("Task1 consumes %d\n", max2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable task2 = () -> {
        synchronized (commonObject2) {
            max2 = IntStream.range(0, 10)
                    .map(i -> rand.nextInt(100))
                    .max()
                    .orElse(Integer.MIN_VALUE);
            System.out.printf("Task2 produces %d\n", max2);
            commonObject2.notify();
        }
        synchronized (commonObject1){
            try {
                System.out.printf("Task2 start waiting\n");
                while(max1 == null)
                    commonObject1.wait();
                System.out.printf("Task1 consumes %d\n", max1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void run (){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.shutdown();
    }

    public static void main(String[] args){
        RendezvousPattern pattern = new RendezvousPattern();
        pattern.run();
    }
}
