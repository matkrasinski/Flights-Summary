package pl.ksr.view;

import java.util.List;

public class WeightsContext {
    private static List<Double> weights = List.of(1d / 11, 1d / 11, 1d / 11,
            1d / 11, 1d / 11, 1d / 11, 1d / 11,
            1d / 11, 1d / 11, 1d / 11, 1d / 11);

    public static List<Double> getWeights() {
        return weights;
    }

    public static void setWeights(List<Double> newWeights) {
        weights = newWeights;
    }
}
