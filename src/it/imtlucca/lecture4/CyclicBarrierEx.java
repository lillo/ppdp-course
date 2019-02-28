package it.imtlucca.lecture4;

import java.util.*;
import java.util.concurrent.*;

public class CyclicBarrierEx {
    private CyclicBarrier cyclicBarrier;
    private List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
    private Random random = new Random();
    private int NUM_PARTIAL_RESULTS;
    private int NUM_WORKERS;

    private Runnable numberGenerator = () -> {
        String thisThreadName = Thread.currentThread().getName();
        List<Integer> partialResult = new ArrayList<>();
        for (int i = 0; i < NUM_PARTIAL_RESULTS; i++) {
            Integer num = random.nextInt(10);
            System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
            partialResult.add(num);
        }
        partialResults.add(partialResult);
        try {
            System.out.println(thisThreadName + " waiting for others to reach barrier.");
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    };

    private Runnable numberAggregator = () -> {
        String thisThreadName = Thread.currentThread().getName();
        System.out.println(thisThreadName + ": Computing final sum of " + NUM_WORKERS + " workers, having " + NUM_PARTIAL_RESULTS + " results each.");
        int sum = 0;
        for (List<Integer> threadResult : partialResults) {
            System.out.print("Adding ");
            for (Integer partialResult : threadResult) {
                System.out.print(partialResult + " ");
                sum += partialResult;
            }
            System.out.println();
        }
        System.out.println(Thread.currentThread().getName() + ": Final result = " + sum);
    };

    private void runSimulation(int numWorkers, int numberOfPartialResults) {
        NUM_PARTIAL_RESULTS = numberOfPartialResults;
        NUM_WORKERS = numWorkers;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS);
        cyclicBarrier = new CyclicBarrier(NUM_WORKERS, numberAggregator);
        System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS + " partial results each");
        for (int i = 0; i < NUM_WORKERS; i++) {
            executor.submit(numberGenerator);
        }
        executor.shutdown();
    }



    public static void main(String[] args) {
        CyclicBarrierEx play = new CyclicBarrierEx();
        play.runSimulation(5, 3);
    }
}
