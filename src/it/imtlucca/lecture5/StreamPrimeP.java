package it.imtlucca.lecture5;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamPrimeP {
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

        String numbers =
                IntStream.range(low, high)
                        .parallel()
                        .filter(StreamPrimeP::isPrime)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(","));
        System.out.printf("%s\n", numbers);
    }
}
