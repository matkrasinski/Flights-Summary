package pl.ksr.functions;

import pl.ksr.sets.Universe;

import java.util.ArrayList;
import java.util.List;

public class CompoundFunction extends MembershipFunction {
    private final List<MembershipFunction> functions;

    public CompoundFunction(List<MembershipFunction> functions, Universe universeOfDiscourse) {
        super.universeOfDiscourse = universeOfDiscourse;
        this.functions = functions;
    }
    public CompoundFunction(Universe universeOfDiscourse) {
        this.universeOfDiscourse = universeOfDiscourse;
        this.functions = new ArrayList<>();
    }

    public CompoundFunction(MembershipFunction function) {
        functions = new ArrayList<>();
        functions.add(function);
        super.universeOfDiscourse = function.getUniverseOfDiscourse();
    }


    @Override
    public double calculateMembershipDegree(double x) {
        return functions.stream().mapToDouble(e -> e.calculateMembershipDegree(x)).max().orElse(0d);
    }


}
