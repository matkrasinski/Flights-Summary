package pl.ksr.sets;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import pl.ksr.functions.CompoundFunction;
import pl.ksr.functions.MembershipFunction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class FuzzySet {

    private final MembershipFunction membershipFunction;

    public FuzzySet(MembershipFunction function) {
        membershipFunction = function;
    }

    public FuzzySet getComplement() {
        return null;
    }

    public FuzzySet getIntersection(FuzzySet fuzzySet) {
        CompoundFunction function = new CompoundFunction(membershipFunction, true);
        function.addGaussianFunction(fuzzySet.getMembershipFunction());
        return new FuzzySet(function);
    }


    public CrispSet getSupp() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return new CrispSet(getUniverseOfDiscourse().getRange().stream()
                    .filter(e -> calculateMembership(e) > 0).toList(),
                    getUniverseOfDiscourse());
        }
        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);
        double step = 1;
        List<Double> values = new ArrayList<>();
        for (double i = min; i <= max; i += step) {
            double value = calculateMembership(i);
//            System.out.println(min + " " + max + " " + i + " " + value);
            if (value > 1e-8)
                values.add(i);
        }
        return new CrispSet(values, getUniverseOfDiscourse());
    }

    public CrispSet getAlphaCut(double alpha) {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return new CrispSet(getUniverseOfDiscourse().getRange().stream()
                    .mapToDouble(this::calculateMembership).filter(e -> e > alpha).boxed().toList(),
                    getUniverseOfDiscourse());
        }
        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);
        double step = 1;
        List<Double> values = new ArrayList<>();
        for (double i = min; i <= max; i += step) {
            double value = calculateMembership(i);
            DecimalFormat f = new DecimalFormat("##.00");
            value = Double.parseDouble(f.format(value));
            if (value > alpha)
                values.add(i);
        }
        return new CrispSet(values, getUniverseOfDiscourse());
    }

    public double getCardinality() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getUniverseOfDiscourse().getRange().stream().mapToDouble(this::calculateMembership).sum();
        }
        UnivariateFunction function = this::calculateMembership;

        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);

        return calculateIntegral(function, min, max);
    }
    private double getComplementCardinality() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getUniverseOfDiscourse().getRange().stream().mapToDouble(x -> (1 - calculateMembership(x))).sum();
        }
        UnivariateFunction function = e -> (1 - calculateMembership(e));

        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);

        return calculateIntegral(function, min, max);
    }

    private double getUniverseCardinality() {
        return getCardinality() + getComplementCardinality();
    }

    public double getDegreeOfFuzziness() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getSupp().getElements().size() / getUniverseCardinality();
        }

        UnivariateFunction function = this::calculateMembership;

        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);

        double sum = calculateIntegral(function, min, max);
        double universeCardinality = getUniverseCardinality();
        return sum / universeCardinality;
    }

    public double getCentroid() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            double sum = 0;
            double weights = 0;

            for (double x : getUniverseOfDiscourse().getRange()) {
                double weight = calculateMembership(x);
                sum += x * weight;
                weights += weight;
            }
            return sum / weights;
        }
        UnivariateFunction weightedSumFunction = e -> (e * calculateMembership(e));
        UnivariateFunction sumFunction = this::calculateMembership;

        double min = getUniverseOfDiscourse().getRange().get(0);
        double max = getUniverseOfDiscourse().getRange().get(1);

        double upper = calculateIntegral(weightedSumFunction, min, max);
        double lower = calculateIntegral(sumFunction, min, max);

        return upper / lower;
    }

    public boolean isNormal() {
        return getHeight() == 1;
    }

    public boolean isConvex() {
        for (double alpha = 0; alpha <= 1; alpha += 0.1) {
            CrispSet alphaCut = getAlphaCut(alpha);
            for (int i = 0; i < alphaCut.getElements().size(); i++) {
                double r = alphaCut.getElements().get(i);
                for (int j = i + 1; j < alphaCut.getElements().size(); j++) {
                    double s = alphaCut.getElements().get(j);
                    for (double lambda = 0; lambda <= 1; lambda += 0.1) {
                        double value = lambda * r + (1 - lambda) * s;
                        if (alphaCut.getUniverseOfDiscourse().isIn(value)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isEmpty() {
        return getSupp().getElements().size() == 0;
    }

    public double getHeight() {
        return getSupp().getElements().stream()
                .mapToDouble(membershipFunction::calculateMembershipDegree)
                .max().orElse(0d);
    }

    public double calculateMembership(double x) {
        return membershipFunction.calculateMembershipDegree(x);
    }

    private double calculateIntegral(UnivariateFunction function, double min, double max) {
        SimpsonIntegrator integrator = new SimpsonIntegrator();
        return integrator.integrate(Integer.MAX_VALUE, function, min, max);
    }

    public Universe getUniverseOfDiscourse() {
        return this.membershipFunction.getUniverseOfDiscourse();
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
}
