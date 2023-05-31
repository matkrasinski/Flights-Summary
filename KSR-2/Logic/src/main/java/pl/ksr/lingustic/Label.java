package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;

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
}
