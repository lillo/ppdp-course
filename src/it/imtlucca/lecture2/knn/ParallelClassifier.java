package it.imtlucca.lecture2.knn;

import java.util.*;
import java.util.concurrent.*;

public class ParallelClassifier {
    private Sample[] dataset;
    private int k;
    private int nThreads;
    private ExecutorService executor;

    public ParallelClassifier(Sample[] dataset, int k, int threads){
        this.dataset = dataset;
        this.k = k;
        this.nThreads = threads;
        this.executor = Executors.newFixedThreadPool(nThreads);  // No a good choice
    }

    public String classify(Sample sample) throws InterruptedException {
        Distance[] distances = new Distance[dataset.length];   // This is not a good style
        CountDownLatch counter = new CountDownLatch(nThreads);

        int isize = dataset.length / nThreads;
        int sindex = 0, eindex = isize;

       for(int i = 0; i < nThreads; i++) {
           Runnable task = new DistanceTask(sindex, eindex, distances, dataset, sample, counter);
           sindex = isize;
           isize = (i < nThreads - 2 ? eindex += isize : dataset.length);
           executor.execute(task);
       }

       counter.await();

       Arrays.parallelSort(distances, (p1, p2) -> (int)(p1.value - p2.value));

       Map<String, Integer> results = new HashMap<>();
       for(int i=0; i < k; ++i){
           Sample s = dataset[distances[i].pos];
           String tag = s.getTag();
           results.merge(tag, 1, (a, b) -> a + b);
       }

        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void dispose(){
        executor.shutdown();
    }

    static class DistanceTask implements Runnable{
        private int start, end;
        private Distance[] distances;
        private Sample[] dataset;
        private Sample sample;
        private CountDownLatch counter;

        public DistanceTask(int sindex, int eindex, Distance[] dist, Sample[] dataset, Sample sample, CountDownLatch counter){
            this.start = sindex;
            this.end = eindex;
            this.distances = dist;
            this.dataset = dataset;
            this.sample = sample;
            this.counter = counter;
        }

        public void run(){
            for(int i=start; i < end; ++i)
                distances[i] = new Distance(Util.euclideanDistance(dataset[i], sample), i);
            counter.countDown();
        }
    }
}
