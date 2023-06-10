package pl.ksr.summary.measures;

import pl.ksr.lingustic.LinguisticQuantifier;

public class QuantifierImprecision {
    public static double calculateT6(LinguisticQuantifier quantifier) {
        double supp = 0;
        for (var range : quantifier.getFuzzySet().getSuppRange())
            supp += Math.abs(range.get(0) - range.get(1));
        return 1 - supp / quantifier.getFuzzySet().getUniverseCardinality();
    }
}
