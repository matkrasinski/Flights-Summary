package pl.ksr.sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrispSet {
    private final List<Double> elements;
    private final Universe universeOfDiscourse;

    public CrispSet(Double[] elements, Universe universeOfDiscourse) {
        this.elements = List.of(elements);
        this.universeOfDiscourse = universeOfDiscourse;
    }
    public CrispSet(List<Double> elements, Universe universeOfDiscourse) {
        this.elements = elements;
        this.universeOfDiscourse = universeOfDiscourse;
    }

    public CrispSet(Universe universeOfDiscourse) {
        this.elements = new ArrayList<>();
        this.universeOfDiscourse = universeOfDiscourse;
    }

    public CrispSet complement() {
        if (universeOfDiscourse.getType() == UniverseType.CONTINUOUS) {
            List<List<Double>> ranges = universeOfDiscourse.getRange();
            List<List<Double>> complement = new ArrayList<>();

            double start = Double.NEGATIVE_INFINITY;
            for (List<Double> range : ranges) {
                double end = range.get(0) - 0.001;
                if (start <= end)
                    complement.add(List.of(start, end));

                start = range.get(1) + 0.001;
            }
            if (start != Double.POSITIVE_INFINITY) {
                complement.add(List.of(start, Double.POSITIVE_INFINITY));
            }
            return new CrispSet(new ContinuousUniverse(complement));
        }
        List<Double> newX = universeOfDiscourse.getRange().get(0);
        newX.removeAll(elements);
        Universe newUniverse = new DiscreteUniverse(newX);
        return new CrispSet(newUniverse);
    }

    public CrispSet union(CrispSet set) {
        Set<Double> unique = Stream.concat(
                        this.getElements().stream(),
                        set.getElements().stream())
                .collect(Collectors.toSet());
        if (universeOfDiscourse.getType() == UniverseType.CONTINUOUS) {
            unique.removeIf(x -> !this.universeOfDiscourse.contains(x) && !set.universeOfDiscourse.contains(x));
            Universe universe = new ContinuousUniverse(
                    Stream.concat(this.getUniverseOfDiscourse().getRange().stream(),
                            set.universeOfDiscourse.getRange().stream()).toList()
            );
            return new CrispSet(unique.stream().toList(), universe);
        }

        Universe universe = new DiscreteUniverse(
                Stream.concat(
                        this.universeOfDiscourse.getRange().get(0).stream(),
                        set.universeOfDiscourse.getRange().get(0).stream()
                ).toList()
        );
        unique.removeIf(x -> !universe.contains(x));

        return new CrispSet(unique.stream().toList(), universe);
    }


    public CrispSet intersection(CrispSet set) {
        Set<Double> unique = Stream.concat(
                        this.getElements().stream(),
                        set.getElements().stream())
                .collect(Collectors.toSet());
        if (universeOfDiscourse.getType() == UniverseType.CONTINUOUS) {
            List<List<Double>> ranges = Stream.concat(
                    this.universeOfDiscourse.getRange().stream(),
                    set.universeOfDiscourse.getRange().stream()
            ).toList();
            double[] intersection = ranges.get(0).stream().mapToDouble(e -> e).toArray(); // Start with the first range

            for (int i = 1; i < ranges.size(); i++) {
                double[] currentRange = ranges.get(i).stream().mapToDouble(e -> e).toArray();
                intersection[0] = Math.max(intersection[0], currentRange[0]);
                intersection[1] = Math.min(intersection[1], currentRange[1]);
            }
            Universe newUniverse = new ContinuousUniverse(intersection[0], intersection[1]);
            unique.removeIf(x -> !newUniverse.contains(x));
            return new CrispSet(unique.stream().toList(), newUniverse);
        }

        return new CrispSet(unique.stream().toList(), null);
    }


    public List<Double> getElements() {
        return elements;
    }

    public Universe getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }
}
