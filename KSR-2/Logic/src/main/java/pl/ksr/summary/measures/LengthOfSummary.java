package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class LengthOfSummary {
    public static double calculateT5(List<Label> summarizers) {
        return 2 * Math.pow(0.5, summarizers.size());
    }
}
