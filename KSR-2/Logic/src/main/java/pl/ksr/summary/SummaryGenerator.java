package pl.ksr.summary;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.summary.measures.*;

import java.util.List;

public class SummaryGenerator {

    public static Summary generateSingleSubjectFirstForm(LinguisticQuantifier quantifier,
                                                         Subject subject,
                                                         List<Label> summarizers) {
        String summaryText = quantifier.getLabel() + " " +
                subject.getSubject() + " had/were " +
                concatLabels(summarizers.stream().map(Label::getLabelName).toList());


        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forSingleFirstForm(quantifier, subject, summarizers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                QuantifierImprecision.calculateT6(quantifier),
                QuantifierRelativeCardinality.calculateT7(quantifier),
                SummarizerCardinality.calculateT8(summarizers),
                0,
                0,
                0
        );

        return new Summary(summaryText, quantifier, List.of(subject), summarizers, null, qualityMeasures);
    }

    public static Summary generateSingleSubjectSecondForm(LinguisticQuantifier quantifier,
                                                          Subject subject,
                                                          List<Label> summarizers,
                                                          List<Label> qualifiers) {
        String summaryText = quantifier.getLabel() + " " + subject.getSubject()
                + " that had/were " +
                concatLabels(qualifiers.stream().map(Label::getLabelName).toList()) +
                " had/were " + concatLabels(summarizers.stream().map(Label::getLabelName).toList());

        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forSingleSecondForm(quantifier, subject, summarizers, qualifiers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                QuantifierImprecision.calculateT6(quantifier),
                QuantifierRelativeCardinality.calculateT7(quantifier),
                SummarizerCardinality.calculateT8(summarizers),
                QualifierImprecision.calculateT9(qualifiers),
                QualifierRelativeCardinality.calculateT10(qualifiers),
                LengthOfQualifier.calculateT11(qualifiers)
        );

        return new Summary(summaryText, quantifier, List.of(subject), summarizers, qualifiers, qualityMeasures);
    }


    public static Summary generateMultiSubjectFirstForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers) {
        String summaryText = quantifier.getLabel() +
                " " +
                subjects.get(0).getSubject() +
                " compared to " +
                subjects.get(1).getSubject() +
                " had/were " +
                concatLabels(summarizers.stream().map(Label::getLabelName).toList());


        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forMultiFirstForm(quantifier, subjects, summarizers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                QuantifierImprecision.calculateT6(quantifier),
                QuantifierRelativeCardinality.calculateT7(quantifier),
                SummarizerCardinality.calculateT8(summarizers),
                0,
                0,
                0
        );

        return new Summary(summaryText, quantifier, subjects, summarizers, null, qualityMeasures);
    }

    public static Summary generateMultiSubjectSecondForm(LinguisticQuantifier quantifier,
                                                               List<Subject> subjects,
                                                               List<Label> summarizers, List<Label> qualifiers) {
        String summaryText = quantifier.getLabel() + " " +
                subjects.get(0).getSubject() +
                " compared to " +
                subjects.get(1).getSubject() +
                " that had/were " +
                concatLabels(qualifiers.stream().map(Label::getLabelName).toList()) +
                " had/were " +
                concatLabels(summarizers.stream().map(Label::getLabelName).toList());

        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forMultiSecondForm(quantifier, subjects, summarizers, qualifiers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                QuantifierImprecision.calculateT6(quantifier),
                QuantifierRelativeCardinality.calculateT7(quantifier),
                SummarizerCardinality.calculateT8(summarizers),
                QualifierImprecision.calculateT9(qualifiers),
                QualifierRelativeCardinality.calculateT10(qualifiers),
                LengthOfQualifier.calculateT11(qualifiers)
        );

        return new Summary(summaryText, quantifier, subjects, summarizers, qualifiers, qualityMeasures);
    }

    public static Summary generateMultiSubjectThirdForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers, List<Label> qualifiers) {

        String summaryText = quantifier.getLabel() + " " +
                subjects.get(0).getSubject() + " that had/were " +
                concatLabels(qualifiers.stream().map(Label::getLabelName).toList()) +
                " compared to " +
                subjects.get(1).getSubject() +
                " had/were " +
                concatLabels(summarizers.stream().map(Label::getLabelName).toList());

        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forMultiThirdForm(quantifier, subjects, summarizers, qualifiers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                QuantifierImprecision.calculateT6(quantifier),
                QuantifierRelativeCardinality.calculateT7(quantifier),
                SummarizerCardinality.calculateT8(summarizers),
                QualifierImprecision.calculateT9(qualifiers),
                QualifierRelativeCardinality.calculateT10(qualifiers),
                LengthOfQualifier.calculateT11(qualifiers)
        );

        return new Summary(summaryText, quantifier, subjects, summarizers, qualifiers, qualityMeasures);
    }

    public static Summary generateMultiSubjectFourthForm(List<Subject> subjects, List<Label> summarizers) {
        String summaryText = "More " + subjects.get(0).getSubject() + " than " +
                subjects.get(1).getSubject() + " had/were " +
                concatLabels(summarizers.stream().map(Label::getLabelName).toList());

        QualityMeasures qualityMeasures = new QualityMeasures(
                DegreeOfTruth.calculateT1forMultiFourthForm(subjects, summarizers),
                DegreeOfImprecision.calculateT2(summarizers),
                DegreeOfCovering.calculateT3(),
                DegreeOfAppropriateness.calculateT4(),
                LengthOfSummary.calculateT5(summarizers),
                0,
                0,
                SummarizerCardinality.calculateT8(summarizers),
                0,
                0,
                0
        );

        return new Summary(summaryText, null, subjects, summarizers, null, qualityMeasures);
    }

    private static String concatLabels(List<String> labels) {
        StringBuilder summaryText = new StringBuilder();
        for (int i = 0; i < labels.size(); i++) {
            summaryText.append(labels.get(i));
            if (i + 1 != labels.size()) {
                summaryText.append(" and had/were ");
            }
        }
        return summaryText.toString();
    }


}
