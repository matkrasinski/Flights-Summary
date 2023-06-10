package pl.ksr.summary;

import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;

import java.util.*;
import java.util.stream.Stream;

public class SummaryFactory {

    public static List<Summary> generateAllSingleSubject(List<LinguisticQuantifier> absoluteQuantifiers,
                                                         List<LinguisticQuantifier> relativeQuantifiers,
                                                         List<Label> selectedAttributes,
                                                         Subject selectedSubject) {
        List<Integer> numbers = selectedAttributes.stream().mapToInt(selectedAttributes::indexOf).boxed().toList();
        List<List<Integer>> onlySummarizersCombinations = generateVariations(numbers);
        List<List<Integer>> summarizersWithQualifiersCombs = generateAllCombinations(numbers);

        List<Summary> summaries = new ArrayList<>();
        List<LinguisticQuantifier> allQuantifiers =
                Stream.concat(absoluteQuantifiers.stream(), relativeQuantifiers.stream()).toList();
        for (var quantifier : allQuantifiers) {
            for (List<Integer> i : onlySummarizersCombinations) {
                Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(quantifier, selectedSubject,
                        i.stream().map(selectedAttributes::get).toList());
                summaries.add(summary);
            }
        }
        for (var quantifier : relativeQuantifiers) {
            for (int i = 0; i < summarizersWithQualifiersCombs.size(); i += 2) {
                Summary summary = SummaryGenerator.generateSingleSubjectSecondForm(quantifier, selectedSubject,
                        summarizersWithQualifiersCombs.get(i).stream().map(selectedAttributes::get).toList(),
                        summarizersWithQualifiersCombs.get(i + 1).stream().map(selectedAttributes::get).toList());
                summaries.add(summary);
            }
        }
        return summaries;
    }

    public static List<Summary> generateAllMultiSubject(List<LinguisticQuantifier> relativeQuantifiers,
                                                        List<Label> selectedAttributes,
                                                            List<Subject> selectedSubjects) {
        List<Integer> numbers = selectedAttributes.stream().mapToInt(selectedAttributes::indexOf).boxed().toList();
        List<List<Integer>> onlySummarizersCombinations = generateVariations(numbers);
        List<List<Integer>> summarizersWithQualifiersCombs = generateAllCombinations(numbers);
        List<Summary> summaries = new ArrayList<>();
        boolean forthIsGenerated = false;
        for (var quantifier : relativeQuantifiers) {
            for (List<Integer> comb : onlySummarizersCombinations) {
                //FIRST
                Summary summary1 = SummaryGenerator.generateMultiSubjectFirstForm(quantifier, selectedSubjects,
                        comb.stream().map(selectedAttributes::get).toList());
                Summary summary2 = SummaryGenerator.generateMultiSubjectFirstForm(quantifier,
                        List.of(selectedSubjects.get(1), selectedSubjects.get(0)),
                        comb.stream().map(selectedAttributes::get).toList());

                summaries.add(summary1);
                summaries.add(summary2);

                // FOURTH
                if (!forthIsGenerated) {
                    Summary summary3 = SummaryGenerator.generateMultiSubjectFourthForm(selectedSubjects,
                            comb.stream().map(selectedAttributes::get).toList());
                    Summary summary4 = SummaryGenerator.generateMultiSubjectFourthForm(
                            List.of(selectedSubjects.get(1), selectedSubjects.get(0)),
                            comb.stream().map(selectedAttributes::get).toList());
                    summaries.add(summary3);
                    summaries.add(summary4);
                }
            }
            forthIsGenerated = true;

            for (int i = 0; i < summarizersWithQualifiersCombs.size(); i += 2) {
                // SECOND
                Summary summary1 = SummaryGenerator.generateMultiSubjectSecondForm(quantifier, selectedSubjects,
                        summarizersWithQualifiersCombs.get(i).stream().map(selectedAttributes::get).toList(),
                        summarizersWithQualifiersCombs.get(i + 1).stream().map(selectedAttributes::get).toList()
                );
                Summary summary2 = SummaryGenerator.generateMultiSubjectSecondForm(quantifier,
                        List.of(selectedSubjects.get(1), selectedSubjects.get(0)),
                        summarizersWithQualifiersCombs.get(i).stream().map(selectedAttributes::get).toList(),
                        summarizersWithQualifiersCombs.get(i + 1).stream().map(selectedAttributes::get).toList()
                );


                // THIRD
                Summary summary3 = SummaryGenerator.generateMultiSubjectThirdForm(quantifier, selectedSubjects,
                        summarizersWithQualifiersCombs.get(i).stream().map(selectedAttributes::get).toList(),
                        summarizersWithQualifiersCombs.get(i + 1).stream().map(selectedAttributes::get).toList()
                );
                Summary summary4 = SummaryGenerator.generateMultiSubjectThirdForm(quantifier,
                        List.of(selectedSubjects.get(1), selectedSubjects.get(0)),
                        summarizersWithQualifiersCombs.get(i).stream().map(selectedAttributes::get).toList(),
                        summarizersWithQualifiersCombs.get(i + 1).stream().map(selectedAttributes::get).toList()
                );
                summaries.add(summary1);
                summaries.add(summary2);
                summaries.add(summary3);
                summaries.add(summary4);
            }
        }
        return summaries;
    }
    private static void generateCombinationsRecursion(List<List<Integer>> combination,
                                                      List<Integer> numbers,
                                                      List<Integer> actualCombination) {
        combination.add(new ArrayList<>(actualCombination));

        for (int i = 0; i < numbers.size(); i++) {
            if (!actualCombination.contains(numbers.get(i))) {
                actualCombination.add(numbers.get(i));

                generateCombinationsRecursion(combination, numbers, actualCombination);

                actualCombination.remove(actualCombination.size() - 1);
            }
        }
    }
    public static List<List<Integer>> generateCombination(List<Integer> numbers) {
        List<List<Integer>> combination = new ArrayList<>();

        generateCombinationsRecursion(combination, numbers, new ArrayList<>());

        combination.removeIf(list -> list.isEmpty() || list.size() == 1);

        combination.add(numbers);

        return combination;
    }
    private static String sortNumbersInString(String str) {
        int[] numbers = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            numbers[i] = Character.getNumericValue(str.charAt(i));
        }
        Arrays.sort(numbers);

        StringBuilder sortedNumberString = new StringBuilder();

        for (int number : numbers) {
            sortedNumberString.append(number);
        }
        return sortedNumberString.toString();
    }
    public static List<List<Integer>> generateAllCombinations(List<Integer> numbers) {
        List<List<Integer>> combinations = generateCombination(numbers);
        Set<String> unique = new HashSet<>();
        for (List<Integer> combination : combinations) {
            StringBuilder combinationSequence = new StringBuilder();
            for (int i : combination)
                combinationSequence.append(i);

            for (int i = 1; i < combination.size(); i++) {
                String cut = sortNumbersInString(combinationSequence.substring(0, i)) +  "," + sortNumbersInString(combinationSequence.substring(i));
                unique.add(cut);
            }
        }

        List<List<Integer>> parts = new ArrayList<>();
        for (String str : unique) {
            String[] split = str.split(",");
            List<Integer> first = Arrays.stream(split[0].split("")).mapToInt(Integer::parseInt).boxed().toList();
            List<Integer> second = Arrays.stream(split[1].split("")).mapToInt(Integer::parseInt).boxed().toList();

            parts.add(first);
            parts.add(second);
        }
        return parts;
    }
    private static void backtrack(List<Integer> numbers, List<Integer> variation, List<List<Integer>> variations, int size, int startIndex) {
        if (variation.size() == size) {
            variations.add(new ArrayList<>(variation));
            return;
        }

        for (int i = startIndex; i < numbers.size(); i++) {
            variation.add(numbers.get(i));
            backtrack(numbers, variation, variations, size, i + 1);
            variation.remove(variation.size() - 1);
        }
    }
    public static List<List<Integer>> generateVariations(List<Integer> numbers) {
        List<List<Integer>> variations = new ArrayList<>();
        for (int i = 1; i <= numbers.size(); i++) {
            backtrack(numbers, new ArrayList<>(), variations, i, 0);
        }

        return variations;
    }
}
