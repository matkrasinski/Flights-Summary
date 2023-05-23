package pl.ksr.functions;


public class TriangleFunction implements MembershipFunction {
    //Start range
    double a;

    // Center of range
    double b;

    // End range
    double c;

    public TriangleFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateMembershipDegree(double x) {
        if (a <= x && x <= b) {
            return (x - a) / (b - a);
        } else if (b <= x && x <= c) {
            return (c - x) / (c - b);
        } else
            return 0;
    }
}
