package it.imtlucca.lecture2.knn;

import java.util.*;

public class SequentialClassifier {
    private Sample[] dataset;
    private int k;

    public SequentialClassifier(Sample[] dataset, int k){
        this.dataset = dataset;
        this.k = k;
    }

    public String classify(Sample sample){
        Distance[] distances = new Distance[dataset.length];   // This is not a good style

        for(int i = 0; i < distances.length; ++i)
            distances[i] = new Distance(Util.euclideanDistance(dataset[i], sample), i);

        Arrays.sort(distances, (p1,p2) -> (int)(p1.value - p2.value));

        Map<String, Integer> results = new HashMap<>();
        for(int i=0; i < k; ++i){
            Sample s = dataset[distances[i].pos];
            String tag = s.getTag();
            results.merge(tag, 1, (a, b) -> a + b);
        }
        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
