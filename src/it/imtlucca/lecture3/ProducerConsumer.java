package it.imtlucca.lecture3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {
    static class Buffer {
        int[] buffer;
        int size = 0;

        public Buffer(int capacity) {
            buffer = new int[capacity];
        }

        public synchronized int get() throws InterruptedException {
            while (size == 0) wait();
            int result = buffer[--size];
            notify();
            return result;
        }

        public synchronized void put(int n) throws InterruptedException {
            while (size == buffer.length) wait();
            buffer[size++] = n;
            notify();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        Buffer buf = new Buffer(5);
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() ->{   // Producer
            try{
                while(true){
                    int next = (int)(Math.random() * 10000);
                    buf.put(next);
                    System.out.println("[Producer] Put " + next);
                    Thread.sleep((int)(Math.random() * 500)); }
            } catch (InterruptedException e) {
                System.out.println("[Producer] Interrupted");
            }});

        executor.execute(() -> {  // Consumer
            try{
                while(true){
                    System.out.println("[Consumer] Got " + buf.get());
                    Thread.sleep((int)(Math.random() * 500)); }
            }catch (InterruptedException e){
                System.out.println("[Consumer] Interrupted");
            } });
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}

