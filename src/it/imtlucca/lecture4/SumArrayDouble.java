package it.imtlucca.lecture4;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumArrayDouble extends RecursiveTask<Double> {
    private ArrayList<Double> array;
    private int start;
    private int end;
    static private final int LIMIT = 1000;

    SumArrayDouble(ArrayList<Double> array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }


    @Override
    protected Double compute() {
        if((end - start) > LIMIT){
            SumArrayDouble left = new SumArrayDouble(array, start, (end - start) / 2);
            SumArrayDouble right = new SumArrayDouble(array, (end - start) / 2, end);
            //right.fork();
            left.fork();

            double r1 = right.compute();
            double r2 = left.join();
            return r1 + r2;
        }
        return serialComputation();
    }

    private double serialComputation(){
        double res = 0.0;
        for(int i=start; i < end; ++i)
            res += array.get(i);
        return res;
    }

    public static void main(String args[]){
        ArrayList<Double> array = new ArrayList<>(2000);
        for(int i=0; i < 100000; i++)
            array.add(Math.random() * 1000.0 + 1.0);
        SumArrayDouble task = new SumArrayDouble(array,0, array.size());
        ForkJoinPool pool = ForkJoinPool.commonPool();
        double result = pool.invoke(task);
        System.out.println("Result: " + result);
    }
}
