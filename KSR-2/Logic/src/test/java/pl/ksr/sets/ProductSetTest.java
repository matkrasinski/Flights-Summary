package pl.ksr.sets;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.functions.TriangleFunction;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.ProductSet;

import java.util.List;

public class ProductSetTest {

    @Test
    public void productTest1() {
        ProductSet productSet = new ProductSet();
        FuzzySet fuzzySet1 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        FuzzySet fuzzySet2 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 2),
                new TriangleFunction(0, 1, 2));

        productSet.product(fuzzySet1)
                .product(fuzzySet2);

        Assert.assertEquals(0, productSet.calculateMembership(0), 0);
        Assert.assertEquals(1d / 3, productSet.calculateMembership(1), 0);
        Assert.assertEquals(7d / 15, productSet.calculateMembership(1.4), 0.0000000001);
        Assert.assertEquals(0, productSet.calculateMembership(3), 0);
    }

    @Test
    public void sumTest2() {
        ProductSet productSet = new ProductSet();
        FuzzySet fuzzySet1 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 5),
                new TriangleFunction(0, 3, 5));
        FuzzySet fuzzySet2 = new FuzzySet(List.of(new Double[]{0d, 1d, 2d, 3d, 4d, 5d}),
                new DenseUniverse(0, 2),
                new TriangleFunction(0, 1, 2));

        productSet.product(fuzzySet1)
                .product(fuzzySet2);

        Assert.assertEquals(0, productSet.getUniverse().getRange().get(0), 0);
        Assert.assertEquals(2, productSet.getUniverse().getRange().get(1), 0);
    }
}
