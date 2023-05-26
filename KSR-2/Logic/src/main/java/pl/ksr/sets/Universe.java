package pl.ksr.sets;

import java.util.List;

public interface Universe {
    boolean isIn(double x);
    List<Double> getRange();
    UniverseType getType();
}
