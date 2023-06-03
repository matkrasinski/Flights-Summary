package pl.ksr.view;

import java.util.ArrayList;
import java.util.List;

import static pl.ksr.view.MainController.calculateCombinations;

public class Launcher {
    public static void main(String[] args) {
        int n = 2; // Liczba indeksów

        List<List<Integer>> combinations = calculateCombinations(n);

        // Wyświetlanie wyników
        for (List<Integer> combination : combinations) {
            List<Integer> group1 = new ArrayList<>();
            List<Integer> group2 = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (combination.contains(i)) {
                    group1.add(i);
                } else {
                    group2.add(i);
                }
            }

            // Sprawdzenie czy obie grupy mają odpowiednie rozmiary
            if (group1.size() > 0 && group2.size() > 0 && group1.size() + group2.size() == n) {
                // Wyświetlanie kombinacji
                System.out.println("Grupa 1: " + group1);
                System.out.println("Grupa 2: " + group2);
                System.out.println();
            }
        }
        Application.main(args);
    }
}