package pl.ksr.sets;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.sets.CrispSet;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.DiscreteUniverse;

public class CrispSetTest {
    @Test
    public void sumTest1() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DenseUniverse(0, 5));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DenseUniverse(0, 7));

        CrispSet sum = CrispSet.sum(crispSet1, crispSet2);

        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(8));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(7));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(1));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(0));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(-1));
    }

    @Test
    public void sumTest2() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DiscreteUniverse(new Double[]{1d, 2d, 3d, 4d, 5d}));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DiscreteUniverse(new Double[]{3d, 4d, 5d, 6d, 7d}));

        CrispSet sum = CrispSet.sum(crispSet1, crispSet2);

        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(8));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(7));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(1));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(0));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(-1));
    }
    @Test
    public void productTest1() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DenseUniverse(1, 5));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DenseUniverse(3, 7));

        CrispSet sum = CrispSet.product(crispSet1, crispSet2);

        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(1));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(3));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(4));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(2));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(7));
    }

    @Test
    public void productTest2() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DiscreteUniverse(new Double[]{1d, 2d, 3d, 4d, 5d}));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DiscreteUniverse(new Double[]{3d, 4d, 5d, 6d, 7d}));

        CrispSet sum = CrispSet.product(crispSet1, crispSet2);

        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(1));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(3));
        Assert.assertTrue(sum.getUniverseOfDiscourse().isIn(4));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(2));
        Assert.assertFalse(sum.getUniverseOfDiscourse().isIn(7));
    }

}
