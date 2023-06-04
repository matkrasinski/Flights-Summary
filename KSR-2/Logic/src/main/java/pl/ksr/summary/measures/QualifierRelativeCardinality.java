package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class QualifierRelativeCardinality {
    public static double calculateT10(List<Label> qualifiers) {
        double product = 1;

        for (Label qualifier : qualifiers) {
            product *= (qualifier.getFuzzySet().getCardinality(null) / qualifier.getFuzzySet().getUniverseCardinality());
        }

        return 1 - Math.pow(product, 1d / qualifiers.size());
    }
}
