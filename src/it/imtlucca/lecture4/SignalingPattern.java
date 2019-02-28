package it.imtlucca.lecture4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignalingPattern {
    private Object commonObject = new Object();
    private Runnable task1 = () -> {
        try {
            System.out.println("Long task ...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (commonObject){
            commonObject.notify();
        }
    };

    private Runnable task2 = () -> {
        System.out.println("I'm waiting for complete my task...");
      synchronized (commonObject){
          try {
              commonObject.wait();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      System.out.println("I can complete my task now");
    };

    public void run(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(task2);
        executorService.submit(task1);
        executorService.shutdown();
    }

    public static void main(String[] args){
        SignalingPattern pattern = new SignalingPattern();
        pattern.run();
    }
}
