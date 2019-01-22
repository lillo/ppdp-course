package it.imtlucca.lecture1;

import java.io.*;
import java.security.*;
import java.util.*;

public class ComputeDigestWrong1 implements Runnable {

    private String filename;
    private String resultDigest;

    public ComputeDigestWrong1(String filename){
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
            resultDigest = result.toString();
            for (byte b : digest) {
                result.append(String.format("%02X", b));
            }           
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
        List<ComputeDigestWrong1> ts = new ArrayList<>();
        long startTime = System.nanoTime();

        for(String f : args){
            ComputeDigestWrong1 comp = new ComputeDigestWrong1(f);
            Thread t = new Thread(comp);
            t.start();
            ts.add(comp);
        }

        for(ComputeDigestWrong1 t : ts) {
            System.out.println(t.getDigest());
        }


    }
}
