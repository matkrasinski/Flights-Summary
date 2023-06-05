package pl.ksr.view;

import pl.ksr.summary.Summary;

public class SummaryRow {

    private final String summary;
    private String t;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String t9;
    private String t10;
    private String t11;


    public SummaryRow(Summary summary) {
        this.summary = summary.getSummary();
        this.t = roundNumbers(summary.getQualityMeasures().calculateOptimalSummary(WeightsContext.getWeights()));
        this.t1 = roundNumbers(summary.getQualityMeasures().getT_1());
        this.t2 = roundNumbers(summary.getQualityMeasures().getT_2());
        this.t3 = roundNumbers(summary.getQualityMeasures().getT_3());
        this.t4 = roundNumbers(summary.getQualityMeasures().getT_4());
        this.t5 = roundNumbers(summary.getQualityMeasures().getT_5());
        this.t6 = roundNumbers(summary.getQualityMeasures().getT_6());
        this.t7 = roundNumbers(summary.getQualityMeasures().getT_7());
        this.t8 = roundNumbers(summary.getQualityMeasures().getT_8());
        this.t9 = roundNumbers(summary.getQualityMeasures().getT_9());
        this.t10 = roundNumbers(summary.getQualityMeasures().getT_10());
        this.t11 = roundNumbers(summary.getQualityMeasures().getT_11());
    }

    public SummaryRow(String summary, double t1) {
        this.summary = summary;
        this.t1 = String.valueOf((double) Math.round(t1 * 100) / 100);
    }

    private String roundNumbers(double value) {
//        if (value < 1.00 && value > 0.99)
//            return "0.99";
//        if (value > 0 && value < 0.01)
//            return "0.00";
        return String.valueOf((double) Math.round(value * 100) / 100);
    }

    @Override
    public String toString() {
//        return summary + ", " + "[" +  t + "]" + "[" + t1 + ", " + t2 + ", " + t3 + ", " + t4 + ", " + t5 + ", "
//                + t6 + ", " + t7 + ", " + t8 + ", " + t9 + ", " + t10 + ", " + t11 + "]";
        return   t + " " + t1 + " " + t2 + " " + t3 + " " + t4 + " " + t5 + " "
                + t6 + " " + t7 + " " + t8 + " " + t9 + " " + t10 + " " + t11;
//        return summary + ", " + t;
    }

    public String getSummary() {
        return summary;
    }

    public String getT() {
        return t;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getT3() {
        return t3;
    }

    public String getT4() {
        return t4;
    }

    public String getT5() {
        return t5;
    }

    public String getT6() {
        return t6;
    }

    public String getT7() {
        return t7;
    }

    public String getT8() {
        return t8;
    }

    public String getT9() {
        return t9;
    }

    public String getT10() {
        return t10;
    }

    public String getT11() {
        return t11;
    }
}
