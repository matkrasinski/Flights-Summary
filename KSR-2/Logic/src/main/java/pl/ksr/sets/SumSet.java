package pl.ksr.sets;

import pl.ksr.functions.MembershipFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumSet {
    private List<MembershipFunction> functions;
    private Universe universe;

    public SumSet() {}

    public SumSet(List<MembershipFunction> functions, Universe universe) {
        this.functions = functions;
        this.universe = universe;
    }

    public SumSet sum(FuzzySet fuzzySet) {
        if (functions == null)
            functions = new ArrayList<>();
        if (universe == null)
            universe = fuzzySet.getCrispSet().getUniverseOfDiscourse();
        else {
            Universe newUniverse;
            if (this.universe instanceof DenseUniverse && fuzzySet.getCrispSet().getUniverseOfDiscourse() instanceof DenseUniverse) {
                newUniverse = new DenseUniverse(
                        Math.min(((DenseUniverse) this.universe).getMin(),
                                ((DenseUniverse) fuzzySet.getCrispSet().getUniverseOfDiscourse()).getMin()),
                        Math.max(((DenseUniverse) this.universe).getMax(),
                                ((DenseUniverse) fuzzySet.getCrispSet().getUniverseOfDiscourse()).getMax())
                );
            } else {
                assert this.universe instanceof DiscreteUniverse;
                newUniverse = new DiscreteUniverse(Stream.concat(
                                    this.universe.getRange().stream(),
                                    fuzzySet.getCrispSet().getUniverseOfDiscourse().getRange().stream())
                        .collect(Collectors.toList()));
            }
            universe = newUniverse;
        }

        functions.add(fuzzySet.getMembershipFunction());
        return this;
    }

    public double calculateMembership(double x) {
        double max = 0;
        for (MembershipFunction membershipFunction : functions) {
            double degree = membershipFunction.calculateMembershipDegree(x);
            if (degree > max)
                max = degree;
        }
        return max;
    }

    public void setFunctions(List<MembershipFunction> functions) {
        this.functions = functions;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public Universe getUniverse() {
        return universe;
    }
}
