package pl.ksr.sets;

import java.util.List;

public class DenseUniverse implements Universe {

    private final double min;
    private final double max;

    public DenseUniverse(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isIn(double x) {
        return x >= min && x <= max;
    }

    @Override
    public UniverseType getType() {
        return UniverseType.DENSE;
    }

    @Override
    public List<Double> getRange() {
        return List.of(min, max);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }


}
