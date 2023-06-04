package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.Subject;

import java.util.ArrayList;
import java.util.List;

public class DegreeOfCovering {
    public static double calculateT3(Subject subject, List<Label> summarizers, List<Label> qualifiers) {
        List<List<Double>> summarizerObjects = new ArrayList<>();
        List<List<Double>> qualifierObjects = new ArrayList<>();

        for (Label summarizer : summarizers)
            summarizerObjects.add(subject.getObject(summarizer.getAttributeName()));
        for (Label qualifier : qualifiers)
            qualifierObjects.add(subject.getObject(qualifier.getAttributeName()));
        List<Double> memberships1 = Label.andConnective(summarizerObjects, summarizers);
        List<Double> memberships2 = Label.andConnective(qualifierObjects, qualifiers);

        double t = 0;
        double h = 0;
        for (int i = 0; i < memberships1.size(); i++) {
            if (memberships1.get(i) > 0 && memberships2.get(i) > 0)
                t++;
            if (memberships2.get(i) > 0)
                h++;
        }

        return t / h;
    }
    public static double calculateT3(Subject subject, List<Label> summarizers) {
        List<List<Double>> summarizerObjects = new ArrayList<>();

        for (Label summarizer : summarizers)
            summarizerObjects.add(subject.getObject(summarizer.getAttributeName()));

        List<Double> memberships = Label.andConnective(summarizerObjects, summarizers);

        double t = memberships.stream().filter(e -> e > 0).mapToDouble(e -> e).count();
        double h = memberships.size();

        return t / h;
    }
}
