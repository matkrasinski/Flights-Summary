package pl.ksr.summary.measures;

import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.ArrayList;
import java.util.List;

public class QuantifierImprecision {
    public static double calculateT6(LinguisticQuantifier quantifier) {
        List<Double> range = quantifier.getFuzzySet().getUniverseOfDiscourse().getRange().get(0);
        List<Double> objects = new ArrayList<>();
        for (double i = range.get(0); i <= range.get(1); i++) {
            objects.add(i);
        }

        return 1 - (double) quantifier.getFuzzySet().getSupp(objects).getElements().size() / objects.size();
    }
}
