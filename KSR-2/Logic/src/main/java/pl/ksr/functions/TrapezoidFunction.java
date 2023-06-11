package pl.ksr.functions;

import pl.ksr.sets.Universe;

public class TrapezoidFunction extends MembershipFunction {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidFunction(double a, double b, double c, double d, Universe universeOfDiscourse) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.universeOfDiscourse = universeOfDiscourse;
    }

    @Override
    public double calculateMembershipDegree(double x) {
        if (a <= x && x <= b) {
            if (a == b)
                return 1;
            return (x - a) / (b - a);
        } else if (b <= x && x <= c)
            return 1;
        else if (c <= x && x <= d) {
            if (c == d)
                return 1;
            return (d - x) / (d - c);
        }
        return 0;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }
}
