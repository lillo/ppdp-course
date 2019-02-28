package it.imtlucca.lecture5;

import java.io.IOException;
import java.nio.file.*;

public class ReplaceWord {

    static void replaceWord(String fileName, String word, String replacement) throws IOException {
        Files.lines(Paths.get(fileName)).parallel()
             .map(line -> line.replaceAll(word, replacement))
             .forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 3){
            System.out.printf("Usage: java ReplaceWord <file> <word> <repl>");
            return;
        }
        replaceWord(args[0], args[1], args[2]);
    }
}
