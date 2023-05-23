package pl.ksr.functions;

public class CompoundGaussian implements MembershipFunction {

    private final MembershipFunction first;
    private final MembershipFunction second;

    public CompoundGaussian(double m1, double s1, double m2, double s2) {
        this.first = new GaussianFunction(m1, s1);
        this.second = new GaussianFunction(m2, s2);
    }

    @Override
    public double calculateMembershipDegree(double x) {
        return Math.max(first.calculateMembershipDegree(x), second.calculateMembershipDegree(x));
    }
}
