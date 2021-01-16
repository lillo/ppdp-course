package it.imtlucca.lecture1;

import java.util.*;
import java.util.stream.Collectors;

public class ComputePrime extends Thread {
    private int low;
    private int upper;
    private List<Integer> primes;

    public ComputePrime(int low, int upper){
        this.low = low;
        this.upper = upper;
        primes = new ArrayList<>();
    }

    private static boolean isPrime(int n){

        for(int i = 2; i <= Math.sqrt(n); i++)
            if(n % i == 0)
                return false;
        return true;
    }

    public List<Integer> getPrimes(){
        return primes;
    }

    public void run(){
        for(int i=low; i <= upper; ++i) {
            if (isPrime(i))
                primes.add(i);
        }
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
        List<ComputePrime> ts = new ArrayList<>(threads);
        long startTime = System.currentTimeMillis();


        for(int i= low; i <= high; i += isize + 1){
            ComputePrime t = new ComputePrime(i, Math.min(i + isize, high));
            t.start();
            ts.add(t);
        }

        for(ComputePrime t : ts){
            try {
                t.join();
                List<Integer> ints = t.getPrimes();
                String numbers = ints.stream().map(Object::toString).collect(Collectors.joining(","));
                System.out.printf("Thread %d: %s\n", t.getId(), numbers);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.printf("Completion time: %d\n", elapsedTime);
    }
}
