package it.imtlucca.lecture4;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class BufferProdCons {
    private Exchanger<List<Integer>> exchanger = new Exchanger<>();
    private final int ITERATIONS = 10;
    private Runnable generator = () -> {
        List<Integer> buffer = new ArrayList<>();
        Random rnd = new Random();
        final int NUMBERS = 3;
        for(int i = 0; i < ITERATIONS; ++i){
            for(int j = 0; j < NUMBERS; ++j){
                buffer.add(rnd.nextInt(10));
            }
            String strBuffer = buffer.stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.printf("Generated: %s\n", strBuffer);
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable consumer = () -> {
        List<Integer> buffer = new ArrayList<>();
        for(int i=0; i< ITERATIONS; ++i){
            buffer.clear();
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int sum = buffer.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("Consumed: %d\n", sum);
        }
    };

    public void startAll(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(generator);
        executorService.submit(consumer);
        executorService.shutdown();
    }

    public static void main(String[] args){
        BufferProdCons prod = new BufferProdCons();
        prod.startAll();
    }
}
