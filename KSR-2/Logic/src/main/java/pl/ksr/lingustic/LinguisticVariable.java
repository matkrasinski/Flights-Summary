package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.Universe;

import java.util.List;
import java.util.Map;

public class LinguisticVariable {

    private final String variableName;
    private final List<Label> labels;
    private final Universe universeOfDiscourse;

    public LinguisticVariable(String variableName, List<Label> labels, Universe universeOfDiscourse) {
        this.variableName = variableName;
        this.labels = labels;
        this.universeOfDiscourse = universeOfDiscourse;
    }

    public List<Label> getLabels() {
        return labels;
    }
    public List<String> getLabelsNames() {
        return labels.stream().map(Label::getLabelName).toList();
    }

    public Universe getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }
    public String getVariableName() {
        return variableName;
    }
}
