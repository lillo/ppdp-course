package it.imtlucca.lecture0;

public class TestInterface {
    public static void printTen(IntSequence seq){
        for(int i = 1; i <= 10; ++i){
            System.out.println(seq.next());
        }
    }

    public static void main(String[] arg){
        printTen(new Naturals());
        printTen(new Squares());
    }
}
