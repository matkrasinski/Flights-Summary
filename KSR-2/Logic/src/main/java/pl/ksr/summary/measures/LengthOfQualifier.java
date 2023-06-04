package pl.ksr.summary.measures;

import pl.ksr.lingustic.Label;

import java.util.List;

public class LengthOfQualifier {
    public static double calculateT11(List<Label> qualifiers) {
        if (qualifiers == null || qualifiers.isEmpty())
            return 1;
        return 2 * Math.pow(0.5, qualifiers.size());
    }
}
