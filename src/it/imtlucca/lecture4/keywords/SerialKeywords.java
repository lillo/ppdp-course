package it.imtlucca.lecture4.keywords;

import java.io.File;
import java.util.*;

public class SerialKeywords {
    public static void main(String[] args) {
        File source = new File(args[0]);
        File[] files = source.listFiles();
        HashMap<String, Word> words = new HashMap<>();
        HashMap<String, Integer> keywords = new HashMap<>();
        int numDocuments = 0;

        if (files == null) {
            System.err.println("Unable to open data");
            return;
        }

        for (File file : files) {
            Document doc = DocumentParser.parse(file.getAbsolutePath());
            for (Word w : doc.getWords().values()) {
                words.merge(w.getWord(), w, Word::merge);
            }
            ++numDocuments;
        }

        for (File file : files) {
            Document doc = DocumentParser.parse(file.getAbsolutePath());
            List<Word> kw = new ArrayList<>(doc.getWords().values());

            for (Word word : kw) {
                Word globalWord = words.get(word.getWord());
                word.setDf(globalWord.getDf(), numDocuments);
            }
            Collections.sort(kw);
            for (Word word : kw) {
                addKeyword(keywords, word.getWord());
            }
        }
        List<Keyword> orderedGlobalKeywords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry :
                keywords.entrySet()) {
            Keyword keyword = new Keyword(new Word(entry.getKey()), entry.getValue());
            orderedGlobalKeywords.add(keyword);
        }
        Collections.sort(orderedGlobalKeywords);
        if (orderedGlobalKeywords.size() > 100) {
            orderedGlobalKeywords =
                    orderedGlobalKeywords.subList(0, 100);
        }
        for (Keyword keyword : orderedGlobalKeywords) {
            System.out.println(keyword.getWord() + ": " +
                    keyword.getDf());
        }

    }

    private static void addKeyword(HashMap<String, Integer> keywords, String word) {
        keywords.merge(word, 1, Integer::sum);
    }
}
