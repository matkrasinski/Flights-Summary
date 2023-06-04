package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.summary.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DegreeOfTruth {

    public static double calculateT1forSingleFirstForm(LinguisticQuantifier quantifier,
                                                       Subject subject,
                                                       List<Label> summarizers) {
        List<List<Double>> objects = new ArrayList<>();

        for (Label summarizer : summarizers) {
            objects.add(subject.getObject(summarizer.getAttributeName()));
        }
        List<Double> memberships = Label.andConnective(objects, summarizers);
        double r = memberships.stream().mapToDouble(e -> e).sum();

        if (quantifier.isRelative()) {
            r = r / (double) objects.get(0).size();
        }

        return quantifier.getFuzzySet().calculateMembership(r);
    }

    public static double calculateT1forSingleSecondForm(LinguisticQuantifier quantifier,
                                                       Subject subject,
                                                       List<Label> summarizers,
                                                       List<Label> qualifiers) {
        List<List<Double>> objects = new ArrayList<>();
        List<List<Double>> qualifierObjects = new ArrayList<>();
        for (Label summarizer : summarizers)
            objects.add(subject.getObject(summarizer.getAttributeName()));
        for (Label qualifier : qualifiers)
            qualifierObjects.add(subject.getObject(qualifier.getAttributeName()));

        objects.addAll(qualifierObjects);
        List<Double> memberships = Label.andConnective(objects,
                Stream.concat(summarizers.stream(), qualifiers.stream()).toList());

        double r = memberships.stream().mapToDouble(e -> e).sum();

        double delimiter = Label.andConnective(qualifierObjects, qualifiers).stream().mapToDouble(e -> e).sum();

        return quantifier.getFuzzySet().calculateMembership(r / delimiter);
    }
    public static double calculateT1forMultiFirstForm(LinguisticQuantifier quantifier,
                                                      List<Subject> subjects,
                                                      List<Label> summarizers) {
        List<List<Double>> p1Objects = new ArrayList<>();
        List<List<Double>> p2Objects = new ArrayList<>();

        for (Label summarizer : summarizers) {
            p1Objects.add(subjects.get(0).getObject(summarizer.getAttributeName()));
            p2Objects.add(subjects.get(1).getObject(summarizer.getAttributeName()));
        }

        double memberships1 = Label.andConnective(p1Objects, summarizers).stream().mapToDouble(e -> e).sum();
        double memberships2 = Label.andConnective(p2Objects, summarizers).stream().mapToDouble(e -> e).sum();

        return quantifier.getFuzzySet().calculateMembership(
                ((1d / p1Objects.size()) * memberships1) /
                        ((1d / p1Objects.size()) * memberships1 + (1d / p2Objects.size()) * memberships2)
        );
    }
    public static double calculateT1forMultiSecondForm(LinguisticQuantifier quantifier,
                                                       List<Subject> subjects,
                                                       List<Label> summarizers,
                                                       List<Label> qualifiers) {
        List<List<Double>> p1Objects = new ArrayList<>();
        List<List<Double>> p2Objects = new ArrayList<>();

        for (Label summarizer : summarizers) {
            p1Objects.add(subjects.get(0).getObject(summarizer.getAttributeName()));
            p2Objects.add(subjects.get(1).getObject(summarizer.getAttributeName()));
        }

        for (Label qualifier : qualifiers)
            p2Objects.add(subjects.get(1).getObject(qualifier.getAttributeName()));

        double sigmaCountSP1 = Label.andConnective(p1Objects, summarizers).stream().mapToDouble(e -> e).sum();
        double sigmaCountSP2W = Label.andConnective(p2Objects,
                Stream.concat(summarizers.stream(), qualifiers.stream()).toList()).stream().mapToDouble(e -> e).sum();

        double m1 = p1Objects.get(0).size();
        double m2 = p2Objects.get(0).size();

        return quantifier.getFuzzySet().calculateMembership(
                ((1d / m1) / sigmaCountSP1) /
                        ((1d / m1) * sigmaCountSP1 + (1d / m2) * sigmaCountSP2W)
        );
    }
    public static double calculateT1forMultiThirdForm(LinguisticQuantifier quantifier,
                                                       List<Subject> subjects,
                                                       List<Label> summarizers,
                                                       List<Label> qualifiers) {
        List<List<Double>> p1Objects = new ArrayList<>();
        List<List<Double>> p2Objects = new ArrayList<>();

        for (Label summarizer : summarizers) {
            p1Objects.add(subjects.get(0).getObject(summarizer.getAttributeName()));
            p2Objects.add(subjects.get(1).getObject(summarizer.getAttributeName()));
        }

        for (Label qualifier : qualifiers)
            p1Objects.add(subjects.get(0).getObject(qualifier.getAttributeName()));

        double sigmaCountSP1W = Label.andConnective(p1Objects,
                Stream.concat(summarizers.stream(), qualifiers.stream()).toList()).stream().mapToDouble(e -> e).sum();
        double sigmaCountSP2 = Label.andConnective(p2Objects, summarizers).stream().mapToDouble(e -> e).sum();

        double m1 = p1Objects.get(0).size();
        double m2 = p2Objects.get(0).size();

        return quantifier.getFuzzySet().calculateMembership(
                ((1d / m1) / sigmaCountSP1W) /
                        ((1d / m1) * sigmaCountSP1W + (1d / m2) * sigmaCountSP2)
        );
    }

    public static double calculateT1forMultiFourthForm(List<Subject> subjects, List<Label> summarizers) {
        List<List<Double>> p1 = new ArrayList<>();
        List<List<Double>> p2 = new ArrayList<>();

        for (Label label : summarizers) {
            p1.add(subjects.get(0).getObject(label.getAttributeName()));
            p2.add(subjects.get(1).getObject(label.getAttributeName()));
        }

        double sp1 = Label.andConnective(p1, summarizers).stream().mapToDouble(e -> e).sum();
        double sp2 = Label.andConnective(p2, summarizers).stream().mapToDouble(e -> e).sum();

        double m1 = p1.get(0).size();
        double m2 = p2.get(0).size();

        double m = m1 + m2;

        return 1 - implication(sp2 / m, sp1 / m);
    }

    // Reichenbach implication
    private static double implication(double a, double b) {
//        return Math.min(1, 1 - a + b);
        return 1 - a + a * b;
    }

}
