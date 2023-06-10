package pl.ksr.sets;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import pl.ksr.functions.MembershipFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FuzzySet {

    private final MembershipFunction membershipFunction;

    public FuzzySet(MembershipFunction function) {
        membershipFunction = function;
    }

    public FuzzySet complement() {
        MembershipFunction function = new MembershipFunction() {
                    @Override
                    public double calculateMembershipDegree(double x) {
                        return 1 - calculateMembership(x);
                    }
                };
        function.universeOfDiscourse = this.getUniverseOfDiscourse();
        return new FuzzySet(function);
    }

    public FuzzySet intersection(FuzzySet fuzzySet) {
        MembershipFunction function = new MembershipFunction() {
            @Override
            public double calculateMembershipDegree(double x) {
                return Math.min(calculateMembership(x), fuzzySet.membershipFunction.calculateMembershipDegree(x));
            }
        };

        if (getUniverseOfDiscourse() instanceof ContinuousUniverse) {
            function.universeOfDiscourse =
                    new ContinuousUniverse(Stream.concat(this.getUniverseOfDiscourse().getRange().stream(),
                            fuzzySet.getUniverseOfDiscourse().getRange().stream()).toList());
        } else {
            function.universeOfDiscourse =
                    new DiscreteUniverse(Stream.concat(this.getUniverseOfDiscourse().getRange().get(0).stream(),
                            fuzzySet.getUniverseOfDiscourse().getRange().get(0).stream()).toList());
        }

        return new FuzzySet(function);
    }

    public FuzzySet union(FuzzySet fuzzySet) {
        MembershipFunction function = new MembershipFunction() {
            @Override
            public double calculateMembershipDegree(double x) {
                return Math.max(calculateMembership(x), fuzzySet.membershipFunction.calculateMembershipDegree(x));
            }
        };
        function.universeOfDiscourse =
                new ContinuousUniverse(Stream.concat(this.getUniverseOfDiscourse().getRange().stream(),
                        fuzzySet.getUniverseOfDiscourse().getRange().stream()).toList());

        return new FuzzySet(function);
    }

    public CrispSet getSupp(List<Double> objects) {
        return getAlphaCut(0, objects);
    }
    public List<List<Double>> getSuppRange() {
        return getAlphaRange(1e-5);
    }

    public List<List<Double>> getAlphaRange(double alpha) {
        List<List<Double>> ranges = getUniverseOfDiscourse().getRange();

        List<List<Double>> newRanges = new ArrayList<>();
        List<Double> newRange = new ArrayList<>();
        for (List<Double> range : ranges) {

            boolean isStart = false;
            for (double i = range.get(0); i <= range.get(1); i += 0.001) {
                double value = calculateMembership(i);
                if (value >= alpha && !isStart) {
                    newRange.add(i);
                    isStart = true;
                }
                if (isStart && value <= 1e-5) {
                    newRange.add(i);
                    newRanges.add(newRange);
                    newRange = new ArrayList<>();
                    isStart = false;
                }
            }
            if (isStart) {
                newRange.add(range.get(1));
                newRanges.add(newRange);
            }
        }
        return newRanges;
    }

    public CrispSet getAlphaCut(double alpha, List<Double> objects) {
        List<Double> newObjects = objects.stream().filter(e -> calculateMembership(e) > alpha).toList();
        return new CrispSet(newObjects, getUniverseOfDiscourse());
    }

    public double getCardinality(List<Double> objects) {
        return objects.stream().mapToDouble(this::calculateMembership).sum();
    }

    public double getCardinalityLikeMeasure() {
        UnivariateFunction function = this::calculateMembership;

        double integral = 0;

        for (List<Double> range : getSuppRange()) {
            integral += calculateIntegral(function, range.get(0), range.get(1));
        }

        return integral;
    }

    public double getComplementCardinality() {
        UnivariateFunction function = e -> (1 - calculateMembership(e));

        double integral = 0;

        for (List<Double> range : getUniverseOfDiscourse().getRange())
            integral += calculateIntegral(function, range.get(0), range.get(1));

        return integral;
    }

    public double getUniverseCardinality() {
        return getCardinalityLikeMeasure() + getComplementCardinality();
    }

    public double getDegreeOfFuzziness(List<Double> objects) {
        return getAlphaDegreeOfFuzziness(1e-5, objects);
    }

    public double getAlphaDegreeOfFuzziness(double alpha, List<Double> objects) {
        return (double) getAlphaCut(alpha, objects).getElements().size() / objects.size();
    }

    public double getCentroid() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            double sum = 0;
            double weights = 0;

            for (double x : getUniverseOfDiscourse().getRange().get(0)) {
                double weight = calculateMembership(x);
                sum += x * weight;
                weights += weight;
            }
            return sum / weights;
        }

        UnivariateFunction weightedSumFunction = e -> (e * calculateMembership(e));
        UnivariateFunction sumFunction = this::calculateMembership;
        double upper = 0;
        double lower = 0;
        for (List<Double> range : getUniverseOfDiscourse().getRange()) {
            upper += calculateIntegral(weightedSumFunction, range.get(0), range.get(1));
            lower += calculateIntegral(sumFunction, range.get(0), range.get(1));
        }

        return upper / lower;
    }

    public boolean isNormal(List<Double> objects) {
        return getHeight(objects) == 1;
    }

    public boolean isConvex() {
//        for (double alpha = 0; alpha <= 1; alpha += 0.1) {
//            CrispSet alphaCut = getAlphaCut(alpha);
//            for (int i = 0; i < alphaCut.getElements().size(); i++) {
//                double r = alphaCut.getElements().get(i);
//                for (int j = i + 1; j < alphaCut.getElements().size(); j++) {
//                    double s = alphaCut.getElements().get(j);
//                    for (double lambda = 0; lambda <= 1; lambda += 0.1) {
//                        double value = lambda * r + (1 - lambda) * s;
//                        if (alphaCut.getUniverseOfDiscourse().contains(value)) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
        return true;
    }

    public boolean isEmpty(List<Double> objects) {
        return getSupp(objects).getElements().isEmpty();
    }

    public double getHeight(List<Double> objects) {
        return getSupp(objects).getElements().stream()
                .mapToDouble(membershipFunction::calculateMembershipDegree)
                .max().orElse(0d);
    }

    public double calculateMembership(double x) {
        return membershipFunction.calculateMembershipDegree(x);
    }

    public double calculateIntegral(UnivariateFunction function, double min, double max) {
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
