package it.imtlucca.lecture4.keywords;

public class Keyword implements Comparable<Keyword> {
    private Word word;
    private int df;

    public Keyword(Word word, int df){
        this.word = word;
        this.df = df;
    }

    @Override
    public int compareTo(Keyword keyword) {
        return Integer.compare(keyword.getDf(), this.getDf());
    }

    public int getDf() {
        return df;
    }

    public String getWord(){
        return word.getWord();
    }
}
