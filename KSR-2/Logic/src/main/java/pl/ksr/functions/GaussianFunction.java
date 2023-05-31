package pl.ksr.functions;

import pl.ksr.sets.Universe;

public class GaussianFunction extends MembershipFunction {
    private final double m;
    private final double s;

    public GaussianFunction(double m, double s, Universe universeOfDiscourse) {
        this.m = m;
        this.s = s;
        this.universeOfDiscourse = universeOfDiscourse;
    }
    @Override
    public double calculateMembershipDegree(double x) {
        double membership = Math.exp(-0.5 * (Math.pow((x - m) / s, 2)));
        if (membership < 1e-2)
            return 0;
        return membership;
    }

}
