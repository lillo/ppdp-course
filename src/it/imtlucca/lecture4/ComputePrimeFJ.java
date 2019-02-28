package it.imtlucca.lecture4;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class ComputePrimeFJ extends RecursiveTask<List<Integer>> {
    private int low;
    private int upper;
    private static int THRESHOLD = 100;
    private List<Integer> primes;

    public ComputePrimeFJ(int low, int upper){
        this.low = low;
        this.upper = upper;
        primes = new ArrayList<>();
    }

    @Override
    protected List<Integer> compute() {
        int isize = upper - low;

        if(isize < THRESHOLD){
            sequentialComputation();
        }else{
            ComputePrimeFJ subTask1 = new ComputePrimeFJ(low, isize),
                           subTask2 = new ComputePrimeFJ(isize + 1, upper);
            ForkJoinTask.invokeAll(subTask1, subTask2);
            primes.addAll(subTask1.primes);
            primes.addAll(subTask2.primes);
        }
        return primes;
    }

    private void sequentialComputation() {
        for(int i=low; i <= upper; ++i)
            if(isPrime(i))
                primes.add(i);
    }


    private static boolean isPrime(int n){

        for(int i = 2; i <= Math.sqrt(n); i++)
            if(n % i == 0)
                return false;
        return true;
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("You must pass: <low> <high>");
            return;
        }

        int low = Integer.parseInt(args[0]);
        low = Math.max(low, 2);
        int high = Integer.parseInt(args[1]);
        high = Math.max(low, high);

        ComputePrimeFJ task = new ComputePrimeFJ(low, high);
        List<Integer> primes = ForkJoinPool.commonPool().invoke(task);
        String numbers = primes.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.printf("%s\n", numbers);
    }
}
