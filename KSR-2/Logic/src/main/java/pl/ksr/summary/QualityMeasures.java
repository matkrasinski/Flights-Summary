package pl.ksr.summary;

import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;
import pl.ksr.sets.FuzzySet;

import java.util.List;

public class QualityMeasures {

    private double T_1;
    private double T_2;
    private double T_3;
    private double T_4;
    private double T_5;

    private double T_6;
    private double T_7;
    private double T_8;
    private double T_9;
    private double T_10;


    // Degree of truth
    public QualityMeasures calculateT_1(List<LinguisticVariable> summarizers,
                                        List<LinguisticVariable> qualifiers,
                                        List<LinguisticQuantifier> quantifiers) {
        this.T_1 = 0;
        return this;
    }

    // Degree of imprecision
    public QualityMeasures calculateT_2(List<LinguisticVariable> summarizers) {
        this.T_2 = 0;
        return this;
    }

    // Degree of covering
    public QualityMeasures calculateT_3(List<LinguisticVariable> summarizers, List<LinguisticVariable> qualifiers) {
        this.T_3 = 0;
        return this;
    }

    // Degree of appropriateness
    public QualityMeasures calculateT_4(List<LinguisticVariable> summarizers) {
        this.T_4 = 0;
        return this;
    }

    // Length of summary
    public QualityMeasures calculateT_5(List<FuzzySet> fuzzySets) {
        this.T_5 = 2 * Math.pow(0.5, fuzzySets.size());
        return this;
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
    public QualityMeasures calculateT_6(LinguisticQuantifier quantifier) {
        this.T_6 = 0;
        return this;
    }

    // Degree of quantifier cardinality
    public QualityMeasures calculateT_7(LinguisticQuantifier quantifier) {
        this.T_7 = 0;
        return this;
    }

    // Degree of summarizer cardinality
    public QualityMeasures calculateT_8(List<LinguisticVariable> summarizers) {
        this.T_8 = 0;
        return this;
    }

    // Degree of qualifier imprecision
    public QualityMeasures calculateT_9(List<LinguisticVariable> qualifiers) {
        this.T_9 = 0;
        return this;
    }

    // Degree of qualifier cardinality
    public QualityMeasures calculateT_10(LinguisticVariable qualifier) {
        this.T_10 = 0;
        return this;
    }

    // An extended concept of the optimal summary
    public double calculateExtendedOptimalSummary(List<Double> weights) {
        double T = 0;
        List<Double> Ts = getAll();
        for (int i = 0; i < Ts.size(); i++)
            T += weights.get(i) * Ts.get(i);

        return T;
    }

    private List<Double> getAll() {
        return List.of(T_1, T_2, T_3, T_4, T_5, T_6, T_7, T_8, T_9, T_10);
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
}
