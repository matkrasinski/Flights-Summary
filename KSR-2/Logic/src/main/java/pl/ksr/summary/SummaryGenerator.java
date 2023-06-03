package pl.ksr.summary;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.List;

public class SummaryGenerator {

    public static Summary generateSingleSubjectFirstForm(LinguisticQuantifier quantifier,
                                                         Subject subject,
                                                         List<Label> summarizers) {
//        List<List<Double>> objects = new ArrayList<>();
//        for (Label summarizer : summarizers)
//            objects.add(subject.getObject(summarizer.getAttributeName()));
//        boolean isEmpty = Label.andConnective(objects, summarizers)
//                .stream().filter(e -> e > 1e-2).findFirst().isEmpty();
//        if (isEmpty)
//            return null;

        StringBuilder summaryText = new StringBuilder(quantifier.getLabel() + " " + subject.getSubject() + " had/were ");
        for (int i = 0; i < summarizers.size(); i++) {
            summaryText.append(summarizers.get(i).getLabelName());
            if (i + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }

        }

        System.out.println(summaryText);
        Summary summary = new Summary(summaryText.toString(), List.of(subject), summarizers, null, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));

        return summary;
    }

    public static Summary generateSingleSubjectSecondForm(LinguisticQuantifier quantifier,
                                                          Subject subject,
                                                          List<Label> summarizers,
                                                          List<Label> qualifiers) {
//        List<List<Double>> summarizerObjects = new ArrayList<>();
//        List<List<Double>> qualifierObjects = new ArrayList<>();
//
////        System.out.println("summarizers");
////        summarizers.forEach(e -> System.out.println(e.getLabelName()));
////        System.out.println("qualifiers");
////        qualifiers.forEach(e -> System.out.println(e.getLabelName()));
//
////        FuzzySet intersection = summarizers.get(0).getFuzzySet();
////        FuzzySet intersection2 = qualifiers.get(0).getFuzzySet();
//
//        for (Label summarizer : summarizers)
//            summarizerObjects.add(subject.getObject(summarizer.getAttributeName()));
//        for (Label qualifier : qualifiers)
//            qualifierObjects.add(subject.getObject(qualifier.getAttributeName()));
//
////        for (int i = 1; i < summarizers.size(); i++) {
////            intersection = intersection.intersection(summarizers.get(i).getFuzzySet());
////        }
////
////        for (int i = 1; i < qualifiers.size(); i++) {
////            intersection2 = intersection2.intersection(qualifiers.get(i).getFuzzySet());
////        }
//
//
//        boolean summarizerEmpty = Label.andConnective(summarizerObjects, summarizers)
//                .stream().filter(e -> e > 1e-2).findFirst().isEmpty();
//        boolean qualifierEmpty = Label.andConnective(qualifierObjects, qualifiers)
//                .stream().filter(e -> e > 1e-2).findFirst().isEmpty();
//
////        summarizers = Stream.concat(summarizers.stream(), qualifiers.stream()).toList();
////        summarizerObjects = Stream.concat(summarizerObjects.stream(), qualifierObjects.stream()).toList();
//
//        boolean isEmpty = Label.andConnective(summarizerObjects, summarizers)
//                .stream().filter(e -> e > 1e-2).findFirst().isEmpty();
//
//        if (summarizerEmpty || qualifierEmpty)
//            return null;

        StringBuilder summaryText = new StringBuilder(quantifier.getLabel() + " " + subject.getSubject()
                + " that had/were ");
        for (int i = 0; i < qualifiers.size(); i++) {
            summaryText.append(qualifiers.get(i).getLabelName());
            if (i + 1 != qualifiers.size()) {
                summaryText.append(" and had/were ");
            }
        }
        summaryText.append(" had/were ");
        for (int j = 0; j < summarizers.size(); j++) {
            summaryText.append(summarizers.get(j).getLabelName());
            if (j + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }
        }

        Summary summary = new Summary(summaryText.toString(), List.of(subject), summarizers, qualifiers, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));
        return summary;
    }


    public static Summary generateMultiSubjectFirstForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers) {

        StringBuilder summaryText = new StringBuilder(quantifier.getLabel() + " " +
                subjects.get(0).getSubject() + " compared to " + subjects.get(1).getSubject() + " had/were ");
        for (int i = 0; i < summarizers.size(); i++) {
            summaryText.append(summarizers.get(i).getLabelName());
            if (i + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }
        }
        Summary summary = new Summary(summaryText.toString(), subjects, summarizers, null, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));

        return summary;
    }

    public static Summary generateMultiSubjectSecondForm(LinguisticQuantifier quantifier,
                                                               List<Subject> subjects,
                                                               List<Label> summarizers, List<Label> qualifiers) {
        StringBuilder summaryText = new StringBuilder(quantifier.getLabel() + " " +
                subjects.get(0).getSubject() + " compared to " + subjects.get(1).getSubject() + " that had/were ");
        for (int i = 0; i < qualifiers.size(); i++) {
            summaryText.append(qualifiers.get(i).getLabelName());
            if (i + 1 != qualifiers.size()) {
                summaryText.append(" and had/were ");
            }
        }
        summaryText.append(" had/were ");
        for (int j = 0; j < summarizers.size(); j++) {
            summaryText.append(summarizers.get(j).getLabelName());
            if (j + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }
        }

        Summary summary = new Summary(summaryText.toString(), subjects, summarizers, qualifiers, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));
        return summary;
    }

    public static Summary generateMultiSubjectThirdForm(LinguisticQuantifier quantifier,
                                                              List<Subject> subjects,
                                                              List<Label> summarizers, List<Label> qualifiers) {
        StringBuilder summaryText = new StringBuilder(quantifier.getLabel() + " " +
                subjects.get(0).getSubject() + " that had/were ");
        for (int i = 0; i < qualifiers.size(); i++) {
            summaryText.append(qualifiers.get(i).getLabelName());
            if (i + 1 != qualifiers.size()) {
                summaryText.append(" and had/were ");
            }
        }
        summaryText.append(" compared to ").append(subjects.get(1).getSubject()).append(" had/were ");
        for (int j = 0; j < summarizers.size(); j++) {
            summaryText.append(summarizers.get(j).getLabelName());
            if (j + 1 != summarizers.size()) {
                summaryText.append(" and had/were ");
            }
        }

        Summary summary = new Summary(summaryText.toString(), subjects, summarizers, qualifiers, quantifier);
        summary.setQualityMeasures(new QualityMeasures(summary));
        return summary;
    }

    public static Summary generateMultiSubjectFourthForm(List<Subject> subjects, List<Label> summarizers) {
        StringBuilder summaryText = new StringBuilder("More " + subjects.get(0).getSubject() + " than "
                + subjects.get(1).getSubject() + " had/were ");
        for (int i = 0; i < summarizers.size(); i++) {
            summaryText.append(summarizers.get(i).getLabelName());
            if (i + 1 != summarizers.size())
                summaryText.append(" and had/were ");
        }

        Summary summary = new Summary(summaryText.toString(), subjects, summarizers, null, null);
        summary.setQualityMeasures(new QualityMeasures(summary));

        return summary;
    }


}
