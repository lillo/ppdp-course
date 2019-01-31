package it.imtlucca.lecture2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ComputePrimeEA implements Callable<List<Integer>> {
    private int low;
    private int upper;

    public ComputePrimeEA(int low, int upper){
        this.low = low;
        this.upper = upper;
    }

    private static boolean isPrime(int n){

        for(int i = 2; i <= Math.sqrt(n); i++)
            if(n % i == 0)
                return false;
        return true;
    }

    public List<Integer> call(){
        List<Integer> primes = new ArrayList<>();
        for(int i=low; i <= upper; ++i)
            if(isPrime(i))
                primes.add(i);
        return primes;
    }

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("You must pass: <number of thread> <low> <high>");
            return;
        }

        int threads = Integer.parseInt(args[0]);
        int low = Integer.parseInt(args[1]);
        low = Math.max(low, 2);
        int high = Integer.parseInt(args[2]);
        high = Math.max(low, high);

        int isize = (high - low + 1) / threads;
        List<ComputePrimeEA> ts = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(threads);


        for(int i= low; i <= high; i += isize + 1){
            ComputePrimeEA task = new ComputePrimeEA(i, Math.min(i + isize, high));
            ts.add(task);
        }

        try{
            for(Future<List<Integer>> t : executor.invokeAll(ts)) {
                List<Integer> ints = t.get();
                String numbers = ints.stream().map(Object::toString).collect(Collectors.joining(","));
                System.out.printf("Result: %s\n", numbers);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.printf("Completion time: %d\n", elapsedTime);
    }
}
