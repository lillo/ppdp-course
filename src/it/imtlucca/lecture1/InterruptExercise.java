package it.imtlucca.lecture1;

public class InterruptExercise implements Runnable{
    @Override
    public void run() {
        System.out.println("T1 started!");
        long currentTime = System.currentTimeMillis();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted. ElapsedTime: " + (System.currentTimeMillis() - currentTime));
            return;
        }
        System.out.println("Normal termination");
    }

    public static void main(String[] args){
        Thread t1 = new Thread(new InterruptExercise());
        t1.start();
        System.out.println("T1 created!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I sends interrupt to T1");
        t1.interrupt();
        System.out.println("Termination");
    }
}
