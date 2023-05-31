package pl.ksr.summary;

import pl.ksr.database.FlightsRepository;
import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.sets.FuzzySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummaryGenerator {

//    public static Summary generateSingleSubjectFirstForm(LinguisticQuantifier quantifier,
//                                                         Subject subject,
//                                                         List<Label> summarizers) {
//        List<List<Double>> objects = new ArrayList<>();
//        for (Label summarizer : summarizers)
//            objects.add(FlightsRepository.findAllByName(summarizer.getAttributeName()));
//
//        List<Double> intersectionList = FuzzySet.calculateMembershipAnd(objects,
//                summarizers.stream().map(Label::getFuzzySet).toList());
//
//        boolean summarizersEmpty = false;
//        boolean isEmpty = true;
//        if (summarizers.size() > 1) {
//            isEmpty = intersectionList.stream().anyMatch(x -> x <= 0);
//            summarizersEmpty = summarizers.stream().anyMatch(e -> e.getFuzzySet().isEmpty());
//        }
//        for (int i = 0; i < summarizers.size(); i++ )
//            for (int j = i + 1; j < summarizers.size(); j++ )
//                if (summarizers.get(i).equals(summarizers.get(j))) {
//                    summarizersEmpty = true;
//                    break;
//                }
//
//        if (!summarizersEmpty && isEmpty) {
//            StringBuilder summaryText = new StringBuilder(quantifier.getLabel().getLabelName() + " " + subject.getSubject() + " had/were ");
//            for (Label summarizer : summarizers) {
//                summaryText.append(summarizer.getLabelName()).append(" and had/were ");
//            }
//            summaryText = new StringBuilder(summaryText.substring(0, summaryText.length() - " and had/were ".length()));
//            Summary summary = new Summary(
//                    summaryText.toString(), SummaryType.SingleIForm, List.of(subject), summarizers, Collections.emptyList(),
//                    quantifier
//            );
//            summary.setQualityMeasures(new QualityMeasures(summary));
//            System.out.println(summaryText);
//            return summary;
//        }
//
//        return null;
//    }

    public static Summary generateSingleSubjectFirstForm(LinguisticQuantifier quantifier,
                                                         Subject subject,
                                                         List<Label> summarizers) {

        StringBuilder summaryText = new StringBuilder(quantifier.getLabel().getLabelName() + " " + subject.getSubject() + " had/were ");
        for (int i = 0; i < summarizers.size(); i++) {
            if (!summarizers.get(i).getFuzzySet().isEmpty()) {
                summaryText.append(summarizers.get(i).getLabelName());
            }

            if (i + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }

        }

        System.out.println(summaryText);
        Summary summary = new Summary(summaryText.toString(), SummaryType.SingleIForm, List.of(subject), summarizers, null, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));

        return summary;
    }


    public static Summary generateSingleSubjectSecondForm(LinguisticQuantifier quantifier,
                                                          Subject subject,
                                                          List<Label> summarizers,
                                                          List<Label> qualifiers) {
        List<List<Double>> objects = new ArrayList<>();
        objects.add(FlightsRepository.findAllByName(summarizers.get(0).getAttributeName()));
        var intersection = summarizers.get(0).getFuzzySet();
        for (int i = 1; i < summarizers.size(); i++) {
            objects.add(FlightsRepository.findAllByName(summarizers.get(i).getAttributeName()));
            intersection = intersection.intersection(summarizers.get(i).getFuzzySet());
        }

        List<Double> intersectionList = FuzzySet.calculateMembershipAnd(objects,
                summarizers.stream().map(Label::getFuzzySet).toList());
        boolean summarizersEmpty = false;
        boolean intersectionEmpty = true;
        boolean qualifierIntersectionEmpty = FuzzySet.calculateMembershipAnd(
                List.of(intersectionList, FlightsRepository.findAllByName(qualifiers.get(0).getAttributeName())),
                List.of(intersection, qualifiers.get(0).getFuzzySet())).isEmpty();

        if (summarizers.size() > 1) {
            intersectionEmpty = intersectionList.stream().anyMatch(x -> x <= 0);
            summarizersEmpty = summarizers.stream().anyMatch(e -> e.getFuzzySet().isEmpty());
        }
        for (int i = 0; i < summarizers.size(); i++ )
            for (int j = i + 1; j < summarizers.size(); j++ )
                if (summarizers.get(i).equals(summarizers.get(j))) {
                    summarizersEmpty = true;
                    break;
                }

        if (!summarizersEmpty && intersectionEmpty && !qualifierIntersectionEmpty) {
            StringBuilder summaryText = new StringBuilder(quantifier.getLabel().getLabelName() + " " +
                    subject.getSubject() + " that had/were " + qualifiers.get(0).getLabelName() + " had/were ") ;

            for (Label summarizer : summarizers) {
                summaryText.append(summarizer.getLabelName()).append(" and ");
            }
            summaryText = new StringBuilder(summaryText.substring(0, summaryText.length() - " and ".length()));
            Summary summary = new Summary(
                    summaryText.toString(), SummaryType.SingleIIForm, List.of(subject), summarizers, qualifiers,
                    quantifier
            );
            summary.setQualityMeasures(new QualityMeasures(summary));
            System.out.println(summaryText);
            return summary;
        }

        return null;
    }

    public static Summary generateMultiSubjectFirstForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers) {
        Summary summary = new Summary("", SummaryType.MultiIForm, subjects, summarizers,
                Collections.emptyList(), quantifier);

        return summary;
    }

    public static Summary generateMultiSubjectSecondForm(LinguisticQuantifier quantifier,
                                                               List<Subject> subjects,
                                                               List<Label> summarizers, List<Label> qualifiers) {
        Summary summary = new Summary(
                "", SummaryType.MultiIIForm, subjects, summarizers, qualifiers, quantifier
        );

        return summary;
    }

    public static Summary generateMultiSubjectThirdForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers, List<Label> qualifiers) {
        Summary summary = new Summary(
                "", SummaryType.MultiIIIForm, subjects, summarizers, qualifiers, quantifier
        );

        return summary;
    }

    public static Summary generateMultiSubjectFourthForm(List<Subject> subjects, List<Label> summarizers) {
        Summary summary = new Summary(
                "", SummaryType.MultiIVForm, subjects, summarizers, Collections.emptyList(), null
        );

        return summary;
    }


}
