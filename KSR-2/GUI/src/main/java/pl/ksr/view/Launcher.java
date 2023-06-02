package pl.ksr.view;

import java.util.List;

import static pl.ksr.view.MainController.calculateCombinations;

public class Launcher {
    public static void main(String[] args) {
        int n = 5; // Wartość n

        List<List<Integer>> combinations = calculateCombinations(n);
        System.out.println(combinations);
//        // Wyświetlanie wyników
//        for (List<Integer> combination : combinations) {
//            List<Integer> group1 = new ArrayList<>();
//            List<Integer> group2 = new ArrayList<>();
//
//            for (int i = 1; i <= n - 1; i++) {
//                if (combination.contains(i)) {
//                    group1.add(i);
//                } else {
//                    group2.add(i);
//                }
//            }
//
//            // Sprawdzenie czy obie grupy nie są puste
//            if (!group1.isEmpty() && !group2.isEmpty()) {
//                // Wyświetlanie kombinacji
//                System.out.println("Grupa 1: " + group1);
//                System.out.println("Grupa 2: " + group2);
//                System.out.println();
//            }
//        }
        Application.main(args);
    }
}