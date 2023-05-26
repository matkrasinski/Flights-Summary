package pl.ksr.sets;

import java.util.List;

public class DiscreteUniverse implements Universe {

    private final List<Double> X;

    public DiscreteUniverse(List<Double> X) {
        this.X = X;
    }
    public DiscreteUniverse(Double[] X) {
        this.X = List.of(X);
    }

    @Override
    public boolean isIn(double x) {
        return X.contains(x);
    }
    @Override
    public UniverseType getType() {
        return UniverseType.DISCRETE;
    }
    @Override
    public List<Double> getRange() {
        return X;
    }

}
