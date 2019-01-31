package it.imtlucca.lecture2;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable task) {
        Thread t = new Thread(task);
        t.start();
    }
}
