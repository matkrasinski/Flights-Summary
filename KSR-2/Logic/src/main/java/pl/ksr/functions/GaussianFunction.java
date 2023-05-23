package pl.ksr.functions;

public class GaussianFunction implements MembershipFunction {

    double m;
    double s;


    public GaussianFunction(double m, double s) {
        this.m = m;
        this.s = s;
    }

    @Override
    public double calculateMembershipDegree(double x) {
        return Math.exp(-0.5 * (Math.pow((x - m) / s, 2)));
    }
}
