package pl.ksr.functions;

import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.DiscreteUniverse;
import pl.ksr.sets.Universe;
import pl.ksr.sets.UniverseType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompoundFunction extends MembershipFunction {
    private final List<MembershipFunction> functions;
    private boolean intersection;


    // Tutaj masz konstruktor ktory z automatu przyjmuje ze funkcje sa intersekcja
    public CompoundFunction(List<MembershipFunction> functions, Universe universeOfDiscourse) {
        super.universeOfDiscourse = universeOfDiscourse;
        this.functions = functions;
        this.intersection = true;
    }
    public CompoundFunction(Universe universeOfDiscourse) {
        this.universeOfDiscourse = universeOfDiscourse;
        this.functions = new ArrayList<>();
    }

    public CompoundFunction(MembershipFunction function, boolean intersection) {
        functions = new ArrayList<>();
        functions.add(function);
        this.intersection = intersection;
        super.universeOfDiscourse = function.getUniverseOfDiscourse();
    }
    public CompoundFunction(MembershipFunction function) {
        functions = new ArrayList<>();
        functions.add(function);
        this.intersection = false;
        super.universeOfDiscourse = function.getUniverseOfDiscourse();
    }

    @Override
    public double calculateMembershipDegree(double x) {
        System.out.println(intersection);
        if (this.intersection)
            return functions.stream().mapToDouble(e -> e.calculateMembershipDegree(x)).min().orElse(0d);
        return functions.stream().mapToDouble(e -> e.calculateMembershipDegree(x)).max().orElse(0d);
    }

    public void addGaussianFunction(MembershipFunction membershipFunction) {
        Universe newUniverse;
        if (this.getUniverseOfDiscourse().getType() == UniverseType.DENSE && membershipFunction.getUniverseOfDiscourse().getType() == UniverseType.DENSE) {
            double start = Math.max(universeOfDiscourse.getRange().get(0), membershipFunction.getUniverseOfDiscourse().getRange().get(0));
            double end = Math.min(universeOfDiscourse.getRange().get(1), membershipFunction.getUniverseOfDiscourse().getRange().get(1));

            newUniverse = new DenseUniverse(start, end);
        } else {
            Set<Double> values = new HashSet<>();
            for (double x : universeOfDiscourse.getRange()) {
                for (MembershipFunction function : functions) {
                    if (membershipFunction.getUniverseOfDiscourse().isIn(x) && function.getUniverseOfDiscourse().isIn(x)) {
                        values.add(x);
                        break;
                    }
                }
            }
            newUniverse = new DiscreteUniverse(values.stream().toList());
        }
        universeOfDiscourse = newUniverse;
        functions.add(membershipFunction);
    }
}
