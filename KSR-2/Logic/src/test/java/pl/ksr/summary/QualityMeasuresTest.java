package pl.ksr.summary;

import org.junit.Assert;
import org.junit.Test;
import pl.ksr.functions.TriangleFunction;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.FuzzySet;

import java.util.List;

public class QualityMeasuresTest {
    @Test
    public void optimalSummaryTest1() {
        QualityMeasures qualityMeasures = new QualityMeasures();
        qualityMeasures
                .calculateT_5(List.of(new FuzzySet(List.of(new Double[]{0d, 5d}),
                        new DenseUniverse(0, 5),
                        new TriangleFunction(0, 3, 5)), new FuzzySet(List.of(new Double[]{0d, 5d}),
                        new DenseUniverse(0, 5),
                        new TriangleFunction(0, 3, 5))));
        Assert.assertEquals(0.5, qualityMeasures.getT_5(), 0);
        Assert.assertEquals(0.25, qualityMeasures.calculateOptimalSummary(List.of(0.1, 0.2, 0.3, 0.4, 0.5)), 0);
    }
}
