package pl.ksr.sets;

import pl.ksr.functions.MembershipFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class FuzzySet {
    private final CrispSet crispSet;

    private final MembershipFunction membershipFunction;

    public FuzzySet(Universe universe, MembershipFunction function) {
        membershipFunction = function;
        crispSet = new CrispSet(new ArrayList<>(), universe);
    }

    public FuzzySet(CrispSet crispSet, MembershipFunction membershipFunction) {
        this.crispSet = crispSet;
        this.membershipFunction = membershipFunction;
    }

    public FuzzySet(List<Double> elements, Universe universe, MembershipFunction membershipFunction) {
        this.crispSet = new CrispSet(elements, universe);
        this.membershipFunction = membershipFunction;
    }

    public List<Double> getComplementValues() {
        List<Double> newValues = new ArrayList<>();
        for (double x : crispSet.getElements()) {
            newValues.add(1 - membershipFunction.calculateMembershipDegree(x));
        }
        return newValues;
    }

    public CrispSet getSupp() {
        List<Double> elements = new ArrayList<>();

        for (double x : crispSet.getElements())
            if (membershipFunction.calculateMembershipDegree(x) > 0)
                elements.add(x);

        return new CrispSet(elements, crispSet.getUniverseOfDiscourse());
    }

    public CrispSet getAlphaCut(double alpha) {
        List<Double> elements = new ArrayList<>();

        for (double x : crispSet.getElements())
            if (membershipFunction.calculateMembershipDegree(x) > alpha)
                elements.add(x);

        return new CrispSet(elements, crispSet.getUniverseOfDiscourse());
    }

    public double getCardinality() {
        double cardinality = 0;

        for (double x : crispSet.getElements())
            cardinality += membershipFunction.calculateMembershipDegree(x);

        return cardinality;
    }
    private double getComplementCardinality() {
        double complimentCardinality = 0;

        for (double x : crispSet.getElements())
            complimentCardinality += (1 - membershipFunction.calculateMembershipDegree(x));

        return complimentCardinality;
    }

    private double getUniverseCardinality() {
        return getCardinality() + getComplementCardinality();
    }

    public double getDegreeOfFuzziness() {
        return getSupp().getElements().stream().mapToDouble(membershipFunction::calculateMembershipDegree).sum()
                / getUniverseCardinality();
    }

    public double getCentroid() {
        double sum = 0;
        double weights = 0;

        for (double x : crispSet.getElements()) {
            double weight = membershipFunction.calculateMembershipDegree(x);
            sum += x * weight;
            weights += weight;
        }
        return sum / weights;
    }

    public boolean isNormal() {
        return getHeight() == 1;
    }

    public boolean isConvex(double alpha, double x1, double x2) {
        return calculateMembership(alpha * x1 + (1 - alpha) * x2) <=
                alpha * calculateMembership(x1) + (1 - alpha) * calculateMembership(x2);
    }

    public boolean isEmpty() {
        for (double x : crispSet.getElements())
            if (membershipFunction.calculateMembershipDegree(x) > 0)
                return false;
        return true;
    }

    public double getHeight() {
        OptionalDouble optionalDouble = crispSet.getElements().stream()
                .mapToDouble(membershipFunction::calculateMembershipDegree)
                .max();
        return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
    }

    public double calculateMembership(double x) {
        return membershipFunction.calculateMembershipDegree(x);
    }

    public CrispSet getCrispSet() {
        return crispSet;
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
}
