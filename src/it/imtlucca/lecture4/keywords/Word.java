package it.imtlucca.lecture4.keywords;

public class Word implements Comparable<Word>{
    private String word;
    private int tf;
    private int df;
    private double tfidf;

    public Word(String word){
        this.word = word;
        df = 1;
    }

    public void incrTf(){
        this.tf++;
    }

    public static Word merge(Word w1, Word w2){
        if(w1.word.equals(w2.word)){
            int df = w1.df + w2.df;
            int tf = w1.tf + w2.tf;
            Word ret = new Word(w1.word);
            ret.df = df;
            ret.tf = tf;
            return ret;
        }else
            throw new IllegalArgumentException("Different words");
    }

    public String getWord(){
        return word;
    }

    public void setDf(int df, int N){
        this.df = df;
        tfidf = tf * Math.log(Double.valueOf(N) / df);
    }

    @Override
    public int compareTo(Word word) {
        return Double.compare(word.tfidf, this.tfidf);
    }

    public int getDf() {
        return df;
    }
}
