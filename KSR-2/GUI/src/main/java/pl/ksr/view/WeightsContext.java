package pl.ksr.view;

import java.util.List;

public class WeightsContext {
    private static List<Double> weights;

    public static List<Double> getWeights() {
        return weights;
    }

    public static void setWeights(List<Double> newWeights) {
        weights = newWeights;
    }
}
