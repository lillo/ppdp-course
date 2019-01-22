package it.imtlucca.lecture1;

public class ThreadPriorities {
    static class Task1 implements Runnable{
        public void run(){
            for(int i=1; i<=4;++i){
                Thread t = Thread.currentThread();
                System.out.printf("%s has priority %d\n", Thread.currentThread().getName(), Thread.currentThread().getPriority());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Task2 implements Runnable{
        public void run() {
            Thread t4 = new Thread(new Task1(), "T4");
            t4.start();
            (new Task1()).run();
        }
    }

    public static void main(String[] args){
        Thread t1 = new Thread(new Task1(), "T1"),
                t2 = new Thread(new Task1(), "T2"),
                t3 = new Thread(new Task2(), "T3");
        t1.setPriority(8);
        t2.setPriority(2);
        t3.setPriority(7);
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.setPriority(3);
        System.out.printf("The main thread has priority: %d", t1.getPriority());
    }
}
