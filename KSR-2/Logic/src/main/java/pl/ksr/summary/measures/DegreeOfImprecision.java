package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.Subject;

import java.util.List;

public class DegreeOfImprecision {
    public static double calculateT2(Subject subject, List<Label> summarizers) {
        double product = 1;
        for (Label summarizer : summarizers) {
            List<Double> objects = subject.getObject(summarizer.getAttributeName());
            product *= summarizer.getFuzzySet().getDegreeOfFuzziness(objects);
        }
        return 1 - Math.pow(product, 1d / summarizers.size());
    }
}
