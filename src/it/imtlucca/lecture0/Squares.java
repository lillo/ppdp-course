package it.imtlucca.lecture0;

public class Squares implements IntSequence {
    private int current;

    public Squares(){
        current = 1;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public int next() {
        int temp = current;
        ++current;
        return temp * temp;
    }
}
