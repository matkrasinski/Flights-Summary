package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class SummarizerCardinality {
    public static double calculateT8(List<Label> summarizers) {
        double m = summarizers.get(0).getFuzzySet().getUniverseCardinality();
        double product = 1;
        for (Label summarizer : summarizers) {
            product *= (summarizer.getFuzzySet().getCardinality() / m);
        }

        return 1 - Math.pow(product, 1d / summarizers.size());
    }
}
