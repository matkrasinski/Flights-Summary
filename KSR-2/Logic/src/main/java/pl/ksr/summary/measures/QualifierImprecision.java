package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class QualifierImprecision {
    public static double calculateT9(List<Label> qualifiers) {
        double product = 1;

        for (Label qualifier : qualifiers) {
            product *= qualifier.getFuzzySet().getDegreeOfFuzziness();
        }

        return 1 - Math.pow(product, 1d / qualifiers.size());
    }
}
