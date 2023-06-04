package pl.ksr;


import pl.ksr.lingustic.QuantifierManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        QuantifierManager.addLabelToRelativeQuantifiers("new", "GaussianFunction", List.of(0d,1d));
    }
}
