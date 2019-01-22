package it.imtlucca.lecture1;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class SimpleProcess {
    public static void main(String args[]) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("ls -ll");
        BufferedReader d
                = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = d.readLine();
        while(line != null){
            System.out.printf("%s\n",line);
            line = d.readLine();
        }
    }
}
