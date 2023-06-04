package pl.ksr.summary.measures;

import pl.ksr.lingustic.LinguisticQuantifier;

public class QuantifierImprecision {
    public static double calculateT6(LinguisticQuantifier quantifier) {
        return 1 - quantifier.getFuzzySet().getDegreeOfFuzziness();
    }
}
