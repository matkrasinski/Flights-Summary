package pl.ksr.lingustic;

import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.Universe;

import java.util.List;
import java.util.Map;

public class LinguisticVariable {
    private final String attributeName;
    private final String variableName;
    private final Map<String, FuzzySet> labels;
    private final Universe universeOfDiscourse;

    public LinguisticVariable(String attributeName, String variableName,
                              Map<String, FuzzySet> labels, Universe universeOfDiscourse) {
        this.attributeName = attributeName;
        this.variableName = variableName;
        this.labels = labels;
        this.universeOfDiscourse = universeOfDiscourse;
    }

    public Map<String, FuzzySet> getLabels() {
        return labels;
    }
    public List<String> getLabelsNames() {
        return labels.keySet().stream().toList();
    }
    public String getAttributeName() {
        return attributeName;
    }
    public Universe getUniverseOfDiscourse() {
        return universeOfDiscourse;
    }
    public String getVariableName() {
        return variableName;
    }
}
