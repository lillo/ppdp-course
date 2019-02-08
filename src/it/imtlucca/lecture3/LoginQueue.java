package it.imtlucca.lecture3;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class LoginQueue {
    private Semaphore semaphore;

    public LoginQueue(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    void logout() {
        semaphore.release();
    }

    int availableSlots() {
        return semaphore.availablePermits();
    }

    public static void main(String[] args){
        int slots = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueue loginQueue = new LoginQueue(slots);
        IntStream.range(0, slots)
                .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();

        System.out.println(loginQueue.availableSlots());
        System.out.println(loginQueue.tryLogin());
    }
}
