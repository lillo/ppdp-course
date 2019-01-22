package it.imtlucca.lecture1;

public class MatrixMultiplication implements Runnable {
    private double A[][];
    private double B[][];
    private double C[][];
    private int i;

    public MatrixMultiplication(double A[][], double B[][], double C[][], int i){
        this.A = A;
        this.B = B;
        this.C = C;
        this.i = i;
    }

    public void run(){
        for(int j=0; j < C.length; j++){
            C[i][j] = 0.0;
            for(int k=0; k < C.length; ++k)
                C[i][j] = C[i][j] + A[i][k] * B[k][j];
        }
    }

    public static void main(String[] args){
        double A [][] = {
                {10.0, 11.0, 12.0, 14.0},
                {1.0, 2.0, 3.0, 5.0},
                {7.0, 88.0, 90.0, 100.0},
                {4.0, 3.0, 2.0, 1.0}
        };

        double B [] [] = {
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 1.0, 0.0, 1.0},
                {0.0, 0.0, 0.0, 1.0}
        };

        double C [][] = new double[A.length][A.length];

        Thread[] workers = new Thread[A.length];

        for(int i = 0; i < A.length; ++i){
            Thread t = new Thread(new MatrixMultiplication(A, B, C, i));
            t.start();
            workers[i] = t;
        }

        for(Thread t : workers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i < C.length; ++i) {
            for (int j = 0; j < C[i].length; ++j)
                System.out.printf("%f ", C[i][j]);
            System.out.println();
        }
    }
}
