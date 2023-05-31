package pl.ksr.sets;

import java.util.ArrayList;
import java.util.List;

public class ContinuousUniverse implements Universe {

    private final List<List<Double>> ranges;

    public ContinuousUniverse(double min, double max) {
        this.ranges = new ArrayList<>();
        this.ranges.add(List.of(min, max));
    }

    public ContinuousUniverse(List<List<Double>> ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean isIn(double x) {
        for (List<Double> range : ranges) {
            if (x >= range.get(0) && x <= range.get(1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UniverseType getType() {
        return UniverseType.CONTINUOUS;
    }

    @Override
    public List<List<Double>> getRange() {
        return ranges;
    }

    public List<Double> discretizeUniverse() {
        List<Double> discretized = new ArrayList<>();
        for (List<Double> range : getRange()) {
            for (double i = range.get(0); i <= range.get(1); i += 1) {
                discretized.add(i);
            }
        }
        return discretized;
    }
}
