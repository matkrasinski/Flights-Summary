package pl.ksr.summary;

import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;

import java.util.ArrayList;
import java.util.List;

public class SummaryGenerator {
    private final List<LinguisticVariable> summarizers;
    private final List<LinguisticVariable> qualifiers;
    private final List<LinguisticQuantifier> quantifiers;

    public SummaryGenerator(List<LinguisticVariable> summarizers, List<LinguisticVariable> qualifiers, List<LinguisticQuantifier> quantifiers) {
        this.summarizers = summarizers;
        this.qualifiers = qualifiers;
        this.quantifiers = quantifiers;
    }

    public List<Summary> generateSingleSubjectFirstForm() {
        List<Summary> summaries = new ArrayList<>();
        for (LinguisticQuantifier quantifier : quantifiers) {
            String summary = "";
            for (LinguisticVariable summarizer : summarizers) {
                //T1
                // for T2 - T11
                for (String label : summarizer.getLabelsNames()) {
                    summary = quantifier.getLabel() + " flights has/are " + label;
                    summaries.add(new Summary(summary));
                }
            }
        }

        return summaries;
    }

    public List<Summary> generateSingleSubjectSecondForm() {
        List<Summary> summaries = new ArrayList<>();

        for (LinguisticQuantifier quantifier : quantifiers) {
            String summary = "";
            for (LinguisticVariable summarizer : summarizers) {
                for (var summarizerLabel : summarizer.getLabels().entrySet()) {
                    for (LinguisticVariable qualifier : qualifiers) {
                        if (summarizer != qualifier) {
                            for (var qualifierLabel : qualifier.getLabels().entrySet()) {
                                var intersection = summarizerLabel.getValue().intersection(qualifierLabel.getValue());
                                if (!qualifierLabel.getValue().isEmpty() && !summarizerLabel.getValue().isEmpty()
                                        && intersection.isEmpty()) {
                                    summary = quantifier.getLabel() + " flights that were " + qualifierLabel.getKey()
                                            + " has/are " + summarizerLabel.getKey();
                                    summaries.add(new Summary(summary));
                                }
                            }
                        }
                    }
                }
            }
        }

        return summaries;
    }

    public List<Summary> generateMultiSubjectFirstForm() {
        List<Summary> summaries = new ArrayList<>();

        return summaries;
    }

    public List<Summary> generateMultiSubjectSecondForm() {
        List<Summary> summaries = new ArrayList<>();

        return summaries;
    }

    public List<Summary> generateMultiSubjectThirdForm() {
        List<Summary> summaries = new ArrayList<>();

        return summaries;
    }

    public List<Summary> generateMultiSubjectFourthForm() {
        List<Summary> summaries = new ArrayList<>();

        return summaries;
    }


}
