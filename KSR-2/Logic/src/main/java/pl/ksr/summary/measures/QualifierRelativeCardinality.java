package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.Subject;

import java.util.List;

public class QualifierRelativeCardinality {
    public static double calculateT10(Subject subject, List<Label> qualifiers) {
        double product = 1;
        for (Label qualifier : qualifiers) {
            List<Double> objects = subject.getObject(qualifier.getAttributeName());
            product *= (qualifier.getFuzzySet().getCardinality(objects) / objects.size());
        }

        return 1 - Math.pow(product, 1d / qualifiers.size());
    }
}
