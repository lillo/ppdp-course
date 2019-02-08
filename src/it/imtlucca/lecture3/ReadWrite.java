package it.imtlucca.lecture3;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ReadWrite {
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> { // writer
            lock.writeLock().lock();
            try {
                Thread.sleep(1);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        });
        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        executor.shutdown();
    }
}
