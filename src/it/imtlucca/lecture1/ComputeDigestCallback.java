package it.imtlucca.lecture1;

import java.io.*;
import java.security.*;
import java.util.*;
import java.util.function.*;

public class ComputeDigestCallback implements Runnable {

    private String filename;
    private Consumer<String> c;

    public ComputeDigestCallback(String filename, Consumer<String> c){

        this.filename = filename;
        this.c = c;
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
            }
            c.accept(result.toString());
        }catch (IOException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        List<ComputeDigestCallback> ts = new ArrayList<>();
        Consumer<String> callback = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(">>>>>" + s);
            }
        };

        for(String f : args){
            ComputeDigestCallback comp = new ComputeDigestCallback(f, callback);
            Thread t = new Thread(comp);
            t.start();
            ts.add(comp);
        }



    }
}
