package pl.ksr.view;

import pl.ksr.summary.Summary;

public class SummaryRow {

    private final String summary;
    private final double t;
    private final double t1;
    private final double t2;
    private final double t3;
    private final double t4;
    private final double t5;
    private final double t6;
    private final double t7;
    private final double t8;
    private final double t9;
    private final double t10;
    private final double t11;


    public SummaryRow(Summary summary) {
        this.summary = summary.getSummary();
        this.t = (double) Math.round(summary.getQualityMeasures()
                .calculateOptimalSummary(WeightsContext.getWeights()) * 100) / 100;
        this.t1 = (double) Math.round(summary.getQualityMeasures().getT_1() * 100) / 100;
        this.t2 = (double) Math.round(summary.getQualityMeasures().getT_2() * 100) / 100;
        this.t3 = (double) Math.round(summary.getQualityMeasures().getT_3() * 100) / 100;
        this.t4 = (double) Math.round(summary.getQualityMeasures().getT_4() * 100) / 100;
        this.t5 = (double) Math.round(summary.getQualityMeasures().getT_5() * 100) / 100;
        this.t6 = (double) Math.round(summary.getQualityMeasures().getT_6() * 100) / 100;
        this.t7 = (double) Math.round(summary.getQualityMeasures().getT_7() * 100) / 100;
        this.t8 = (double) Math.round(summary.getQualityMeasures().getT_8() * 100) / 100;
        this.t9 = (double) Math.round(summary.getQualityMeasures().getT_9() * 100) / 100;
        this.t10 = (double) Math.round(summary.getQualityMeasures().getT_10() * 100) / 100;
        this.t11 = (double) Math.round(summary.getQualityMeasures().getT_11() * 100) / 100;
    }

    @Override
    public String toString() {
        return summary + ", " + "[" +  t + "]" + "[" + t1 + ", " + t2 + ", " + t3 + ", " + t4 + ", " + t5 + ", "
                + t6 + ", " + t7 + ", " + t8 + ", " + t9 + ", " + t10 + ", " + t11 + "]";
    }

    public String getSummary() {
        return summary;
    }

    public double getT() {
        return t;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }

    public double getT3() {
        return t3;
    }

    public double getT4() {
        return t4;
    }

    public double getT5() {
        return t5;
    }

    public double getT6() {
        return t6;
    }

    public double getT7() {
        return t7;
    }

    public double getT8() {
        return t8;
    }

    public double getT9() {
        return t9;
    }

    public double getT10() {
        return t10;
    }

    public double getT11() {
        return t11;
    }

}
