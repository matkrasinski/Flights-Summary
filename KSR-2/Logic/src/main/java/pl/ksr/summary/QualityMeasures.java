package pl.ksr.summary;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.List;


public class QualityMeasures {
    private double T_1;
    private double T_2;
    private double T_3;
    private double T_4;
    private double T_5;
    private final double T_6;
    private double T_7;
    private final double T_8;
    private final double T_9;
    private double T_10;
    private double T_11;
    private final Summary summary;

    public QualityMeasures(Summary summary) {
        this.summary = summary;
        this.T_1 = calculateT_1(summary.getQuantifier(), summary.getSummarizers());
        this.T_2 = calculateT_2(summary.getSummarizers());
        if (summary.getQualifiers() != null && !summary.getQualifiers().isEmpty()) {
            this.T_3 = calculateT_3(summary.getSummarizers(), summary.getQualifiers().get(0));
            this.T_4 = calculateT_4(summary.getSummarizers(), summary.getQualifiers().get(0));
        } else {
            this.T_3 = calculateT_3(summary.getSummarizers(), null);
            this.T_4 = calculateT_4(summary.getSummarizers(), null);
        }
        this.T_5 = calculateT_5(summary.getSummarizers());
        this.T_6 = calculateT_6(summary.getQuantifier());
        this.T_7 = calculateT_7(summary.getQuantifier());
        this.T_8 = calculateT_8(summary.getSummarizers());
        this.T_9 = calculateT_9(summary.getQualifiers());
        this.T_10 = calculateT_10(summary.getQualifiers());
        this.T_11 = calculateT_11(summary.getQualifiers());
    }

    // Degree of truth
    public double calculateT_1(LinguisticQuantifier quantifier, List<Label> summarizers) {
        double degreeOfTruth = 0;
//        if (summarizers.size() == 1) {
//            List<Double> xs = FlightsRepository.findAllByName(summarizers.get(0).getAttributeName());
//            double sum = 0;
//            for (double x : xs)
//                sum += summarizers.get(0).getFuzzySet().calculateMembership(x);
//            System.out.println("sum :" + sum);
//            if (quantifier.isRelative()) {
//                sum = sum / summary.getSubjects().get(0).getObjects().size();
//            }
//            System.out.println("sum after division :" + sum);
//
//            degreeOfTruth = quantifier.getLabel().getFuzzySet().calculateMembership(sum);
//        } else if (summarizers.size() > 1) {
//            List<List<Double>> objects = new ArrayList<>();
//            for (Label label : summarizers)
//                objects.add(FlightsRepository.findAllByName(label.getAttributeName()));
//
//
////            System.out.println("summarizer :" + );
//            List<Double> sum1 = FuzzySet.calculateMembershipAnd(objects,
//                     summarizers.stream().map(Label::getFuzzySet).toList());
//
//            double sum = sum1.stream().mapToDouble(e -> e).sum();
//            System.out.println("sum :" + sum);
//
//            double delimiter = objects.get(objects.size() - 1).stream()
//                    .mapToDouble(e -> summarizers.get(summarizers.size() - 1).getFuzzySet().calculateMembership(e))
//                    .sum();
////            double delimiter = x2.stream().mapToDouble(e -> qualifier.getFuzzySet().calculateMembership(e)).sum();
//
//            if (quantifier.isRelative())
//                sum = sum / delimiter;
//            System.out.println("sum after division :" + sum);
//            if (Double.isNaN(sum))
//                sum = 0;
//            degreeOfTruth = quantifier.getLabel().getFuzzySet().calculateMembership(sum);
//
//        } else if (summary.getSummaryType() == SummaryType.MultiIForm) {
//            // TODO
//        } else if (summary.getSummaryType() == SummaryType.MultiIIForm) {
//            // TODO
//        } else if (summary.getSummaryType() == SummaryType.MultiIIIForm) {
//            // TODO
//        } else if (summary.getSummaryType() == SummaryType.MultiIVForm) {
//            // TODO
//        }
//        T_1 = degreeOfTruth;
        return T_1;
    }

    // Degree of imprecision
    public double calculateT_2(List<Label> summarizers)  {
        double degreeOfImprecision = 1;

        for (var summarizer : summarizers)
            degreeOfImprecision *= summarizer.getFuzzySet().getDegreeOfFuzziness();

        T_2 =  1 - Math.pow(degreeOfImprecision, 1d / summarizers.size());
        return T_2;
    }

    // Degree of covering
    public double calculateT_3(List<Label> summarizers, Label qualifier) {
        double t = 0;
        double h = 0;

        return T_3;

    }

    // Degree of appropriateness
    public double calculateT_4(List<Label> summarizers, Label qualifier) {
        double product = 1;

        return T_4;
    }

    // Length of summary
    public double calculateT_5(List<Label> summarizers) {
        this.T_5 = 2 * Math.pow(0.5, summarizers.size());
        return T_5;
    }

    // The optimal summary
    public double calculateOptimalSummary(List<Double> weights) {
        double T = 0;
        List<Double> Ts = getAll().subList(0, 5);
        for (int i = 0; i < Ts.size(); i++)
            T += weights.get(i) * Ts.get(i);

        return T;
    }

    // Degree of quantifier imprecision
    public double calculateT_6(LinguisticQuantifier quantifier) {
        var degreeOfFuzziness = quantifier.getLabel().getFuzzySet().getDegreeOfFuzziness();

//        quantifierImprecision =
//                Math.abs(supp.getUniverseOfDiscourse().getRange().get(0).get(0)
//                - supp.getUniverseOfDiscourse().getRange().get(0).get(1));

//        if (!quantifier.isRelative())
//            quantifierImprecision /= quantifier.getFuzzySet().getUniverseCardinality();
//                    summary.getSubjects().get(0).getObjects().size();


        return 1 - degreeOfFuzziness;
    }

    // Degree of quantifier cardinality
    public double calculateT_7(LinguisticQuantifier quantifier) {
        double quantifierCardinality = 1;

        return T_7;
    }

    // Degree of summarizer cardinality
    public double calculateT_8(List<Label> summarizers) {
        double summarizerCardinality = 1;
        for (Label summarizer : summarizers) {
            var range = summarizer.getFuzzySet().getUniverseOfDiscourse().getRange();
            double delimiter = 0;
            for (List<Double> x : range)
                delimiter += Math.abs(x.get(0) - x.get(x.size() - 1));

            summarizerCardinality *= summarizer.getFuzzySet().getCardinality() / delimiter;
        }

        return 1 - Math.pow(summarizerCardinality, 1d / summarizers.size());
    }

    // Degree of qualifier imprecision
    public double calculateT_9(List<Label> qualifiers) {
        if (qualifiers == null || qualifiers.size() == 0)
            return 0;
        if (qualifiers.size() == 1) {
            return 1 - qualifiers.get(0).getFuzzySet().getDegreeOfFuzziness();
        }
        double product = 1;
        for (Label qualifier : qualifiers) {
            product *= qualifier.getFuzzySet().getDegreeOfFuzziness();
        }

        return 1 - Math.pow(product, 1d / qualifiers.size());
    }

    // Degree of qualifier cardinality
    public double calculateT_10(List<Label> qualifiers) {

        if (qualifiers == null || qualifiers.size() == 0)
            return 0;
        if (qualifiers.size() == 1) {
            var range = qualifiers.get(0).getFuzzySet().getUniverseOfDiscourse().getRange();
            double delimiter = 0;
            double qualifierCardinality = 1;
            for (List<Double> x : range)
                delimiter += Math.abs(x.get(0) - x.get(x.size() - 1));
            qualifierCardinality *= (qualifiers.get(0).getFuzzySet().getCardinality() / delimiter);

            return 1 - qualifierCardinality;
        }
        double product = 1;
        for (Label qualifier : qualifiers) {
            var range = qualifier.getFuzzySet().getUniverseOfDiscourse().getRange().get(0);
            product *= qualifier.getFuzzySet().getCardinality() /
                    Math.abs(range.get(0) - range.get(range.size() - 1));
        }

        T_10 = 1 - Math.pow(product, 1d / qualifiers.size());
        return T_10;
    }

    // Length of qualifier
    public double calculateT_11(List<Label> qualifiers) {
        double q = 1;
        if (qualifiers != null && !qualifiers.isEmpty())
            q = qualifiers.size();
        T_11 = 2 * Math.pow(0.5, q);
        return T_11;
    }

    // An extended concept of the optimal summary
    public double calculateExtendedOptimalSummary(List<Double> weights) {
        double T = 0;
        List<Double> Ts = getAll();
        for (int i = 0; i < Ts.size(); i++)
            T += weights.get(i) * Ts.get(i);

        return T;
    }

    public List<Double> getAll() {
        return List.of(T_1, T_2, T_3, T_4, T_5, T_6, T_7, T_8, T_9, T_10, T_11);
    }

    public double getT_1() {
        return T_1;
    }

    public double getT_2() {
        return T_2;
    }

    public double getT_3() {
        return T_3;
    }

    public double getT_4() {
        return T_4;
    }

    public double getT_5() {
        return T_5;
    }

    public double getT_6() {
        return T_6;
    }

    public double getT_7() {
        return T_7;
    }

    public double getT_8() {
        return T_8;
    }

    public double getT_9() {
        return T_9;
    }

    public double getT_10() {
        return T_10;
    }
    public double getT_11() {
        return T_11;
    }
}
