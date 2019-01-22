package it.imtlucca.lecture1;

public class SleepInterrupt implements Runnable{

    @Override
    public void run() {
        System.out.println("I sleep for 20 seconds");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            return;
        }
        System.out.println("Normal termination");
    }

    public static void main(String[] args)  {
        Thread t = new Thread(new SleepInterrupt());
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I interrupt the other thread");
        t.interrupt();
        System.out.println("Termination");
    }
}
