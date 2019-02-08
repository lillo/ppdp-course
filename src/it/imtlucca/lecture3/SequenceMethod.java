package it.imtlucca.lecture3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SequenceMethod {
    private ArrayList<Integer> elements;

    public synchronized Integer getRandom(){
        System.out.printf("Inside getRandom()\n");
        Random rnd = new Random();
        int n = rnd.nextInt(elements.size());
        return elements.remove(n);
    }

    public synchronized void incrRandom(){
        System.out.printf("Inside incrRandom\n");
        Integer num = getRandom();
        System.out.printf("In incrRandom: %d\n", num);
        num++;
        elements.add(num);
        System.out.printf("Exiting incrRandom");
    }

    public SequenceMethod(Integer ... nums){
        elements = new ArrayList<>();
        elements.addAll(Arrays.asList(nums));
    }

    public static void main(String[] args){
        ExecutorService executor = Executors.newCachedThreadPool();
        SequenceMethod seq = new SequenceMethod(1,2,3,4,5,6,7,8,9,10);

        for(int i=0;i<5;++i)
            executor.execute(() ->{
                seq.incrRandom();
            });
    }
}
