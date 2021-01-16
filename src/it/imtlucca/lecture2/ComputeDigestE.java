package it.imtlucca.lecture2;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComputeDigestE implements Runnable {

    private String filename;

    public ComputeDigestE(String filename){
        this.filename = filename;
    }

    @Override
    public void run(){
        try{
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
            }            System.out.println(result);
        }catch (IOException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //Executor executor = Executors.newSingleThreadExecutor();
        Executor executor = Executors.newFixedThreadPool(5);
        //Executor executor = Executors.newCachedThreadPool();


        for(String f : args){
            executor.execute(new ComputeDigestE(f));
        }

        ((ExecutorService) executor).shutdown();
    }
}
