package pl.ksr.functions;

public class TrapezoidFunction implements MembershipFunction {

    double a;
    double b;
    double c;
    double d;

    public TrapezoidFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double calculateMembershipDegree(double x) {
        if (a <= x && x <= b) {
            return (x - a) / (b - a);
        } else if (b <= x && x <= c) {
            return 1;
        } else if (c <= x && x <= d) {
            return (d - x) / (d - c);
        } else {
            return 0;
        }
    }
}
