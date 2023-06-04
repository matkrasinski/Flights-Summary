package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class DegreeOfImprecision {
    public static double calculateT2(List<Label> summarizers) {
        double product = 1;
        for (Label summarizer : summarizers) {
            product *= summarizer.getFuzzySet().getDegreeOfFuzziness();
        }
        return 1 - Math.pow(product, 1d / summarizers.size());
    }
}
