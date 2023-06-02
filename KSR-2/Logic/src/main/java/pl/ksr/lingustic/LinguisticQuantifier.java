package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;

public class LinguisticQuantifier {

    private final boolean isRelative;
    private final String label;
    private final FuzzySet fuzzySet;

    public LinguisticQuantifier(boolean isRelative, String label, FuzzySet fuzzySet) {
        this.isRelative = isRelative;
        this.label = label;
        this.fuzzySet = fuzzySet;
    }

    public boolean isRelative() {
        return isRelative;
    }


    public String getLabel() {
        return label;
    }

    public FuzzySet getFuzzySet() {
        return fuzzySet;
    }
}
