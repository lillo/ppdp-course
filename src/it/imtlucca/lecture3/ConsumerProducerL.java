package it.imtlucca.lecture3;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ConsumerProducerL {
    private final String[] buffer;
    private final int capacity;

    private int front;
    private int rear;
    private int count;

    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public ConsumerProducerL(int capacity) {
        super();

        this.capacity = capacity;

        buffer = new String[capacity];
    }

    public void deposit(String data) throws InterruptedException {
        lock.lock();

        try {
            while (count == capacity) {
                notFull.await();
            }

            buffer[rear] = data;
            rear = (rear + 1) % capacity;
            count++;

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String fetch() throws InterruptedException {
        System.out.println("");
        lock.lock();

        try {
            while (count == 0) {
                notEmpty.await();
            }

            String result = buffer[front];
            front = (front + 1) % capacity;
            count--;

            notFull.signal();

            return result;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConsumerProducerL buffer = new ConsumerProducerL(5);
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            for(int i=1; i <= 10; ++i) {
                try {
                    String datum = "Datum" + i;
                    System.out.println("Deposit: " + datum);
                    buffer.deposit(datum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(() -> {
            for(int i=1; i <= 10; ++i) {
                try {
                    String datum = buffer.fetch();
                    System.out.println("Fetch: " + datum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
