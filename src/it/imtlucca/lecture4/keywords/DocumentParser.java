package it.imtlucca.lecture4.keywords;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.text.Normalizer;

public class DocumentParser {
    public static Document parse(String path){
        Path file = Paths.get(path);
        Document doc = new Document(file.toString());

        try(BufferedReader reader = Files.newBufferedReader(file)){
            for(String line : Files.readAllLines(file))
                parseLine(line, doc);
        }catch(IOException e){
            e.printStackTrace();
        }
        return doc;
    }

    private static void parseLine(String line, Document doc) {
        line = Normalizer.normalize(line, Normalizer.Form.NFKC);
        line = line.replaceAll("[^\\p{ASCII}]", "");
        line = line.toLowerCase();

        for(String w : line.split("\\W+"))
            doc.addWord(w);
    }
}
