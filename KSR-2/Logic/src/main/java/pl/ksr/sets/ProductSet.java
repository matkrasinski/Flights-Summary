//package pl.ksr.sets;
//
//import pl.ksr.functions.MembershipFunction;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class ProductSet {
//
//    private List<MembershipFunction> functions;
//    private Universe universe;
//
//    public ProductSet() {}
//
//    public ProductSet(List<MembershipFunction> functions, Universe universe) {
//        this.functions = functions;
//        this.universe = universe;
//    }
//
//    public ProductSet product(FuzzySet fuzzySet) {
//        if (functions == null)
//            functions = new ArrayList<>();
//        if (universe == null)
//            universe = fuzzySet.getCrispSet().getUniverseOfDiscourse();
//        else {
//            Universe newUniverse;
//            if (this.universe instanceof DenseUniverse && fuzzySet.getCrispSet().getUniverseOfDiscourse() instanceof DenseUniverse) {
//                double start = Math.max(((DenseUniverse) this.universe).getMin(),
//                        ((DenseUniverse) fuzzySet.getCrispSet().getUniverseOfDiscourse()).getMin());
//                double end = Math.min(((DenseUniverse) this.universe).getMax(),
//                        ((DenseUniverse) fuzzySet.getCrispSet().getUniverseOfDiscourse()).getMax());
//
//                newUniverse = new DenseUniverse(start, end);
//            } else {
//                Set<Double> unique = new HashSet<>();
//                assert this.universe instanceof DiscreteUniverse && fuzzySet.getCrispSet().getUniverseOfDiscourse() instanceof DiscreteUniverse;
//                for (double x : (this.universe).getRange()) {
//                    if (fuzzySet.getCrispSet().getUniverseOfDiscourse().getRange().contains(x))
//                        unique.add(x);
//                }
//
//                newUniverse = new DiscreteUniverse(unique.stream().toList());
//            }
//            universe = newUniverse;
//        }
//        functions.add(fuzzySet.getMembershipFunction());
//        return this;
//    }
//
//    public double calculateMembership(double x) {
//        double min = Double.MAX_VALUE;
//        for (MembershipFunction membershipFunction : functions) {
//            double degree = membershipFunction.calculateMembershipDegree(x);
//            if (degree < min)
//                min = degree;
//        }
//        return min;
//    }
//
//    public Universe getUniverse() {
//        return universe;
//    }
//}
