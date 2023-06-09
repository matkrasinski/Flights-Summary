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
    public boolean contains(double x) {
        return X.contains(x);
    }
    @Override
    public List<List<Double>> getRange() {
        return List.of(X);
    }


}
