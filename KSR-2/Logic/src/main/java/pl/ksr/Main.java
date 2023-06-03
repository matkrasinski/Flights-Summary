package pl.ksr;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> lista = new ArrayList<>();
        lista.add(0);
        lista.add(1);
        lista.add(2);
        lista.add(3);
        lista.add(4);

        List<List<Integer>> kombinacje = generujKombinacje(lista);
        Set<String> unique = new HashSet<>();

        // Wyświetlanie kombinacji
        for (List<Integer> kombinacja : kombinacje) {
            StringBuilder comb = new StringBuilder();
            for (int i : kombinacja)
                comb.append(i);

            for (int i = 1; i < kombinacja.size(); i++) {
                String cut = sortString(comb.substring(0, i)) +  "," + sortString(comb.substring(i));
//                System.out.println(cut);
//                cut = sortString(cut);
                unique.add(cut);

            }
        }
        System.out.println(unique);
        System.out.println(unique.size());

    }

    private static String sortString(String str) {
        int[] numbers = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            numbers[i] = Character.getNumericValue(str.charAt(i));
        }
        Arrays.sort(numbers);

        // Convert the sorted array back to a string
        StringBuilder sortedNumberString = new StringBuilder();

        for (int number : numbers) {
            sortedNumberString.append(number);
        }
        return sortedNumberString.toString();
    }

    public static List<List<Integer>> generujKombinacje(List<Integer> lista) {
        List<List<Integer>> kombinacje = new ArrayList<>();

        // Generowanie kombinacji rekurencyjnie
        generujKombinacjeRekurencyjnie(kombinacje, lista, new ArrayList<Integer>());

        // Usuwanie pustych list i kombinacji z jednym elementem
        kombinacje.removeIf(list -> list.isEmpty() || list.size() == 1);

        // Dodawanie kombinacji ze wszystkimi elementami oryginalnej listy
        kombinacje.add(lista);

        return kombinacje;
    }

    private static void generujKombinacjeRekurencyjnie(List<List<Integer>> kombinacje, List<Integer> lista, List<Integer> aktualnaKombinacja) {
        // Dodawanie aktualnej kombinacji do listy kombinacji
        kombinacje.add(new ArrayList<>(aktualnaKombinacja));

        // Generowanie kolejnych kombinacji
        for (int i = 0; i < lista.size(); i++) {
            if (!aktualnaKombinacja.contains(lista.get(i))) {
                // Dodawanie kolejnego elementu do aktualnej kombinacji
                aktualnaKombinacja.add(lista.get(i));

                // Rekurencyjne generowanie kombinacji z pozostałymi elementami
                generujKombinacjeRekurencyjnie(kombinacje, lista, aktualnaKombinacja);

                // Usuwanie ostatnio dodanego elementu, aby wygenerować kolejne kombinacje
                aktualnaKombinacja.remove(aktualnaKombinacja.size() - 1);
            }
        }
    }


}
