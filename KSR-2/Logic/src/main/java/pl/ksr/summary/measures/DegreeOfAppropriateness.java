package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.Subject;

import java.util.List;

public class DegreeOfAppropriateness {
    public static double calculateT4(Subject subject, List<Label> summarizers, List<Label> qualifiers) {
        double t_3 = DegreeOfCovering.calculateT3(subject, summarizers, qualifiers);
        double r = calculateR(subject, summarizers);
        return Math.abs(r - t_3);
    }

    public static double calculateT4(Subject subject, List<Label> summarizers) {
        double t_3 = DegreeOfCovering.calculateT3(subject, summarizers);
        double r = calculateR(subject, summarizers);
        return Math.abs(r - t_3);
    }

    private static double calculateR(Subject subject, List<Label> summarizers) {
        double r = 1;
        for (Label summarizer : summarizers) {
            List<Double> objects = subject.getObject(summarizer.getAttributeName());
            r *= (double) summarizer.getFuzzySet().getSupp(objects).getElements().size() / objects.size();
        }

        return r;
    }
}
