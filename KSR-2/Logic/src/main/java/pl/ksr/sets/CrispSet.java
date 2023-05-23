package pl.ksr.sets;

import java.util.HashSet;
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

    public static CrispSet sum(CrispSet set1, CrispSet set2) {
        Set<Double> unique = Stream.concat(
                set1.getElements().stream(),
                set2.getElements().stream())
                .collect(Collectors.toSet());

        Universe newUniverse;
        if (set1.universeOfDiscourse instanceof DenseUniverse && set2.universeOfDiscourse instanceof DenseUniverse) {
            newUniverse = new DenseUniverse(
                    Math.min(((DenseUniverse) set1.universeOfDiscourse).getMin(),
                            ((DenseUniverse) set2.universeOfDiscourse).getMin()),
                    Math.max(((DenseUniverse) set1.universeOfDiscourse).getMax(),
                            ((DenseUniverse) set2.universeOfDiscourse).getMax())
            );
        } else {
            assert set1.universeOfDiscourse instanceof DiscreteUniverse;
            newUniverse = new DiscreteUniverse(Stream.concat(
                    set1.universeOfDiscourse.getRange().stream(),
                    set2.universeOfDiscourse.getRange().stream())
                    .collect(Collectors.toList()));
        }
        return new CrispSet(unique.stream().toList(), newUniverse);
    }


    public static CrispSet product(CrispSet set1, CrispSet set2) {
        Set<Double> intersection = new HashSet<>(set1.elements);
        intersection.retainAll(set2.elements);
        Universe newUniverse;
        if (set1.universeOfDiscourse instanceof DenseUniverse && set2.universeOfDiscourse instanceof DenseUniverse) {
            double start = Math.max(((DenseUniverse) set1.universeOfDiscourse).getMin(),
                    ((DenseUniverse) set2.universeOfDiscourse).getMin());
            double end = Math.min(((DenseUniverse) set1.universeOfDiscourse).getMax(),
                    ((DenseUniverse) set2.universeOfDiscourse).getMax());

            newUniverse = new DenseUniverse(start, end);
        } else {
            Set<Double> unique = new HashSet<>();
            assert set1.universeOfDiscourse instanceof DiscreteUniverse && set2.universeOfDiscourse instanceof DiscreteUniverse;
            for (double x : set1.universeOfDiscourse.getRange()) {
                if (set2.universeOfDiscourse.getRange().contains(x))
                    unique.add(x);
            }

            newUniverse = new DiscreteUniverse(unique.stream().toList());
        }
        return new CrispSet(intersection.stream().toList(), newUniverse);
    }


    public List<Double> getElements() {
        return elements;
    }

    public Universe getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }
}
