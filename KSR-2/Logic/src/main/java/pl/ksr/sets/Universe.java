package pl.ksr.sets;

import java.util.List;

public interface Universe {
    boolean contains(double x);
    List<List<Double>> getRange();
    UniverseType getType();
}
