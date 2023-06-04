package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.Subject;

import java.util.List;

public class QualifierImprecision {
    public static double calculateT9(Subject subject, List<Label> qualifiers) {
        if (qualifiers == null || qualifiers.isEmpty())
            return 0;
        double product = 1;
        for (Label qualifier : qualifiers) {
            List<Double> objects = subject.getObject(qualifier.getAttributeName());
            product *= qualifier.getFuzzySet().getDegreeOfFuzziness(objects);
        }

        return 1 - Math.pow(product, 1d / qualifiers.size());
    }
}
