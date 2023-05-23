package pl.ksr.sets;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.sets.CrispSet;
import pl.ksr.sets.DenseUniverse;

public class UniverseTest {
    @Test
    public void sumUniverseTest1() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DenseUniverse(1, 5));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DenseUniverse(3, 7));

        CrispSet sum = CrispSet.sum(crispSet1, crispSet2);

        Assert.assertEquals(1, sum.getUniverseOfDiscourse().getRange().get(0), 0);
        Assert.assertEquals(7, sum.getUniverseOfDiscourse().getRange().get(1), 0);
    }
    @Test
    public void productUniverseTest1() {
        CrispSet crispSet1 = new CrispSet(new Double[]{
                1d, 2d, 3d, 4d, 5d
        }, new DenseUniverse(1, 5));
        CrispSet crispSet2 = new CrispSet(new Double[]{
                1d, 6d, 3d, 4d, 5d
        }, new DenseUniverse(3, 7));

        CrispSet sum = CrispSet.product(crispSet1, crispSet2);

        Assert.assertEquals(3, sum.getUniverseOfDiscourse().getRange().get(0), 0);
        Assert.assertEquals(5, sum.getUniverseOfDiscourse().getRange().get(1), 0);
    }



}
