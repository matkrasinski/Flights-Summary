package pl.ksr;

//import pl.ksr.lingustic.VariableManager;

import pl.ksr.database.FlightsRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        MembershipFunction mem1 = new TrapezoidFunction(1380, 1440, 0, 60);
//        System.out.println(mem1.calculateMembershipDegree(1400));
//        System.out.println(mem1.calculateMembershipDegree(30) + "\n");
//
//        MembershipFunction mem2 = new ComplexGaussian(2.5, 30, 365, 30);
//
//        System.out.println(mem2.calculateMembershipDegree(0));
//        System.out.println(mem2.calculateMembershipDegree(25));
//        System.out.println(mem2.calculateMembershipDegree(100));
//        System.out.println(mem2.calculateMembershipDegree(350));

//        System.out.println(VariableManager.loadVariables().get(0).getLabels());

        List<String> allLabels = FlightsRepository.getAttributesNames();

        for (String label : allLabels) {
            System.out.println(FlightsRepository.findAllByName(label));
        }

    }
}
