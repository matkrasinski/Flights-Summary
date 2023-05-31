package pl.ksr.functions;

import pl.ksr.sets.Universe;

public abstract class MembershipFunction {
    public Universe universeOfDiscourse;
    public abstract double calculateMembershipDegree(double x);
    public Universe getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }



}
