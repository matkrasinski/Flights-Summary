package pl.ksr.summary;

import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;

import java.util.ArrayList;
import java.util.List;

public class SummaryGenerator {
    private List<LinguisticVariable> summarizers;
    private List<LinguisticVariable> qualifiers;
    private List<LinguisticQuantifier> quantifiers;

    public SummaryGenerator(List<LinguisticVariable> summarizers, List<LinguisticVariable> qualifiers, List<LinguisticQuantifier> quantifiers) {
        this.summarizers = summarizers;
        this.qualifiers = qualifiers;
        this.quantifiers = quantifiers;
    }

    public List<Summary> generateSingleSubjectFirstForm() {
        List<Summary> summaries = new ArrayList<>();

        return summaries;
    }

    public List<Summary> generateSingleSubjectSecondForm() {
        List<Summary> summaries = new ArrayList<>();

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
