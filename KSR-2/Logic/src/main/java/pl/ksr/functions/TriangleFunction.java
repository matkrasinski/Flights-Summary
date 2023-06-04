package pl.ksr.functions;


import pl.ksr.sets.Universe;

public class TriangleFunction extends MembershipFunction {
    //Start range
    private final double a;
    // Center of range
    private final double b;
    // End range
    private final double c;

    public TriangleFunction(double a, double b, double c, Universe universeOfDiscourse) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.universeOfDiscourse = universeOfDiscourse;
    }

    @Override
    public double calculateMembershipDegree(double x) {
        if (a <= x && x <= b)
            return (x - a) / (b - a);
        else if (b <= x && x <= c)
            return (c - x) / (c - b);
        else
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
}
