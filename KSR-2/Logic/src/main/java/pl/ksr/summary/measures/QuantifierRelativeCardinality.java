package pl.ksr.summary.measures;

import pl.ksr.lingustic.LinguisticQuantifier;

public class QuantifierRelativeCardinality {
    public static double calculateT7(LinguisticQuantifier quantifier) {
        return 1 - quantifier.getFuzzySet().getCardinality(null) / quantifier.getFuzzySet().getUniverseCardinality();
    }

}
