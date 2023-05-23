package pl.ksr.sets;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.functions.TriangleFunction;
import pl.ksr.sets.CrispSet;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.DenseUniverse;

import java.util.List;

public class FuzzySetTest {
    @Test
    public void getSuppTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));

        Assert.assertEquals(4, fuzzySet.getSupp().getElements().size());
    }

    @Test
    public void getAlphaCutTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));

        Assert.assertEquals(3, fuzzySet.getAlphaCut(0.4).getElements().size());
    }

    @Test
    public void getCardinalityTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));
        System.out.println(fuzzySet.getCardinality());
        Assert.assertEquals(2.5, fuzzySet.getCardinality(), 0);
    }

    @Test
    public void getDegreeOfFuzzinessTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));
        System.out.println(fuzzySet.getSupp().getElements());
        System.out.print(fuzzySet.getDegreeOfFuzziness());
        Assert.assertEquals(2.5 / 6, fuzzySet.getDegreeOfFuzziness(), 0);
    }

    @Test
    public void getCentroidTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));
        Assert.assertEquals(((double) 20 / 3) / 2.5, fuzzySet.getCentroid(), 0.000001);
    }

    @Test
    public void isNormalTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 4d,3d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));

        Assert.assertTrue(fuzzySet.isNormal());
    }

    @Test
    public void isNormalTest2() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3.5d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));

        Assert.assertFalse(fuzzySet.isNormal());
    }

    @Test
    public void getComplementTest1() {
        CrispSet crispSet = new CrispSet(new Double[]{0d, 1d, 2d, 3.5d, 4d, 5d}, new DenseUniverse(0, 5));
        FuzzySet fuzzySet = new FuzzySet(crispSet, new TriangleFunction(0, 3, 5));
        System.out.println(fuzzySet.getCrispSet().getElements().stream().map(fuzzySet::calculateMembership).toList());
        System.out.println(fuzzySet.getComplementValues());
    }

//    @Test
//    public void sumTest1() {
//        CrispSet crispSet1 = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 5));
//        FuzzySet fuzzySet1 = new FuzzySet(crispSet1, new TriangleFunction(0, 3, 5));
//
//        CrispSet crispSet2 = new CrispSet(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}, new DenseUniverse(0, 2));
//        FuzzySet fuzzySet2 = new FuzzySet(crispSet2, new TriangleFunction(0, 1, 2));
//
//        FuzzySet sum = FuzzySet.sum(fuzzySet1, fuzzySet2);
//
//        System.out.println(sum.getCrispSet().getElements());
//    }

    @Test
    public void isSetEmptyTest1() {
        FuzzySet fuzzySet = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3.5d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        Assert.assertFalse(fuzzySet.isEmpty());
    }
    @Test
    public void isSetEmptyTest2() {
        FuzzySet fuzzySet = new FuzzySet(List.of(new Double[]{0d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        Assert.assertTrue(fuzzySet.isEmpty());
    }

    @Test
    public void checkConvexTest1() {
        FuzzySet fuzzySet = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));

        List<Double> elements = fuzzySet.getCrispSet().getElements();

        Assert.assertTrue(fuzzySet.isConvex(0.5, elements.get(0), elements.get(1)));
        Assert.assertFalse(fuzzySet.isConvex(0.5, elements.get(0), elements.get(5)));
    }


}
