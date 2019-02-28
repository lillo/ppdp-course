package it.imtlucca.lecture4.keywords;

import java.util.*;

public class Document {
    private String fileName;
    private HashMap<String, Word> voc;

    public Document(String fileName){
        this.fileName = fileName;
        voc = new HashMap<>();
    }

    public HashMap<String, Word> getWords(){
        return voc;
    }

    public void addWord(String string){
        voc.computeIfAbsent(string, k -> new Word(k)).incrTf();
    }
}
