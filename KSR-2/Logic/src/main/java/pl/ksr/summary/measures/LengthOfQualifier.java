package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class LengthOfQualifier {
    public static double calculateT11(List<Label> qualifiers) {
        return 2 * Math.pow(0.5, qualifiers.size());
    }
}
