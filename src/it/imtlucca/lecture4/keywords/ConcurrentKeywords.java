package it.imtlucca.lecture4.keywords;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentKeywords {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Word> globalVoc = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Integer> globalKeywords = new ConcurrentHashMap<>();

        File source = new File("data");
        File[] files = source.listFiles(f ->
                f.getName().endsWith(".txt"));
        if (files == null) {
            System.err.println("The 'data' folder not found!");
            return;
        }
        ConcurrentLinkedDeque<File> concurrentFileListPhase1 = new ConcurrentLinkedDeque<>(Arrays.asList(files));
        ConcurrentLinkedDeque<File> concurrentFileListPhase2 = new ConcurrentLinkedDeque<>(Arrays.asList(files));
        int numDocuments = files.length;

        int numTasks = Runtime.getRuntime().availableProcessors();
        Phaser phaser = new Phaser();
        KeywordTask[] tasks = new KeywordTask[numTasks];
        ExecutorService executorService = Executors.newFixedThreadPool(numTasks);

        for (int i = 0; i < numTasks; i++) {
            tasks[i] = new KeywordTask(concurrentFileListPhase1, concurrentFileListPhase2, phaser, globalVoc,
                                       globalKeywords, concurrentFileListPhase1.size(), "Task" + i, i==0);
            phaser.register();
            System.out.println(phaser.getRegisteredParties() + " tasks arrived to the Phaser.");
            executorService.submit(tasks[i]);
        }

        executorService.shutdown();
        executorService.awaitTermination(120, TimeUnit.SECONDS);
    }
}
