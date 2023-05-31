package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;

public class LinguisticQuantifier {

    private final boolean isRelative;
    private final Label label;

    public LinguisticQuantifier(boolean isRelative, Label label) {
        this.isRelative = isRelative;
        this.label = label;
    }

    public boolean isRelative() {
        return isRelative;
    }


    public Label getLabel() {
        return label;
    }
}
