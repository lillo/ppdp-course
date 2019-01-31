package it.imtlucca.lecture2;

import java.util.concurrent.Executor;

public class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable task) {
        task.run();
    }
}
