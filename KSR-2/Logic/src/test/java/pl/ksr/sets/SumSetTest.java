package pl.ksr.sets;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.functions.TriangleFunction;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.SumSet;

import java.util.List;

public class SumSetTest {
    @Test
    public void sumTest1() {
        SumSet sumSet = new SumSet();
        FuzzySet fuzzySet1 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        FuzzySet fuzzySet2 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 2),
                new TriangleFunction(0, 1, 2));

        sumSet.sum(fuzzySet1)
                .sum(fuzzySet2);

        Assert.assertEquals(0d, sumSet.calculateMembership(0), 0);
        Assert.assertEquals(1d, sumSet.calculateMembership(1), 0);
        Assert.assertEquals(2d / 3, sumSet.calculateMembership(2), 0);
        Assert.assertEquals(1d, sumSet.calculateMembership(3), 0);
        Assert.assertEquals(0d, sumSet.calculateMembership(5), 0);
    }

    @Test
    public void sumTest2() {
        SumSet sumSet = new SumSet();
        FuzzySet fuzzySet1 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        FuzzySet fuzzySet2 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 2),
                new TriangleFunction(0, 1, 2));

        sumSet.sum(fuzzySet1)
                .sum(fuzzySet2);

        Assert.assertEquals(0, sumSet.getUniverse().getRange().get(0), 0);
        Assert.assertEquals(5, sumSet.getUniverse().getRange().get(1), 0);
    }

}
