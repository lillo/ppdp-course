package it.imtlucca.lecture0;

public class Naturals implements IntSequence {
    private int current;

    public Naturals(){
        current = 0;
    }

    @Override
    public boolean hasNext() {
        return true;
    }


    public int next() {
        int temp = current;
        current++;
        return temp;
    }
}
