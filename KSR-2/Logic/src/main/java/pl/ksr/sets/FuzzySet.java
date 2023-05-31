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
        function.universeOfDiscourse =
                new ContinuousUniverse(Stream.concat(this.getUniverseOfDiscourse().getRange().stream(),
                        fuzzySet.getUniverseOfDiscourse().getRange().stream()).toList());

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

    public CrispSet getSupp() {
        return getAlphaCut(0);
    }

    public CrispSet getAlphaCut(double alpha) {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return new CrispSet(getUniverseOfDiscourse().getRange().get(0).stream()
                    .filter(e -> calculateMembership(e) > alpha).toList(),
                    getUniverseOfDiscourse());
        }
        List<List<Double>> ranges = getUniverseOfDiscourse().getRange();

        List<List<Double>> newRanges = new ArrayList<>();
        List<Double> newRange = new ArrayList<>();
        for (List<Double> range : ranges) {
            double step = 0.001;
            boolean isStart = false;
            for (double i = range.get(0); i <= range.get(1); i += step) {
                double value = calculateMembership(i);
                if (value >= 1e-5 && !isStart) {
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
        return new CrispSet(new ContinuousUniverse(newRanges));
    }

    public double getCardinality() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getUniverseOfDiscourse().getRange().get(0).stream().mapToDouble(this::calculateMembership).sum();
        }
        UnivariateFunction function = this::calculateMembership;

        double integral = 0;

        for (List<Double> range : getUniverseOfDiscourse().getRange()) {
            integral += calculateIntegral(function, range.get(0), range.get(1));
        }
        return integral;
    }
    public double getComplementCardinality() {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getUniverseOfDiscourse().getRange().get(0).stream().mapToDouble(x -> (1 - calculateMembership(x))).sum();
        }
        UnivariateFunction function = e -> (1 - calculateMembership(e));

        double integral = 0;

        for (List<Double> range : getUniverseOfDiscourse().getRange()) {
            integral += calculateIntegral(function, range.get(0), range.get(1));
        }
        return integral;
    }

    public double getUniverseCardinality() {
        return getCardinality() + getComplementCardinality();
    }

    public double getDegreeOfFuzziness() {
        return getAlphaDegreeOfFuzziness(0);
    }

    public double getAlphaDegreeOfFuzziness(double alpha) {
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE) {
            return getAlphaCut(alpha).getElements().size() / getUniverseCardinality();
        }

        UnivariateFunction function = this::calculateMembership;

        CrispSet alphaCut = getAlphaCut(alpha);

        double sum = 0;
        for (List<Double> range : alphaCut.getUniverseOfDiscourse().getRange()) {
            sum += calculateIntegral(function, range.get(0), range.get(1));
        }

        double universeCardinality = getUniverseCardinality();
        return sum / universeCardinality;
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
        if (getUniverseOfDiscourse().getType() == UniverseType.DISCRETE)
            return getSupp().getElements().isEmpty();
        return ((ContinuousUniverse) getUniverseOfDiscourse()).discretizeUniverse()
                .stream().map(this::calculateMembership).filter(e -> e > 0).toList().isEmpty();
    }

    public double getHeight() {
        return getSupp().getElements().stream()
                .mapToDouble(membershipFunction::calculateMembershipDegree)
                .max().orElse(0d);
    }

    public double calculateMembership(double x) {
        return membershipFunction.calculateMembershipDegree(x);
    }

    public static List<Double> calculateMembershipAnd(List<List<Double>> objects, List<FuzzySet> sets) {
        List<Double> minimumValues = new ArrayList<>();

        int numPositions = objects.get(0).size();

        for (int i = 0; i < numPositions; i++) {
            double minValue = 1;

            int finalI = i;
            List<Double> rows = objects.stream().map(list -> list.get(finalI)).toList();

            int index = 0;
            for (double x : rows) {
                double value = sets.get(index).calculateMembership(x);
                if (value < minValue)
                    minValue = value;
                index++;
            }
            minimumValues.add(minValue);
        }
        return minimumValues;
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
