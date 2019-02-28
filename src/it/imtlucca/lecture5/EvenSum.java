package it.imtlucca.lecture5;

import java.util.stream.LongStream;

public class EvenSum {
    static long serialEvenSum(long threshold){
        return LongStream.range(0, threshold)
                        .filter(i -> i % 2 == 0)
                        .sum();
    }

    static long parallelEvenSum(long threshold){
        return LongStream.range(0, threshold)
                .parallel()
                .filter(i -> i % 2 == 0)
                .sum();
    }

    public static void main(String[] args){
        int threadshold = Integer.parseInt(args[0]);
        long currentTime = System.currentTimeMillis();
        System.out.printf("Serial version - result: %d - time %d\n",
                serialEvenSum(threadshold), System.currentTimeMillis() - currentTime);
        currentTime = System.currentTimeMillis();
        System.out.printf("Parallel version - result: %d - time %d\n",
                parallelEvenSum(threadshold), System.currentTimeMillis() - currentTime);

    }
}
