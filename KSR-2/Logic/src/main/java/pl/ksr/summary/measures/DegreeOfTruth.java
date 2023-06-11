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
        double arg = ((1d / p1Objects.get(0).size()) * memberships1) /
                ((1d / p1Objects.get(0).size()) * memberships1 + (1d / p2Objects.get(0).size()) * memberships2);

        return quantifier.getFuzzySet().calculateMembership(arg);
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


        double memberships1 = Label.andConnective(p1Objects, summarizers).stream().mapToDouble(e -> e).sum();
        List<Label> labels = new ArrayList<>();
        labels.addAll(summarizers);
        labels.addAll(qualifiers);

        double memberships2 = Label.andConnective(p2Objects, labels).stream().mapToDouble(e -> e).sum();

        double arg = ((1d / p1Objects.get(0).size()) * memberships1) /
                ((1d / p1Objects.get(0).size()) * memberships1 + (1d / p2Objects.get(0).size()) * memberships2);

        return quantifier.getFuzzySet().calculateMembership(arg);
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

        List<Label> labels = new ArrayList<>();
        labels.addAll(summarizers);
        labels.addAll(qualifiers);

        double memberships1 = Label.andConnective(p1Objects, labels).stream().mapToDouble(e -> e).sum();


        double memberships2 = Label.andConnective(p2Objects, summarizers).stream().mapToDouble(e -> e).sum();

        double arg = ((1d / p1Objects.get(0).size()) * memberships1) /
                ((1d / p1Objects.get(0).size()) * memberships1 + (1d / p2Objects.get(0).size()) * memberships2);

        return quantifier.getFuzzySet().calculateMembership(arg);
    }

    public static double calculateT1forMultiFourthForm(List<Subject> subjects, List<Label> summarizers) {
        List<List<Double>> p1Objects = new ArrayList<>();
        List<List<Double>> p2Objects = new ArrayList<>();

        for (Label label : summarizers) {
            p1Objects.add(subjects.get(0).getObject(label.getAttributeName()));
            p2Objects.add(subjects.get(1).getObject(label.getAttributeName()));
        }

        List<Double> sp1 = Label.andConnective(p1Objects, summarizers).stream().mapToDouble(e -> e).boxed().toList();
        List<Double> sp2 = Label.andConnective(p2Objects, summarizers).stream().mapToDouble(e -> e).boxed().toList();

        double m1 = sp1.size();
        double m2 = sp2.size();
        double sum1 = sp1.stream().mapToDouble(e -> e).sum();
        double sum2 = sp2.stream().mapToDouble(e -> e).sum();


        return 1 - implication(sum2 / m2, sum1 / m1);
    }

    // Reichenbach implication
    // Lukasiewicz implication
    private static double implication(double a, double b) {
        return Math.min(1, 1 - a + b);
//        return 1 - a + a * b;
    }

}
