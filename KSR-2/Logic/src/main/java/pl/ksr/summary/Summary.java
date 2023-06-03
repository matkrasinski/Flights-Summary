package pl.ksr.summary;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.List;

public class Summary {

    private final String summary;
    private QualityMeasures qualityMeasures;
    private final List<Subject> subjects;
    private final List<Label> summarizers;
    private final List<Label> qualifiers;
    private final LinguisticQuantifier quantifier;


    public Summary(String summary, List<Subject> subjects, List<Label> summarizers, List<Label> qualifiers,
                   LinguisticQuantifier quantifier) {
        this.summary = summary;
        this.subjects = subjects;
        this.summarizers = summarizers;
        this.qualifiers = qualifiers;
        this.quantifier = quantifier;
    }

    public String getSummary() {
        return summary;
    }

    public QualityMeasures getQualityMeasures() {
        return qualityMeasures;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setQualityMeasures(QualityMeasures qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
    }

    public List<Label> getSummarizers() {
        return summarizers;
    }

    public List<Label> getQualifiers() {
        return qualifiers;
    }

    public LinguisticQuantifier getQuantifier() {
        return quantifier;
    }

}
