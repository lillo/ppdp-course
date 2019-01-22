package it.imtlucca.lecture1;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ComputeDigestPolling implements Runnable {

    private String filename;
    private String resultDigest;

    public ComputeDigestPolling(String filename){
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
        List<ComputeDigestPolling> ts = new ArrayList<>();

        for(String f : args){
            ComputeDigestPolling comp = new ComputeDigestPolling(f);
            Thread t = new Thread(comp);
            t.start();
            ts.add(comp);
        }

        for(ComputeDigestPolling t : ts) {
            // Start polling
            while(true) {
                if(t.getDigest() != null){
                    System.out.println(t.getDigest());
                    break;
                }else
                    System.out.print(".");
            }
        }


    }
}
