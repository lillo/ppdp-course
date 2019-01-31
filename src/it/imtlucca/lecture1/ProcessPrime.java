package it.imtlucca.lecture1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessPrime {
    public static void main(String[] args) throws IOException{
        if(args.length != 3){
            System.out.println("You must pass: <number of processes> <low> <high>");
            return;
        }

        int threads = Integer.parseInt(args[0]);
        int low = Integer.parseInt(args[1]);
        low = Math.max(low, 2);
        int high = Integer.parseInt(args[2]);
        high = Math.max(low, high);
        int isize = (high - low + 1) / threads;
        List<Process> ts = new ArrayList<>(threads);
        long startTime = System.currentTimeMillis();

        for(int i= low; i <= high; i += isize + 1){
            Process p = null;
            p = makeProcess(i, Math.min(i + isize, high));
            ts.add(p);
        }

        System.out.println("*********\n\n");
        for(Process p : ts) {
            BufferedReader d
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = d.readLine();
            while (line != null) {
                System.out.printf("%s\n", line);
                line = d.readLine();
            }
        }
        System.out.println("*********\n\n");
        System.out.printf("Total time: %d\n", System.currentTimeMillis() - startTime);
    }

    private static Process makeProcess(int low, int upper) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true).command("java", "it.imtlucca.lecture1.ComputePrimeE", "1", "" + low, "" + upper);
        return builder.start();
    }
}
