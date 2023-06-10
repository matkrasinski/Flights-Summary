package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;

import java.util.ArrayList;
import java.util.List;

public class Label {
    private String attributeName;
    private final String label;
    private final FuzzySet fuzzySet;

    public Label(String attributeName, String label, FuzzySet fuzzySet) {
        this.attributeName = attributeName;
        this.label = label;
        this.fuzzySet = fuzzySet;
    }

    public Label(String label, FuzzySet fuzzySet) {
        this.label = label;
        this.fuzzySet = fuzzySet;
    }

    public String getLabelName() {
        return label;
    }
    public FuzzySet getFuzzySet() {
        return fuzzySet;
    }
    public String getAttributeName() {
        return attributeName;
    }

    public static List<Double> andConnective(List<List<Double>> objects, List<Label> labels) {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < objects.get(0).size(); i++) {
            double min = 1;
            for (int j = 0; j < labels.size(); j++) {
                double value = labels.get(j).getFuzzySet().calculateMembership(objects.get(j).get(i));
                if (value < min)
                    min = value;
            }
            res.add(min);
        }
        return res;

    }
}
