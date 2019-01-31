package it.imtlucca.lecture2.knn;

public class Util {
    static double euclideanDistance(Sample s1, Sample s2){
        double result = 0.0;

        assert(s1.size() == s2.size());

        double[] sample1 = s1.getRawData();
        double[] sample2 = s2.getRawData();

        for(int i=0; i < sample1.length; ++i)
            result += Math.pow(sample1[i] - sample2[i], 2);

        return Math.sqrt(result);
    }

    static class Pair<A,B>{
        public final A first;
        public final B second;

        public Pair(A first, B second){
            this.first = first;
            this.second = second;
        }
    }
}
