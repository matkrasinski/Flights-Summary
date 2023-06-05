package pl.ksr.summary.measures;

import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.List;

public class QuantifierImprecision {
    public static double calculateT6(LinguisticQuantifier quantifier) {
        List<Double> range = quantifier.getFuzzySet().getUniverseOfDiscourse().getRange().get(0);

        return 1 - Math.abs(range.get(0) - range.get(1)) / range.get(1);
    }
}
