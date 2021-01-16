package it.imtlucca.lecture1;

import java.io.*;
import java.security.*;
import java.util.*;

public class ComputeDigestWrong implements Runnable {

    private String filename;
    private String resultDigest;

    public ComputeDigestWrong(String filename){
        this.filename = filename;
    }

    @Override
    public void run(){
        try{
            resultDigest = null;
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while(din.read() != -1)
                ; // skip
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            for (byte b : digest) {
                result.append(String.format("%02X", b));
            }
            resultDigest = result.toString();
        }catch (IOException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public String getDigest(){

        return resultDigest;
    }


    public static void main(String[] args){
        List<Thread> ts = new ArrayList<>();
        long startTime = System.nanoTime();

        for(String f : args){
            ComputeDigestWrong comp = new ComputeDigestWrong(f);
            Thread t = new Thread(comp);
            t.start();  // Start the thread
            System.out.println(comp.getDigest());
            ts.add(t);
        }

        for(Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Time: %d ns\n", elapsedTime);
    }
}
