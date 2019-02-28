package it.imtlucca.lecture5;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class URLExtractor {
    static CompletableFuture<Void> retrieveURL(URL u){
        return
        CompletableFuture.supplyAsync(() -> {
            try {
                return (HttpURLConnection)u.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).thenApplyAsync( conn -> {
            try {
                conn.setRequestMethod("GET");
                conn.connect();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return conn;
        }).thenAcceptAsync(conn -> {
            try {
                System.out.printf("Request: %s  --- response status: %d\n", u.toString(), conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws MalformedURLException {
        URL[] urls = {new URL("https://www.example.com"), new URL("https://www.google.com"), new URL("http://www.imtlucca.it")};
        CompletableFuture<Void>[] results = new CompletableFuture[urls.length];
        for(int i=0; i< results.length; ++i)
            results[i] = retrieveURL(urls[i]);
        CompletableFuture.allOf(results).join();
    }
}
